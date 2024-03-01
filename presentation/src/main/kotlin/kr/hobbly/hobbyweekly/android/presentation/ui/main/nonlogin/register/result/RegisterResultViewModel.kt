package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.result

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class RegisterResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegisterResultState> =
        MutableStateFlow(RegisterResultState.Init)
    val state: StateFlow<RegisterResultState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegisterResultEvent> = MutableEventFlow()
    val event: EventFlow<RegisterResultEvent> = _event.asEventFlow()

    fun onIntent(intent: RegisterResultIntent) {

    }
}
