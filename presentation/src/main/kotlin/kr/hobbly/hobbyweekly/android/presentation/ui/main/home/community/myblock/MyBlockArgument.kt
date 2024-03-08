package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.myblock

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class MyBlockArgument(
    val state: MyBlockState,
    val event: EventFlow<MyBlockEvent>,
    val intent: (MyBlockIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface MyBlockState {
    data object Init : MyBlockState
    data object Loading : MyBlockState
}


sealed interface MyBlockEvent

sealed interface MyBlockIntent
