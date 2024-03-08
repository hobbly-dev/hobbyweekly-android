package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.myblock

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
class MyBlockViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyBlockState> =
        MutableStateFlow(MyBlockState.Init)
    val state: StateFlow<MyBlockState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyBlockEvent> = MutableEventFlow()
    val event: EventFlow<MyBlockEvent> = _event.asEventFlow()

    val initialData: String = ""

    fun onIntent(intent: MyBlockIntent) {

    }
}
