package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.zip
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetBlockUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.board.GetBoardUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.EditPostUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.LoadPostUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.WritePostUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.WriteRoutinePostUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.file.GetUrlAndUploadImageUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@HiltViewModel
class PostEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBlockUseCase: GetBlockUseCase,
    private val getBoardUseCase: GetBoardUseCase,
    private val loadPostUseCase: LoadPostUseCase,
    private val getUrlAndUploadImageUseCase: GetUrlAndUploadImageUseCase,
    private val writePostUseCase: WritePostUseCase,
    private val writeRoutinePostUseCase: WriteRoutinePostUseCase,
    private val editPostUseCase: EditPostUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<PostEditState> = MutableStateFlow(PostEditState.Init)
    val state: StateFlow<PostEditState> = _state.asStateFlow()

    private val _event: MutableEventFlow<PostEditEvent> = MutableEventFlow()
    val event: EventFlow<PostEditEvent> = _event.asEventFlow()

    val blockId: Long by lazy {
        savedStateHandle.get<Long>(PostEditConstant.ROUTE_ARGUMENT_BLOCK_ID) ?: -1L
    }

    val boardId: Long by lazy {
        savedStateHandle.get<Long>(PostEditConstant.ROUTE_ARGUMENT_BOARD_ID) ?: -1L
    }

    val postId: Long by lazy {
        savedStateHandle.get<Long>(PostEditConstant.ROUTE_ARGUMENT_POST_ID) ?: -1L
    }

    val routineId: Long by lazy {
        savedStateHandle.get<Long>(PostEditConstant.ROUTE_ARGUMENT_ROUTINE_ID) ?: -1L
    }

    private val _block: MutableStateFlow<Block> = MutableStateFlow(Block.empty)
    val block: StateFlow<Block> = _block.asStateFlow()

    private val _board: MutableStateFlow<Board> = MutableStateFlow(Board.empty)
    val board: StateFlow<Board> = _board.asStateFlow()

    init {
        launch {
            _state.value = PostEditState.Loading

            zip(
                { getBlockUseCase(id = blockId) },
                { getBoardUseCase(id = boardId) }
            ).onSuccess { (block, board) ->
                _state.value = PostEditState.Init
                _block.value = block
                _board.value = board

                if (postId != -1L) {
                    load()
                }
            }.onFailure { exception ->
                _state.value = PostEditState.Init
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

    fun onIntent(intent: PostEditIntent) {
        when (intent) {
            is PostEditIntent.OnPost -> {
                if (postId == -1L) {
                    post(
                        title = intent.title,
                        content = intent.content,
                        originalImageList = intent.originalImageList,
                        newImageList = intent.newImageList,
                        isSecret = intent.isSecret,
                        isAnonymous = intent.isAnonymous
                    )
                } else {
                    edit(
                        title = intent.title,
                        content = intent.content,
                        originalImageList = intent.originalImageList,
                        newImageList = intent.newImageList,
                        isSecret = intent.isSecret,
                        isAnonymous = intent.isAnonymous
                    )
                }
            }
        }
    }

    private suspend fun load() {
        _state.value = PostEditState.Loading

        loadPostUseCase(
            id = postId
        ).onSuccess { post ->
            _state.value = PostEditState.Init

            _event.emit(PostEditEvent.Load.Success(post))
        }.onFailure { exception ->
            _state.value = PostEditState.Init
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

    private fun edit(
        title: String,
        content: String,
        originalImageList: List<String>,
        newImageList: List<GalleryImage>,
        isSecret: Boolean,
        isAnonymous: Boolean
    ) {
        launch {
            _state.value = PostEditState.Loading

            getUrlAndUploadImageUseCase(
                imageUriList = newImageList.map { it.filePath }
            ).onSuccess { newImageList ->
                editPostUseCase(
                    id = postId,
                    title = title,
                    content = content,
                    imageList = originalImageList + newImageList,
                    isSecret = isSecret,
                    isAnonymous = isAnonymous
                ).onSuccess {
                    _state.value = PostEditState.Init
                    _event.emit(PostEditEvent.Edit.Success)
                }.getOrThrow()
            }.onFailure { exception ->
                _state.value = PostEditState.Init
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

    private fun post(
        title: String,
        content: String,
        originalImageList: List<String>,
        newImageList: List<GalleryImage>,
        isSecret: Boolean,
        isAnonymous: Boolean
    ) {
        launch {
            _state.value = PostEditState.Loading

            getUrlAndUploadImageUseCase(
                imageUriList = newImageList.map { it.filePath }
            ).onSuccess { newImageList ->
                if (routineId == -1L) {
                    writePostUseCase(
                        id = boardId,
                        title = title,
                        content = content,
                        imageList = originalImageList + newImageList,
                        isSecret = isSecret,
                        isAnonymous = isAnonymous
                    ).onSuccess { id ->
                        _state.value = PostEditState.Init
                        _event.emit(PostEditEvent.Post.Success(id))
                    }.getOrThrow()
                } else {
                    writeRoutinePostUseCase(
                        id = routineId,
                        title = title,
                        content = content,
                        imageList = originalImageList + newImageList,
                        isSecret = isSecret,
                        isAnonymous = isAnonymous
                    ).onSuccess { id ->
                        _state.value = PostEditState.Init
                        _event.emit(PostEditEvent.Post.Success(id))
                    }.getOrThrow()
                }
            }.onFailure { exception ->
                _state.value = PostEditState.Init
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
