package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.statistics

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.GetRoutineStatisticsListUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class MyPageStatisticsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRoutineStatisticsListUseCase: GetRoutineStatisticsListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageStatisticsState> =
        MutableStateFlow(MyPageStatisticsState.Init)
    val state: StateFlow<MyPageStatisticsState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyPageStatisticsEvent> = MutableEventFlow()
    val event: EventFlow<MyPageStatisticsEvent> = _event.asEventFlow()

    private val _routineStatisticsList: MutableStateFlow<List<RoutineStatistics>> =
        MutableStateFlow(emptyList())
    val routineStatisticsList: StateFlow<List<RoutineStatistics>> =
        _routineStatisticsList.asStateFlow()

    fun onIntent(intent: MyPageStatisticsIntent) {
        when (intent) {
            is MyPageStatisticsIntent.OnDateChanged -> {
                getRoutineStatisticsList(intent.date)
            }
        }
    }

    private fun getRoutineStatisticsList(
        date: LocalDate
    ) {
        launch {
            getRoutineStatisticsListUseCase(
                id = -1,
                date = date
            ).onSuccess { routineStatisticsList ->
                _state.value = MyPageStatisticsState.Init

                _routineStatisticsList.value = routineStatisticsList
            }.onFailure { exception ->
                _state.value = MyPageStatisticsState.Init
                when (exception) {
                    is ServerException -> {
                        _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                    }

                    else -> {
                        _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                    }
                }
            }
        }
    }
}
