package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.search

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class CommunitySearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<CommunitySearchState> =
        MutableStateFlow(CommunitySearchState.Init)
    val state: StateFlow<CommunitySearchState> = _state.asStateFlow()

    private val _event: MutableEventFlow<CommunitySearchEvent> = MutableEventFlow()
    val event: EventFlow<CommunitySearchEvent> = _event.asEventFlow()

    private val _suggestBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val suggestBlockList: StateFlow<List<Block>> = _suggestBlockList.asStateFlow()

    private val _searchBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val searchBlockList: StateFlow<List<Block>> = _searchBlockList.asStateFlow()

    fun onIntent(intent: CommunitySearchIntent) {
        when (intent) {
            is CommunitySearchIntent.Search -> {
                search(intent.keyword)
            }
        }
    }

    private fun search(keyword: String) {
        launch {
            _state.value = CommunitySearchState.Loading

            _state.value = CommunitySearchState.SearchResult
        }
    }
}
