package kr.hobbly.hobbyweekly.android.presentation.ui.main.home

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class HomeArgument(
    val state: HomeState,
    val event: EventFlow<HomeEvent>,
    val intent: (HomeIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface HomeState {
    data object Init : HomeState
}


sealed interface HomeEvent {
    data class ChangeHomeType(val homeType: HomeType) : HomeEvent
}

sealed interface HomeIntent {
    data class HomeTypeChange(val homeType: HomeType) : HomeIntent
}
