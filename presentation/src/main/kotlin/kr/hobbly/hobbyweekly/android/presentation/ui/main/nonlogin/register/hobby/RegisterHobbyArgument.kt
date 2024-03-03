package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.hobby

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class RegisterHobbyArgument(
    val state: RegisterHobbyState,
    val event: EventFlow<RegisterHobbyEvent>,
    val intent: (RegisterHobbyIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface RegisterHobbyState {
    data object Init : RegisterHobbyState
    data object Loading : RegisterHobbyState
}


sealed interface RegisterHobbyEvent {
    sealed interface PatchHobby : RegisterHobbyEvent {
        data object Success : PatchHobby
    }
}

sealed interface RegisterHobbyIntent {
    data class OnConfirm(val checkedHobbyList: List<String>) : RegisterHobbyIntent
}
