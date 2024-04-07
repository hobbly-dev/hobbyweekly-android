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
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class BoardRoutineViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<BoardRoutineState> =
        MutableStateFlow(BoardRoutineState.Init)
    val state: StateFlow<BoardRoutineState> = _state.asStateFlow()

    private val _event: MutableEventFlow<BoardRoutineEvent> = MutableEventFlow()
    val event: EventFlow<BoardRoutineEvent> = _event.asEventFlow()

    private val _routineList: MutableStateFlow<List<Routine>> = MutableStateFlow(emptyList())
    val routineList: StateFlow<List<Routine>> = _routineList.asStateFlow()

    fun onIntent(intent: BoardRoutineIntent) {

    }
}
