package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.popularblock

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class PopularBlockArgument(
    val state: PopularBlockState,
    val event: EventFlow<PopularBlockEvent>,
    val intent: (PopularBlockIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface PopularBlockState {
    data object Init : PopularBlockState
    data object Loading : PopularBlockState
}


sealed interface PopularBlockEvent

sealed interface PopularBlockIntent
