package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

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

    fun onIntent(intent: PostIntent) {

    }
}
