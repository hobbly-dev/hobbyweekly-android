package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board.SearchBoardPostUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class BoardSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchBoardPostUseCase: SearchBoardPostUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<BoardSearchState> =
        MutableStateFlow(BoardSearchState.Init)
    val state: StateFlow<BoardSearchState> = _state.asStateFlow()

    private val _event: MutableEventFlow<BoardSearchEvent> = MutableEventFlow()
    val event: EventFlow<BoardSearchEvent> = _event.asEventFlow()

    val blockId: Long by lazy {
        savedStateHandle.get<Long>(BoardSearchConstant.ROUTE_ARGUMENT_BLOCK_ID) ?: -1L
    }

    val boardId: Long by lazy {
        savedStateHandle.get<Long>(BoardSearchConstant.ROUTE_ARGUMENT_BOARD_ID) ?: -1L
    }

    private val _searchPostPaging: MutableStateFlow<PagingData<BoardPost>> =
        MutableStateFlow(PagingData.empty())
    val searchPostPaging: StateFlow<PagingData<BoardPost>> = _searchPostPaging.asStateFlow()

    fun onIntent(intent: BoardSearchIntent) {
        when (intent) {
            is BoardSearchIntent.Search -> {
                search(intent.keyword)
            }
        }
    }

    private fun search(keyword: String) {
        launch {
            // TODO : Paging State 에 따라서 보여주기
            searchBoardPostUseCase(id = boardId, keyword = keyword)
                .cachedIn(viewModelScope)
                .onStart {
                    _state.value = BoardSearchState.Loading
                }.catch { exception ->
                    _state.value = BoardSearchState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }.collect { searchPostPaging ->
                    _state.value = BoardSearchState.Init
                    _searchPostPaging.value = searchPostPaging
                }
        }
    }
}
