package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

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
class CommunityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<CommunityState> = MutableStateFlow(CommunityState.Init)
    val state: StateFlow<CommunityState> = _state.asStateFlow()

    private val _event: MutableEventFlow<CommunityEvent> = MutableEventFlow()
    val event: EventFlow<CommunityEvent> = _event.asEventFlow()

    private val _communityData: MutableStateFlow<CommunityData> = MutableStateFlow(
        CommunityData(
            myBlockList = listOf(),
            popularBlockList = listOf(),
            popularPostList = listOf()
        )
    )
    val communityData: StateFlow<CommunityData> = _communityData.asStateFlow()

    fun onIntent(intent: CommunityIntent) {

    }
}
