package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine

@Immutable
data class RoutineEditArgument(
    val state: RoutineEditState,
    val event: EventFlow<RoutineEditEvent>,
    val intent: (RoutineEditIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface RoutineEditState {
    data object Init : RoutineEditState
    data object Loading : RoutineEditState
}


sealed interface RoutineEditEvent {
    sealed interface LoadData : RoutineEditEvent {
        data class Success(val routine: Routine) : LoadData
    }

    sealed interface AddRoutine : RoutineEditEvent {
        data object Success : AddRoutine
    }

    sealed interface EditRoutine : RoutineEditEvent {
        data object Success : EditRoutine
    }

    sealed interface DeleteRoutine : RoutineEditEvent {
        data object Success : DeleteRoutine
    }
}

sealed interface RoutineEditIntent {
    data class OnConfirm(
        val selectedDayOfWeek: List<Int>,
        val description: String
    ) : RoutineEditIntent

    data object OnDelete : RoutineEditIntent
}
