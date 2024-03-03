package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.term

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.profile.RegisterProfileEvent
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.profile.RegisterProfileState

@HiltViewModel
class RegisterTermViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegisterTermState> = MutableStateFlow(RegisterTermState.Init)
    val state: StateFlow<RegisterTermState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegisterTermEvent> = MutableEventFlow()
    val event: EventFlow<RegisterTermEvent> = _event.asEventFlow()

    private val _termList: MutableStateFlow<List<Term>> = MutableStateFlow(emptyList())
    val termList: StateFlow<List<Term>> = _termList.asStateFlow()

    fun onIntent(intent: RegisterTermIntent) {
        when (intent) {
            is RegisterTermIntent.OnConfirm -> {
                patchTermState(intent.checkedTermList)
            }
        }
    }

    private fun patchTermState(
        checkedTermList: List<String>
    ) {
        launch {
            _state.value = RegisterTermState.Loading
            // TODO
            delay(1000L)
            _event.emit(RegisterTermEvent.PatchTerm.Success)
            _state.value = RegisterTermState.Init
        }
    }
}
