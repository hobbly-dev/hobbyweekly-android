package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RoutineState> = MutableStateFlow(RoutineState.Init)
    val state: StateFlow<RoutineState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RoutineEvent> = MutableEventFlow()
    val event: EventFlow<RoutineEvent> = _event.asEventFlow()

    private val _routineList: MutableStateFlow<List<Routine>> = MutableStateFlow(emptyList())
    val routineList: StateFlow<List<Routine>> = _routineList.asStateFlow()

    init {
//        _routineList.value = listOf(
//            Routine(
//                id = 0,
//                blockName = "블록 이름",
//                dayOfWeek = 0,
//                description = "설명",
//                alarmTime = null,
//                isEnabled = true,
//                isConfirmed = false
//            )
//        )
    }

    fun onIntent(intent: RoutineIntent) {
        when (intent) {
            is RoutineIntent.OnEditRoutine -> {
                editRoutine(intent.routine)
            }
        }
    }

    private fun editRoutine(routine: Routine) {
        launch {
            _state.value = RoutineState.Loading

            _state.value = RoutineState.Init
        }
    }
}
