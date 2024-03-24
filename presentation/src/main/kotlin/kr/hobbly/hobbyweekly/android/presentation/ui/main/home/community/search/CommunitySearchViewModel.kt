package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.search

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
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetRecommendBlockListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.SearchBlockPagingUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class CommunitySearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRecommendBlockListUseCase: GetRecommendBlockListUseCase,
    private val searchBlockPagingUseCase: SearchBlockPagingUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<CommunitySearchState> =
        MutableStateFlow(CommunitySearchState.Init)
    val state: StateFlow<CommunitySearchState> = _state.asStateFlow()

    private val _event: MutableEventFlow<CommunitySearchEvent> = MutableEventFlow()
    val event: EventFlow<CommunitySearchEvent> = _event.asEventFlow()

    private val _suggestBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val suggestBlockList: StateFlow<List<Block>> = _suggestBlockList.asStateFlow()

    private val _searchBlockPaging: MutableStateFlow<PagingData<Block>> =
        MutableStateFlow(PagingData.empty())
    val searchBlockPaging: StateFlow<PagingData<Block>> = _searchBlockPaging.asStateFlow()

    init {
        launch {
            _state.value = CommunitySearchState.Loading
            getRecommendBlockListUseCase()
                .onSuccess { suggestBlockList ->
                    _state.value = CommunitySearchState.Init
                    _suggestBlockList.value = suggestBlockList
                }.onFailure { exception ->
                    _state.value = CommunitySearchState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
        }
    }

    fun onIntent(intent: CommunitySearchIntent) {
        when (intent) {
            is CommunitySearchIntent.Search -> {
                search(intent.keyword)
            }
        }
    }

    private fun search(keyword: String) {
        launch {
            // TODO : Paging State 에 따라서 보여주기
            searchBlockPagingUseCase(keyword = keyword)
                .cachedIn(viewModelScope)
                .onStart {
                    _state.value = CommunitySearchState.Loading
                }.catch { exception ->
                    _state.value = CommunitySearchState.SearchResult
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }.collect { searchBlockPaging ->
                    _state.value = CommunitySearchState.SearchResult
                    _searchBlockPaging.value = searchBlockPaging
                }
        }
    }
}
