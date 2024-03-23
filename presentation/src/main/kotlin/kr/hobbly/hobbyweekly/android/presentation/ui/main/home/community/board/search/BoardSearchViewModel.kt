package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.search

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class BoardSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<BoardSearchState> =
        MutableStateFlow(BoardSearchState.Init)
    val state: StateFlow<BoardSearchState> = _state.asStateFlow()

    private val _event: MutableEventFlow<BoardSearchEvent> = MutableEventFlow()
    val event: EventFlow<BoardSearchEvent> = _event.asEventFlow()

    private val _searchPostList: MutableStateFlow<List<BoardPost>> = MutableStateFlow(emptyList())
    val searchPostList: StateFlow<List<BoardPost>> = _searchPostList.asStateFlow()

    fun onIntent(intent: BoardSearchIntent) {
        when (intent) {
            is BoardSearchIntent.Search -> {
                search(intent.keyword)
            }
        }
    }

    private fun search(keyword: String) {
        launch {
            _state.value = BoardSearchState.Loading

            _state.value = BoardSearchState.Init
        }
    }
}
