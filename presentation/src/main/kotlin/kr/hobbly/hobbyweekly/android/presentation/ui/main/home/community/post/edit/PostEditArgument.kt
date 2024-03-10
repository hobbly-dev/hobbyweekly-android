package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@Immutable
data class PostEditArgument(
    val state: PostEditState,
    val event: EventFlow<PostEditEvent>,
    val intent: (PostEditIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface PostEditState {
    data object Init : PostEditState
    data object Loading : PostEditState
}


sealed interface PostEditEvent {
    sealed interface Post : PostEditEvent {
        data object Success : Post
    }

    sealed interface Edit : PostEditEvent {
        data object Success : Edit
    }
}

sealed interface PostEditIntent {
    data class OnPost(
        val title: String,
        val content: String,
        val imageList: List<GalleryImage>,
        val isSecret: Boolean,
        val isAnonymous: Boolean
    ) : PostEditIntent
}
