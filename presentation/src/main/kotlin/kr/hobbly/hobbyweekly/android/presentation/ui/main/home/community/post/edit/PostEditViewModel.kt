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
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@HiltViewModel
class PostEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
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

    init {
        if (postId != -1L) {
            load()
        }
    }

    fun onIntent(intent: PostEditIntent) {
        when (intent) {
            is PostEditIntent.OnPost -> {
                if (postId == -1L) {
                    edit(
                        title = intent.title,
                        content = intent.content,
                        imageList = intent.imageList,
                        isSecret = intent.isSecret,
                        isAnonymous = intent.isAnonymous
                    )
                } else {
                    post(
                        title = intent.title,
                        content = intent.content,
                        imageList = intent.imageList,
                        isSecret = intent.isSecret,
                        isAnonymous = intent.isAnonymous
                    )
                }
            }
        }
    }

    private fun load() {
        launch {
            _state.value = PostEditState.Loading

            _state.value = PostEditState.Init
        }
    }

    private fun edit(
        title: String,
        content: String,
        imageList: List<GalleryImage>,
        isSecret: Boolean,
        isAnonymous: Boolean
    ) {
        launch {
            _state.value = PostEditState.Loading

            _event.emit(PostEditEvent.Edit.Success)
            _state.value = PostEditState.Init
        }
    }

    private fun post(
        title: String,
        content: String,
        imageList: List<GalleryImage>,
        isSecret: Boolean,
        isAnonymous: Boolean
    ) {
        launch {
            _state.value = PostEditState.Loading

            _event.emit(PostEditEvent.Post.Success)
            _state.value = PostEditState.Init
        }
    }
}
