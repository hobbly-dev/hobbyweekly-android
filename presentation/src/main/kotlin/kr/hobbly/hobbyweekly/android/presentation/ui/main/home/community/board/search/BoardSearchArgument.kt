package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.search

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class BoardSearchArgument(
    val state: BoardSearchState,
    val event: EventFlow<BoardSearchEvent>,
    val intent: (BoardSearchIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface BoardSearchState {
    data object Init : BoardSearchState
    data object Loading : BoardSearchState
}


sealed interface BoardSearchEvent

sealed interface BoardSearchIntent {
    data class Search(val keyword: String) : BoardSearchIntent
}
