package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.zip
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetBlockUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetMyBlockListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.board.GetBoardUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.SearchPostPagingFromBoardUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBlockUseCase: GetBlockUseCase,
    private val getMyBlockListUseCase: GetMyBlockListUseCase,
    private val getBoardUseCase: GetBoardUseCase,
    private val searchPostPagingFromBoardUseCase: SearchPostPagingFromBoardUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<BoardState> = MutableStateFlow(BoardState.Init)
    val state: StateFlow<BoardState> = _state.asStateFlow()

    private val _event: MutableEventFlow<BoardEvent> = MutableEventFlow()
    val event: EventFlow<BoardEvent> = _event.asEventFlow()

    val blockId: Long by lazy {
        savedStateHandle.get<Long>(BoardConstant.ROUTE_ARGUMENT_BLOCK_ID) ?: -1L
    }

    val boardId: Long by lazy {
        savedStateHandle.get<Long>(BoardConstant.ROUTE_ARGUMENT_BOARD_ID) ?: -1L
    }

    private val _block: MutableStateFlow<Block> = MutableStateFlow(Block.empty)
    val block: StateFlow<Block> = _block.asStateFlow()

    private val _isMyBlock: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isMyBlock: StateFlow<Boolean> = _isMyBlock.asStateFlow()

    private val _board: MutableStateFlow<Board> = MutableStateFlow(Board.empty)
    val board: StateFlow<Board> = _board.asStateFlow()

    private val _postPaging: MutableStateFlow<PagingData<Post>> =
        MutableStateFlow(PagingData.empty())
    val postPaging: StateFlow<PagingData<Post>> = _postPaging.asStateFlow()

    fun onIntent(intent: BoardIntent) {
        when (intent) {
            BoardIntent.Refresh -> {
                refresh()
            }
        }
    }

    private fun refresh() {
        launch {
            _state.value = BoardState.Loading
            zip(
                { getBlockUseCase(id = blockId) },
                { getMyBlockListUseCase() },
                { getBoardUseCase(id = boardId) }
            ).onSuccess { (block, myBlockList, board) ->
                _state.value = BoardState.Init
                _block.value = block
                _isMyBlock.value = myBlockList.any { it.id == blockId }
                _board.value = board
            }.onFailure { exception ->
                _state.value = BoardState.Init
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
        launch {
            searchPostPagingFromBoardUseCase(
                id = boardId,
                keyword = ""
            )
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
                }.collect { postPaging ->
                    _postPaging.value = postPaging
                }
        }
    }
}
