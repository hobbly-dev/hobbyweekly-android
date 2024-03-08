package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block

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
class BlockViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<BlockState> = MutableStateFlow(BlockState.Init)
    val state: StateFlow<BlockState> = _state.asStateFlow()

    private val _event: MutableEventFlow<BlockEvent> = MutableEventFlow()
    val event: EventFlow<BlockEvent> = _event.asEventFlow()

    val blockId: Long by lazy {
        savedStateHandle.get<Long>(BlockConstant.ROUTE_ARGUMENT_BLOCK_ID) ?: -1L
    }

    fun onIntent(intent: BlockIntent) {

    }
}
