package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class BoardArgument(
    val state: BoardState,
    val event: EventFlow<BoardEvent>,
    val intent: (BoardIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface BoardState {
    data object Init : BoardState
    data object Loading : BoardState
}


sealed interface BoardEvent {

}

sealed interface BoardIntent
