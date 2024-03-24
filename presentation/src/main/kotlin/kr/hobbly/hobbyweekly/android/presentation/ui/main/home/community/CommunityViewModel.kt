package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.zip
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetMyBlockListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetPopularBlockListUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMyBlockListUseCase: GetMyBlockListUseCase,
    private val getPopularBlockListUseCase: GetPopularBlockListUseCase,
//    private val getPopularPostListUseCase: GetPopularPostListUseCase TODO
) : BaseViewModel() {

    private val _state: MutableStateFlow<CommunityState> = MutableStateFlow(CommunityState.Init)
    val state: StateFlow<CommunityState> = _state.asStateFlow()

    private val _event: MutableEventFlow<CommunityEvent> = MutableEventFlow()
    val event: EventFlow<CommunityEvent> = _event.asEventFlow()

    private val _myBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val myBlockList: StateFlow<List<Block>> = _myBlockList.asStateFlow()

    private val _popularBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val popularBlockList: StateFlow<List<Block>> = _popularBlockList.asStateFlow()

    private val _popularPostList: MutableStateFlow<List<BoardPost>> = MutableStateFlow(emptyList())
    val popularPostList: StateFlow<List<BoardPost>> = _popularPostList.asStateFlow()

    init {
        refresh()
    }

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
                _popularPostList.value = listOf()
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
    }
}
