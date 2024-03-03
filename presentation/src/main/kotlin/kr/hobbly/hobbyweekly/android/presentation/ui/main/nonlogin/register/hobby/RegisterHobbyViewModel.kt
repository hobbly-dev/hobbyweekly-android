package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.hobby

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
class RegisterHobbyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegisterHobbyState> =
        MutableStateFlow(RegisterHobbyState.Init)
    val state: StateFlow<RegisterHobbyState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegisterHobbyEvent> = MutableEventFlow()
    val event: EventFlow<RegisterHobbyEvent> = _event.asEventFlow()

    private val _hobbyList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val hobbyList: StateFlow<List<String>> = _hobbyList.asStateFlow()

    init {
        _hobbyList.value = listOf(
            "축구",
            "뜨개질",
            "다도",
            "유도",
            "독서",
            "요리",
            "산책",
            "헬스",
            "미술",
            "개발",
            "여행",
            "회화",
            "전시회",
            "발표",
            "먹방",
            "등산"
        )
    }

    fun onIntent(intent: RegisterHobbyIntent) {
        when (intent) {
            is RegisterHobbyIntent.OnConfirm -> {
                patchHobby(intent.checkedHobbyList)
            }
        }
    }

    private fun patchHobby(checkedHobbyList: List<String>) {
        launch {
            _state.value = RegisterHobbyState.Loading
            // TODO
            _event.emit(RegisterHobbyEvent.PatchHobby.Success)
            _state.value = RegisterHobbyState.Init
        }
    }
}
