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

@HiltViewModel
class RegisterTermViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegisterTermState> =
        MutableStateFlow(RegisterTermState.Init)
    val state: StateFlow<RegisterTermState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegisterTermEvent> = MutableEventFlow()
    val event: EventFlow<RegisterTermEvent> = _event.asEventFlow()

    private val _termList: MutableStateFlow<List<Term>> = MutableStateFlow(emptyList())
    val termList: StateFlow<List<Term>> = _termList.asStateFlow()

    init {
        _termList.value = listOf(
            Term(
                id = "AA001",
                title = "개인정보 수집 이용동의",
                link = "https://www.naver.com",
                isNecessary = true
            ),
            Term(
                id = "AA002",
                title = "고유식별 정보처리 동의",
                link = "https://www.naver.com",
                isNecessary = true
            ),
            Term(
                id = "AA003",
                title = "통신사 이용약관 동의",
                link = "https://www.naver.com",
                isNecessary = true
            ),
            Term(
                id = "AA004",
                title = "14세 이상 동의",
                link = "",
                isNecessary = false
            )
        )
    }

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
