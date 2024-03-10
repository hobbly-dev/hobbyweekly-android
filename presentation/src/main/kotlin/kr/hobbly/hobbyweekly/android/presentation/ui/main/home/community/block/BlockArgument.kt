package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class BlockArgument(
    val state: BlockState,
    val event: EventFlow<BlockEvent>,
    val intent: (BlockIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface BlockState {
    data object Init : BlockState
    data object Loading : BlockState
}


sealed interface BlockEvent {
    sealed interface RemoveBlock : BlockEvent {
        data object Success : RemoveBlock
    }
}

sealed interface BlockIntent {
    data object OnRemove : BlockIntent
}
