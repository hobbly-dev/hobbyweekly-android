package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.entry

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class RegisterEntryArgument(
    val state: RegisterEntryState,
    val event: EventFlow<RegisterEntryEvent>,
    val intent: (RegisterEntryIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface RegisterEntryState {
    data object Init : RegisterEntryState
    data object Loading : RegisterEntryState
}


sealed interface RegisterEntryEvent

sealed interface RegisterEntryIntent
