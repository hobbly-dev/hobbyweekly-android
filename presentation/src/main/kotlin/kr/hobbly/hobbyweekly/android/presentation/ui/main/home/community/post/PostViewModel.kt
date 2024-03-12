package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Comment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class PostViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<PostState> = MutableStateFlow(PostState.Init)
    val state: StateFlow<PostState> = _state.asStateFlow()

    private val _event: MutableEventFlow<PostEvent> = MutableEventFlow()
    val event: EventFlow<PostEvent> = _event.asEventFlow()

    val blockId: Long by lazy {
        savedStateHandle.get<Long>(PostConstant.ROUTE_ARGUMENT_BLOCK_ID) ?: -1L
    }

    val boardId: Long by lazy {
        savedStateHandle.get<Long>(PostConstant.ROUTE_ARGUMENT_BOARD_ID) ?: -1L
    }

    val postId: Long by lazy {
        savedStateHandle.get<Long>(PostConstant.ROUTE_ARGUMENT_POST_ID) ?: -1L
    }

    private val _post: MutableStateFlow<Post> = MutableStateFlow(Post.empty)
    val post: StateFlow<Post> = _post.asStateFlow()

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(Profile.empty)
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    private val _commentList: MutableStateFlow<PagingData<Comment>> =
        MutableStateFlow(PagingData.empty())
    val commentList: StateFlow<PagingData<Comment>> = _commentList.asStateFlow()

    fun onIntent(intent: PostIntent) {
//        when (intent) {
//            is PostIntent.Post.OnLike -> {
//                onPostLike(intent.postId)
//            }
//            is PostIntent.Post.OnDelete -> {
//                onPostDelete(intent.postId)
//            }
//            is PostIntent.Post.OnReport -> {
//                onPostReport(intent.postId)
//            }
//            is PostIntent.Comment.OnComment -> {
//                onCommentComment(intent.commentId)
//            }
//            is PostIntent.Comment.OnLike -> {
//                onCommentLike(intent.commentId)
//            }
//            is PostIntent.Comment.OnEdit -> {
//                onCommentEdit(intent.commentId)
//            }
//            is PostIntent.Comment.OnDelete -> {
//                onCommentDelete(intent.commentId)
//            }
//            is PostIntent.Comment.OnReport -> {
//                onCommentReport(intent.commentId)
//            }
//        }
    }
}
