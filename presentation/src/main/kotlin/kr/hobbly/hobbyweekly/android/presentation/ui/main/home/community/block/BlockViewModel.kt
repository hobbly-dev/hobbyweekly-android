package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.zip
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardType
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetBlockUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetMyBlockListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.RemoveMyBlockUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.board.GetBoardListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.GetPopularPostFromBlockPagingUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.SearchPostPagingFromBoardUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class BlockViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBlockUseCase: GetBlockUseCase,
    private val getMyBlockListUseCase: GetMyBlockListUseCase,
    private val getBoardListUseCase: GetBoardListUseCase,
    private val searchPostPagingFromBoardUseCase: SearchPostPagingFromBoardUseCase,
    private val getPopularPostFromBlockPagingUseCase: GetPopularPostFromBlockPagingUseCase,
    private val removeMyBlockUseCase: RemoveMyBlockUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<BlockState> = MutableStateFlow(BlockState.Init)
    val state: StateFlow<BlockState> = _state.asStateFlow()

    private val _event: MutableEventFlow<BlockEvent> = MutableEventFlow()
    val event: EventFlow<BlockEvent> = _event.asEventFlow()

    val blockId: Long by lazy {
        savedStateHandle.get<Long>(BlockConstant.ROUTE_ARGUMENT_BLOCK_ID) ?: -1L
    }

    private val _block: MutableStateFlow<Block> = MutableStateFlow(Block.empty)
    val block: StateFlow<Block> = _block.asStateFlow()

    private val _myBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val myBlockList: StateFlow<List<Block>> = _myBlockList.asStateFlow()

    val isMyBlock: StateFlow<Boolean> = combine(block, myBlockList) { block, myBlockList ->
        myBlockList.any { it.id == block.id }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

    private val _boardList: MutableStateFlow<List<Board>> = MutableStateFlow(emptyList())
    val boardList: StateFlow<List<Board>> = _boardList.asStateFlow()

    private val _noticePostPaging: MutableStateFlow<PagingData<Post>> =
        MutableStateFlow(PagingData.empty())
    val noticePostPaging: StateFlow<PagingData<Post>> = _noticePostPaging.asStateFlow()

    private val _popularPostPaging: MutableStateFlow<PagingData<Post>> =
        MutableStateFlow(PagingData.empty())
    val popularPostPaging: StateFlow<PagingData<Post>> = _popularPostPaging.asStateFlow()

    init {
        refresh()
    }

    fun onIntent(intent: BlockIntent) {
        when (intent) {
            BlockIntent.Refresh -> {
                refresh()
            }

            BlockIntent.OnRemove -> {
                removeBlock()
            }
        }
    }

    private fun removeBlock() {
        launch {
            _state.value = BlockState.Loading
            removeMyBlockUseCase(
                id = blockId
            ).onSuccess {
                _state.value = BlockState.Init
                _event.emit(BlockEvent.RemoveBlock.Success)
            }.onFailure { exception ->
                _state.value = BlockState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    private fun refresh() {
        launch {
            getPopularPostFromBlockPagingUseCase(id = blockId)
                .cachedIn(viewModelScope)
                .catch { exception ->
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }.collect { popularPostPaging ->
                    _popularPostPaging.value = popularPostPaging
                }

            _state.value = BlockState.Loading
            zip(
                { getBlockUseCase(id = blockId) },
                { getMyBlockListUseCase() },
                { getBoardListUseCase(id = blockId) }
            ).onSuccess { (block, myBlockList, boardList) ->
                _state.value = BlockState.Init
                _block.value = block
                _myBlockList.value = myBlockList
                _boardList.value = boardList

                boardList.find { it.type == BoardType.Notice }?.let { noticeBoard ->
                    searchPostPagingFromBoardUseCase(id = noticeBoard.id, keyword = "")
                        .cachedIn(viewModelScope)
                        .catch { exception ->
                            when (exception) {
                                is ServerException -> {
                                    _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                                }

                                else -> {
                                    _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                                }
                            }
                        }.collect { popularPostPaging ->
                            _popularPostPaging.value = popularPostPaging
                        }
                }
            }.onFailure { exception ->
                _state.value = BlockState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }
}
