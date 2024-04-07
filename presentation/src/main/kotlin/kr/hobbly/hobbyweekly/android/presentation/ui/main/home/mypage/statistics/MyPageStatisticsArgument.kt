package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.statistics

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kotlinx.coroutines.CoroutineExceptionHandler

@Immutable
data class MyPageStatisticsArgument(
    val state: MyPageStatisticsState,
    val event: EventFlow<MyPageStatisticsEvent>,
    val intent: (MyPageStatisticsIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface MyPageStatisticsState {
    data object Init : MyPageStatisticsState
    data object Loading : MyPageStatisticsState
}


sealed interface MyPageStatisticsEvent

sealed interface MyPageStatisticsIntent
