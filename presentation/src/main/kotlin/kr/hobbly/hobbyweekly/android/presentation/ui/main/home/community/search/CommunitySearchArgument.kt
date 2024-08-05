package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.search

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class CommunitySearchArgument(
    val state: CommunitySearchState,
    val event: EventFlow<CommunitySearchEvent>,
    val intent: (CommunitySearchIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface CommunitySearchState {
    data object Init : CommunitySearchState
    data object Loading : CommunitySearchState
    data object SearchResult : CommunitySearchState
}


sealed interface CommunitySearchEvent

sealed interface CommunitySearchIntent {
    data class Search(val keyword: String) : CommunitySearchIntent
}
