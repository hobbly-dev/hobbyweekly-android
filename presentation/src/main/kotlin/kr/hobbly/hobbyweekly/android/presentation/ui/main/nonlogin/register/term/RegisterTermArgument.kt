package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.term

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class RegisterTermArgument(
    val state: RegisterTermState,
    val event: EventFlow<RegisterTermEvent>,
    val intent: (RegisterTermIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface RegisterTermState {
    data object Init : RegisterTermState
    data object Loading : RegisterTermState
}


sealed interface RegisterTermEvent {
    sealed interface PatchTerm : RegisterTermEvent {
        data object Success : PatchTerm
    }
}

sealed interface RegisterTermIntent {
    data class OnConfirm(val checkedTermList: List<String>) : RegisterTermIntent
}
