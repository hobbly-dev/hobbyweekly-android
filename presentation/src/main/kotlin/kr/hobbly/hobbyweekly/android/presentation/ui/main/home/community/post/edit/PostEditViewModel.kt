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

@HiltViewModel
class PostEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<PostEditState> = MutableStateFlow(PostEditState.Init)
    val state: StateFlow<PostEditState> = _state.asStateFlow()

    private val _event: MutableEventFlow<PostEditEvent> = MutableEventFlow()
    val event: EventFlow<PostEditEvent> = _event.asEventFlow()

    val initialData = ""

    fun onIntent(intent: PostEditIntent) {

    }
}
