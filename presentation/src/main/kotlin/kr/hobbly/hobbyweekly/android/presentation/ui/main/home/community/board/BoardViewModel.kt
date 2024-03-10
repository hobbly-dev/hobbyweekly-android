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
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
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

    private val _boardData: MutableStateFlow<BoardData> = MutableStateFlow(BoardData.empty)
    val boardData: StateFlow<BoardData> = _boardData.asStateFlow()

    fun onIntent(intent: BoardIntent) {

    }
}
