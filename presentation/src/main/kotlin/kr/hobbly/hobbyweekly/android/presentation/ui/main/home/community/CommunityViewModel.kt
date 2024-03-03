package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<CommunityState> = MutableStateFlow(CommunityState.Init)
    val state: StateFlow<CommunityState> = _state.asStateFlow()

    private val _event: MutableEventFlow<CommunityEvent> = MutableEventFlow()
    val event: EventFlow<CommunityEvent> = _event.asEventFlow()

    val initialData: String = ""

    fun onIntent(intent: CommunityIntent) {

    }
}
