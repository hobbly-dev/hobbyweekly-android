package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.entry

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class RegisterEntryArgument(
    val state: RegisterEntryState,
    val event: EventFlow<RegisterEntryEvent>,
    val intent: (RegisterEntryIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface RegisterEntryState {
    data object Init : RegisterEntryState
    data object Loading : RegisterEntryState
}


sealed interface RegisterEntryEvent {
    data object NeedNickname : RegisterEntryEvent
    data object NeedTermAgreement : RegisterEntryEvent
    data object NeedHobbyList : RegisterEntryEvent
    data object NoProblem : RegisterEntryEvent
}

sealed interface RegisterEntryIntent
