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
import kr.hobbly.hobbyweekly.android.common.util.coroutine.zip
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.GetCurrentRoutineListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.GetLatestRoutineListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.SwitchRoutineAlarmUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCurrentRoutineListUseCase: GetCurrentRoutineListUseCase,
    private val getLatestRoutineListUseCase: GetLatestRoutineListUseCase,
    private val switchRoutineAlarmUseCase: SwitchRoutineAlarmUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<RoutineState> = MutableStateFlow(RoutineState.Init)
    val state: StateFlow<RoutineState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RoutineEvent> = MutableEventFlow()
    val event: EventFlow<RoutineEvent> = _event.asEventFlow()

    fun onIntent(intent: RoutineIntent) {
        when (intent) {
            is RoutineIntent.OnSwitch -> {
                switchRoutineAlarm(intent.routine)
            }

            RoutineIntent.Refresh -> {
                refresh()
            }
        }
    }

    private fun switchRoutineAlarm(routine: Routine) {
        launch {
            switchRoutineAlarmUseCase(
                id = routine.id,
                isEnabled = routine.isAlarmEnabled
            ).onSuccess {
                if (routine.isAlarmEnabled) {
                    _event.emit(RoutineEvent.UpdateAlarm.On(routine))
                } else {
                    _event.emit(RoutineEvent.UpdateAlarm.Off(routine))
                }
            }.onFailure { exception ->
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
                refresh()
            }
        }
    }

    private fun refresh() {
        launch {
            _state.value = RoutineState.Loading
            zip(
                { getCurrentRoutineListUseCase() },
                { getLatestRoutineListUseCase() }
            ).onSuccess { (currentRoutineList, latestRoutineList) ->
                _state.value = RoutineState.Init
                _event.emit(RoutineEvent.UpdateAlarm.Refresh(currentRoutineList, latestRoutineList))
                _event.emit(RoutineEvent.UpdateRoutine(currentRoutineList, latestRoutineList))
            }.onFailure { exception ->
                _state.value = RoutineState.Init
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
