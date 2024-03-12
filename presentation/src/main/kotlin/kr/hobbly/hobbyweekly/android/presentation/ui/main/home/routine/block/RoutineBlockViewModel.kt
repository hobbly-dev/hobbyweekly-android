package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.block

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
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

@HiltViewModel
class RoutineBlockViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RoutineBlockState> =
        MutableStateFlow(RoutineBlockState.Init)
    val state: StateFlow<RoutineBlockState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RoutineBlockEvent> = MutableEventFlow()
    val event: EventFlow<RoutineBlockEvent> = _event.asEventFlow()

    private val _myBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val myBlockList: StateFlow<List<Block>> = _myBlockList.asStateFlow()

    fun onIntent(intent: RoutineBlockIntent) {

    }
}
