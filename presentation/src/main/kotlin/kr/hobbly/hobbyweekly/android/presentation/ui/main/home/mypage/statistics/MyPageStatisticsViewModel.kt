package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.statistics

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
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics

@HiltViewModel
class MyPageStatisticsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageStatisticsState> =
        MutableStateFlow(MyPageStatisticsState.Init)
    val state: StateFlow<MyPageStatisticsState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyPageStatisticsEvent> = MutableEventFlow()
    val event: EventFlow<MyPageStatisticsEvent> = _event.asEventFlow()

    private val _routineStatistics: MutableStateFlow<List<RoutineStatistics>> =
        MutableStateFlow(emptyList())
    val routineStatisticsList: StateFlow<List<RoutineStatistics>> = _routineStatistics.asStateFlow()

    fun onIntent(intent: MyPageStatisticsIntent) {

    }
}
