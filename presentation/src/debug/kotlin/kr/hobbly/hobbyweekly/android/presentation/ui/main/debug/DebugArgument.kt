package kr.hobbly.hobbyweekly.android.presentation.ui.main.debug

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class DebugArgument(
    val state: DebugState,
    val event: EventFlow<DebugEvent>,
    val intent: (DebugIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface DebugState {
    data object Init : DebugState
}


sealed interface DebugEvent

sealed interface DebugIntent
