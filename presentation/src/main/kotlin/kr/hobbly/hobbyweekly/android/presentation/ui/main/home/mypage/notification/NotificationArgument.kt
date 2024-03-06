package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class NotificationArgument(
    val state: NotificationState,
    val event: EventFlow<NotificationEvent>,
    val intent: (NotificationIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface NotificationState {
    data object Init : NotificationState
    data object Loading : NotificationState
}


sealed interface NotificationEvent

sealed interface NotificationIntent
