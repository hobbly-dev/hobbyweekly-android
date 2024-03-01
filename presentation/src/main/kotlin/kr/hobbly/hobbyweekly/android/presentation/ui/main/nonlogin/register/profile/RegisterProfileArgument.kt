package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.profile

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class RegisterProfileArgument(
    val state: RegisterProfileState,
    val event: EventFlow<RegisterProfileEvent>,
    val intent: (RegisterProfileIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface RegisterProfileState {
    data object Init : RegisterProfileState
    data object Loading : RegisterProfileState
}


sealed interface RegisterProfileEvent

sealed interface RegisterProfileIntent
