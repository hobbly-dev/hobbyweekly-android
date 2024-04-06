package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetBlockUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBlockUseCase: GetBlockUseCase,
//    private val getBoardUseCase: GetBoardUseCase, TODO
//    private val getBoardPostListUseCase: GetBoardPostListUseCase TODO
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

    private val _board: MutableStateFlow<Board> = MutableStateFlow(Board.empty)
    val board: StateFlow<Board> = _board.asStateFlow()

    private val _postList: MutableStateFlow<List<Post>> = MutableStateFlow(emptyList())
    val postList: StateFlow<List<Post>> = _postList.asStateFlow()

    init {
        refresh()
    }

    fun onIntent(intent: BoardIntent) {

    }

    private fun refresh() {
        _state.value = BoardState.Loading
        launch {
            getBlockUseCase(
                id = blockId
            ).onSuccess { block ->
                _state.value = BoardState.Init
                _block.value = block
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
    }
}
