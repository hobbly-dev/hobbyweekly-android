package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.block

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
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetMyBlockListUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class RoutineBlockViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMyBlockListUseCase: GetMyBlockListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<RoutineBlockState> =
        MutableStateFlow(RoutineBlockState.Init)
    val state: StateFlow<RoutineBlockState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RoutineBlockEvent> = MutableEventFlow()
    val event: EventFlow<RoutineBlockEvent> = _event.asEventFlow()

    private val _myBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val myBlockList: StateFlow<List<Block>> = _myBlockList.asStateFlow()

    init {
        launch {
            _state.value = RoutineBlockState.Loading
            getMyBlockListUseCase()
                .onSuccess { myBlockList ->
                    _state.value = RoutineBlockState.Init
                    _myBlockList.value = myBlockList
                }.onFailure { exception ->
                    _state.value = RoutineBlockState.Init
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

    fun onIntent(intent: RoutineBlockIntent) {

    }
}
