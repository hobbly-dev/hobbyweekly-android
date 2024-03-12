package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.block

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kotlinx.coroutines.CoroutineExceptionHandler

@Immutable
data class RoutineBlockArgument(
    val state: RoutineBlockState,
    val event: EventFlow<RoutineBlockEvent>,
    val intent: (RoutineBlockIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface RoutineBlockState {
    data object Init : RoutineBlockState
    data object Loading : RoutineBlockState
}


sealed interface RoutineBlockEvent

sealed interface RoutineBlockIntent
