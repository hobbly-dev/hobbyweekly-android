package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.routine

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
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.GetCurrentRoutineListUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class BoardRoutineViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCurrentRoutineListUseCase: GetCurrentRoutineListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<BoardRoutineState> =
        MutableStateFlow(BoardRoutineState.Init)
    val state: StateFlow<BoardRoutineState> = _state.asStateFlow()

    private val _event: MutableEventFlow<BoardRoutineEvent> = MutableEventFlow()
    val event: EventFlow<BoardRoutineEvent> = _event.asEventFlow()

    private val _routineList: MutableStateFlow<List<Routine>> = MutableStateFlow(emptyList())
    val routineList: StateFlow<List<Routine>> = _routineList.asStateFlow()

    init {
        launch {
            _state.value = BoardRoutineState.Loading
            getCurrentRoutineListUseCase()
                .onSuccess { routineList ->
                    _state.value = BoardRoutineState.Init
                    _routineList.value = routineList
                }.onFailure { exception ->
                    _state.value = BoardRoutineState.Init
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

    fun onIntent(intent: BoardRoutineIntent) {

    }
}
