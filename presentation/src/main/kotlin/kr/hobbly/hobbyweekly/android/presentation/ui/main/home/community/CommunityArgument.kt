package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kotlinx.coroutines.CoroutineExceptionHandler

@Immutable
data class CommunityArgument(
    val state: CommunityState,
    val event: EventFlow<CommunityEvent>,
    val intent: (CommunityIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface CommunityState {
    data object Init : CommunityState
    data object Loading : CommunityState
}


sealed interface CommunityEvent

sealed interface CommunityIntent
