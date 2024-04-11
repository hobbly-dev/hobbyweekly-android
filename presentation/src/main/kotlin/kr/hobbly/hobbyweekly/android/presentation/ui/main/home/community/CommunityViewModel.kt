package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

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
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.zip
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetMyBlockListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetPopularBlockListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.GetPopularPostPagingUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMyBlockListUseCase: GetMyBlockListUseCase,
    private val getPopularBlockListUseCase: GetPopularBlockListUseCase,
    private val getPopularPostPagingUseCase: GetPopularPostPagingUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<CommunityState> = MutableStateFlow(CommunityState.Init)
    val state: StateFlow<CommunityState> = _state.asStateFlow()

    private val _event: MutableEventFlow<CommunityEvent> = MutableEventFlow()
    val event: EventFlow<CommunityEvent> = _event.asEventFlow()

    private val _myBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val myBlockList: StateFlow<List<Block>> = _myBlockList.asStateFlow()

    private val _popularBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val popularBlockList: StateFlow<List<Block>> = _popularBlockList.asStateFlow()

    private val _popularPostPaging: MutableStateFlow<PagingData<Post>> =
        MutableStateFlow(PagingData.empty())
    val popularPostPaging: StateFlow<PagingData<Post>> = _popularPostPaging.asStateFlow()

    fun onIntent(intent: CommunityIntent) {
        when (intent) {
            CommunityIntent.Refresh -> {
                refresh()
            }
        }
    }

    private fun refresh() {
        launch {
            _state.value = CommunityState.Loading
            zip(
                { getMyBlockListUseCase() },
                { getPopularBlockListUseCase() }
            ).onSuccess { (myBlockList, popularBlockList) ->
                _state.value = CommunityState.Init

                _myBlockList.value = myBlockList
                _popularBlockList.value = popularBlockList
            }.onFailure { exception ->
                _state.value = CommunityState.Init
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
        launch {
            getPopularPostPagingUseCase()
                .cachedIn(viewModelScope)
                .catch { exception ->
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }.collect { popularPostList ->
                    _popularPostPaging.value = popularPostList
                }
        }
    }
}
