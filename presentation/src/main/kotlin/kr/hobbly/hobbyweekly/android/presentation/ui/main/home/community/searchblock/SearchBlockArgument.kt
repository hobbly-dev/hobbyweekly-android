package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.searchblock

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class SearchBlockArgument(
    val state: SearchBlockState,
    val event: EventFlow<SearchBlockEvent>,
    val intent: (SearchBlockIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface SearchBlockState {
    data object Init : SearchBlockState
    data object Loading : SearchBlockState
    data object SearchResult : SearchBlockState
}


sealed interface SearchBlockEvent

sealed interface SearchBlockIntent {
    data class Search(val keyword: String) : SearchBlockIntent
}
