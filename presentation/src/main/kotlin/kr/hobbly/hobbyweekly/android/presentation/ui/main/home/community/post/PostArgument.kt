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

sealed interface PostIntent {
    sealed interface Post : PostIntent {
        data class OnLike(val postId: Long) : Post
        data class OnDelete(val postId: Long) : Post
        data class OnReport(val postId: Long) : Post
    }

    sealed interface Comment : PostIntent {
        data class OnComment(val commentText: String, val isAnonymous: Boolean) : Comment
        data class OnLike(val commentId: Long) : Comment
        data class OnEdit(val commentId: Long) : Comment
        data class OnDelete(val commentId: Long) : Comment
        data class OnReport(val commentId: Long) : Comment
    }
}
