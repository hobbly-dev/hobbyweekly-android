package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.result

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class RegisterResultArgument(
    val state: RegisterResultState,
    val event: EventFlow<RegisterResultEvent>,
    val intent: (RegisterResultIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface RegisterResultState {
    data object Init : RegisterResultState
    data object Loading : RegisterResultState
}


sealed interface RegisterResultEvent

sealed interface RegisterResultIntent
