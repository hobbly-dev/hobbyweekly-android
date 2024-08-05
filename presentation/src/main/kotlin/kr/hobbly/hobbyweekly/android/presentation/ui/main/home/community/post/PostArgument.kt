package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class PostArgument(
    val state: PostState,
    val event: EventFlow<PostEvent>,
    val intent: (PostIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface PostState {
    data object Init : PostState
    data object Loading : PostState
}


sealed interface PostEvent {
    sealed interface Post : PostEvent {
        sealed interface Remove : Post {
            data object Success : Remove
        }

        sealed interface Block : Post {
            data object Success : Block
        }

        sealed interface Report : Post {
            data object Success : Report
        }
    }

    sealed interface Comment : PostEvent {
        sealed interface Write : Comment {
            data class Success(val id: Long) : Write
        }

        sealed interface Remove : Comment {
            data object Success : Remove
        }

        sealed interface Block : Comment {
            data object Success : Block
        }

        sealed interface Report : Comment {
            data object Success : Report
        }
    }
}

sealed interface PostIntent {
    sealed interface Post : PostIntent {
        data object OnLike : Post
        data object OnRemove : Post
        data object OnBlock : Post
        data class OnReport(val reason: String) : Post
        data object Refresh : Post
    }

    sealed interface Comment : PostIntent {
        data class OnComment(
            val parentId: Long,
            val commentText: String,
            val isAnonymous: Boolean
        ) : Comment

        data class OnLike(val commentId: Long) : Comment
        data class OnRemove(val commentId: Long) : Comment
        data class OnBlock(val userId: Long) : Comment
        data class OnReport(val commentId: Long, val reason: String) : Comment
        data object Refresh : Comment
    }
}
