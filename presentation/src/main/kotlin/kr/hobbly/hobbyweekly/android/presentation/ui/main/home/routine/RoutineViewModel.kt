package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RoutineState> = MutableStateFlow(RoutineState.Init)
    val state: StateFlow<RoutineState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RoutineEvent> = MutableEventFlow()
    val event: EventFlow<RoutineEvent> = _event.asEventFlow()

    val initialData: String = ""

    fun onIntent(intent: RoutineIntent) {

    }
}
