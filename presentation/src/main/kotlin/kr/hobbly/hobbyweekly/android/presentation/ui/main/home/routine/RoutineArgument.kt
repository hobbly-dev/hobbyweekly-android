package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kotlinx.coroutines.CoroutineExceptionHandler

@Immutable
data class RoutineArgument(
    val state: RoutineState,
    val event: EventFlow<RoutineEvent>,
    val intent: (RoutineIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface RoutineState {
    data object Init : RoutineState
    data object Loading : RoutineState
}


sealed interface RoutineEvent

sealed interface RoutineIntent
