package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.term

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
class RegisterTermViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegisterTermState> =
        MutableStateFlow(RegisterTermState.Init)
    val state: StateFlow<RegisterTermState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegisterTermEvent> = MutableEventFlow()
    val event: EventFlow<RegisterTermEvent> = _event.asEventFlow()

    fun onIntent(intent: RegisterTermIntent) {

    }
}
