package kr.hobbly.hobbyweekly.android.presentation.ui.main.home

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
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Init)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HomeEvent> = MutableEventFlow()
    val event: EventFlow<HomeEvent> = _event.asEventFlow()

    val initialHomeType: HomeType by lazy {
        val route = savedStateHandle.get<String>(HomeConstant.ROUTE_ARGUMENT_SCREEN)
        HomeType.values().firstOrNull { it.route == route } ?: HomeType.values().first()
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.HomeTypeChange -> {
                launch {
                    _event.emit(HomeEvent.ChangeHomeType(intent.homeType))
                }
            }
        }
    }
}
