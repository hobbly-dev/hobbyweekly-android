package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

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
    sealed interface Load : PostEditEvent {
        data class Success(val post: kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post) :
            Load
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
        val originalImageUriList: List<String>,
        val newImageUriList: List<String>,
        val isSecret: Boolean,
        val isAnonymous: Boolean
    ) : PostEditIntent

    sealed interface EditNewImage : PostEditIntent {
        data class Remove(
            val item: String
        ) : EditNewImage
    }
}
