package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.routine

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kotlinx.coroutines.CoroutineExceptionHandler

@Immutable
data class BoardRoutineArgument(
    val state: BoardRoutineState,
    val event: EventFlow<BoardRoutineEvent>,
    val intent: (BoardRoutineIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface BoardRoutineState {
    data object Init : BoardRoutineState
    data object Loading : BoardRoutineState
}


sealed interface BoardRoutineEvent

sealed interface BoardRoutineIntent
