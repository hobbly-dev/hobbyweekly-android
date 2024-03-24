package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.zip
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardComment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment.LikeCommentUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment.RemoveCommentUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment.ReportCommentUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment.WriteCommentReplyUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment.board.LoadBoardCommentPagingUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment.board.WriteBoardCommentUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board.LikeBoardPostUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board.LoadBoardPostUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board.RemoveBoardPostUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board.ReportBoardPostUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetProfileUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class PostViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loadBlockPostUseCase: LoadBoardPostUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val loadBoardCommentPagingUseCase: LoadBoardCommentPagingUseCase,
    private val likeBoardPostUseCase: LikeBoardPostUseCase,
    private val removeBoardPostUseCase: RemoveBoardPostUseCase,
    private val reportBoardPostUseCase: ReportBoardPostUseCase,
    private val writeBoardCommentUseCase: WriteBoardCommentUseCase,
    private val writeCommentReplyUseCase: WriteCommentReplyUseCase,
    private val likeCommentUseCase: LikeCommentUseCase,
    private val removeCommentUseCase: RemoveCommentUseCase,
    private val reportCommentUseCase: ReportCommentUseCase
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

    private val _post: MutableStateFlow<BoardPost> = MutableStateFlow(BoardPost.empty)
    val post: StateFlow<BoardPost> = _post.asStateFlow()

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(Profile.empty)
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    private val _commentPaging: MutableStateFlow<PagingData<BoardComment>> =
        MutableStateFlow(PagingData.empty())
    val commentPaging: StateFlow<PagingData<BoardComment>> = _commentPaging.asStateFlow()

    init {
        refresh()
    }

    fun onIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.Post.OnLike -> {
                likePost()
            }

            is PostIntent.Post.OnRemove -> {
                removePost()
            }

            is PostIntent.Post.OnReport -> {
                reportPost(intent.reason)
            }

            is PostIntent.Comment.OnComment -> {
                if (intent.parentId == -1L) {
                    comment(intent.commentText, intent.isAnonymous)
                } else {
                    reply(intent.parentId, intent.commentText, intent.isAnonymous)
                }
            }

            is PostIntent.Comment.OnLike -> {
                likeComment(intent.commentId)
            }

            is PostIntent.Comment.OnRemove -> {
                removeComment(intent.commentId)
            }

            is PostIntent.Comment.OnReport -> {
                reportComment(intent.commentId, intent.reason)
            }
        }
    }

    private fun refresh() {
        launch {
            loadBoardCommentPagingUseCase(id = postId)
                .cachedIn(viewModelScope)
                .catch { exception ->
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }.collect { commentPaging ->
                    _commentPaging.value = commentPaging
                }

            _state.value = PostState.Loading
            zip(
                { loadBlockPostUseCase(id = postId) },
                { getProfileUseCase() },
            ).onSuccess { (post, profile) ->
                _state.value = PostState.Init

                _post.value = post
                _profile.value = profile
            }.onFailure { exception ->
                _state.value = PostState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    private fun likePost() {
        launch {
            likeBoardPostUseCase(
                id = postId
            ).onSuccess {
                _state.value = PostState.Init
            }.onFailure { exception ->
                _state.value = PostState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    private fun removePost() {
        launch {
            removeBoardPostUseCase(
                id = postId
            ).onSuccess {
                _state.value = PostState.Init
                _event.emit(PostEvent.Post.Remove.Success)
            }.onFailure { exception ->
                _state.value = PostState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    private fun reportPost(
        reason: String
    ) {
        launch {
            reportBoardPostUseCase(
                id = postId,
                reason = reason
            ).onSuccess {
                _state.value = PostState.Init
                _event.emit(PostEvent.Post.Report.Success)
            }.onFailure { exception ->
                _state.value = PostState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    private fun comment(
        commentText: String,
        isAnonymous: Boolean
    ) {
        launch {
            writeBoardCommentUseCase(
                id = postId,
                content = commentText,
                isAnonymous = isAnonymous
            ).onSuccess { id ->
                _state.value = PostState.Init
                _event.emit(PostEvent.Comment.Write.Success(-1)) // TODO
            }.onFailure { exception ->
                _state.value = PostState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    private fun reply(
        parentId: Long,
        commentText: String,
        isAnonymous: Boolean
    ) {
        launch {
            writeCommentReplyUseCase(
                id = parentId,
                content = commentText,
                isAnonymous = isAnonymous
            ).onSuccess { id ->
                _state.value = PostState.Init
                _event.emit(PostEvent.Comment.Write.Success(-1)) // TODO
            }.onFailure { exception ->
                _state.value = PostState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    private fun likeComment(
        commentId: Long
    ) {
        launch {
            likeCommentUseCase(
                id = commentId
            ).onSuccess {
                _state.value = PostState.Init
            }.onFailure { exception ->
                _state.value = PostState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    private fun removeComment(
        commentId: Long
    ) {
        launch {
            removeCommentUseCase(
                id = commentId
            ).onSuccess {
                _state.value = PostState.Init
                _event.emit(PostEvent.Comment.Remove.Success)
            }.onFailure { exception ->
                _state.value = PostState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }

    private fun reportComment(
        commentId: Long,
        reason: String
    ) {
        launch {
            reportCommentUseCase(
                id = commentId,
                reason = reason
            ).onSuccess {
                _state.value = PostState.Init
                _event.emit(PostEvent.Comment.Report.Success)
            }.onFailure { exception ->
                _state.value = PostState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }
}
