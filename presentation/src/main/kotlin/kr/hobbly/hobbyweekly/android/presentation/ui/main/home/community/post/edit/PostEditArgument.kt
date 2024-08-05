package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@Immutable
data class PostEditArgument(
    val state: PostEditState,
    val event: EventFlow<PostEditEvent>,
    val intent: (PostEditIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface PostEditState {
    data object Init : PostEditState
    data object Loading : PostEditState
}


sealed interface PostEditEvent {
    sealed interface Load : PostEditEvent {
        data class Success(val post: kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post) : Load
    }

    sealed interface Post : PostEditEvent {
        data class Success(val id: Long) : Post
    }

    sealed interface Edit : PostEditEvent {
        data object Success : Edit
    }
}

sealed interface PostEditIntent {
    data class OnPost(
        val title: String,
        val content: String,
        val originalImageList: List<String>,
        val newImageList: List<GalleryImage>,
        val isSecret: Boolean,
        val isAnonymous: Boolean
    ) : PostEditIntent
}
