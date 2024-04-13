package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine

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


sealed interface RoutineEvent {
    sealed interface UpdateAlarm : RoutineEvent {
        data class Off(val routine: Routine) : UpdateAlarm
        data class On(val routine: Routine) : UpdateAlarm
        data class Refresh(
            val currentRoutineList: List<Routine>,
            val latestRoutineList: List<Routine>
        ) : UpdateAlarm
    }

    data class UpdateRoutine(val currentRoutineList: List<Routine>) : RoutineEvent
}

sealed interface RoutineIntent {
    data class OnSwitch(val routine: Routine) : RoutineIntent
    data object Refresh : RoutineIntent
}
