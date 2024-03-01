package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.entry

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
class RegisterEntryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegisterEntryState> =
        MutableStateFlow(RegisterEntryState.Init)
    val state: StateFlow<RegisterEntryState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegisterEntryEvent> = MutableEventFlow()
    val event: EventFlow<RegisterEntryEvent> = _event.asEventFlow()

    fun onIntent(intent: RegisterEntryIntent) {

    }
}
