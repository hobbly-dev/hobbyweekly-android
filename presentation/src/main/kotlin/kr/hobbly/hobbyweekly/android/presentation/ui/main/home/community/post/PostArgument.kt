package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class PostArgument(
    val state: PostState,
    val event: EventFlow<PostEvent>,
    val intent: (PostIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface PostState {
    data object Init : PostState
    data object Loading : PostState
}


sealed interface PostEvent

sealed interface PostIntent
