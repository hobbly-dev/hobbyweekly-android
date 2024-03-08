package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.popularblock

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
class PopularBlockViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<PopularBlockState> =
        MutableStateFlow(PopularBlockState.Init)
    val state: StateFlow<PopularBlockState> = _state.asStateFlow()

    private val _event: MutableEventFlow<PopularBlockEvent> = MutableEventFlow()
    val event: EventFlow<PopularBlockEvent> = _event.asEventFlow()

    val initialData: String = ""

    fun onIntent(intent: PopularBlockIntent) {

    }
}
