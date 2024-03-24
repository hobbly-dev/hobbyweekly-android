package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.popularblock

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
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetPopularBlockListUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class PopularBlockViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPopularBlockListUseCase: GetPopularBlockListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<PopularBlockState> =
        MutableStateFlow(PopularBlockState.Init)
    val state: StateFlow<PopularBlockState> = _state.asStateFlow()

    private val _event: MutableEventFlow<PopularBlockEvent> = MutableEventFlow()
    val event: EventFlow<PopularBlockEvent> = _event.asEventFlow()

    private val _popularBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val popularBlockList: StateFlow<List<Block>> = _popularBlockList.asStateFlow()

    init {
        refresh()
    }

    fun onIntent(intent: PopularBlockIntent) {

    }

    private fun refresh() {
        _state.value = PopularBlockState.Loading
        launch {
            getPopularBlockListUseCase()
                .onSuccess { popularBlockList ->
                    _state.value = PopularBlockState.Init
                    _popularBlockList.value = popularBlockList
                }.onFailure { exception ->
                    _state.value = PopularBlockState.Init
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
