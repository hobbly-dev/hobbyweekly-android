package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit

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
class RoutineEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RoutineEditState> = MutableStateFlow(RoutineEditState.Init)
    val state: StateFlow<RoutineEditState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RoutineEditEvent> = MutableEventFlow()
    val event: EventFlow<RoutineEditEvent> = _event.asEventFlow()

    private val _block: MutableStateFlow<Block> = MutableStateFlow(Block.empty)
    val block: StateFlow<Block> = _block.asStateFlow()

    val blockId: Long by lazy {
        savedStateHandle.get<Long>(RoutineEditConstant.ROUTE_ARGUMENT_BLOCK_ID) ?: -1
    }

    val routineId: Long by lazy {
        savedStateHandle.get<Long>(RoutineEditConstant.ROUTE_ARGUMENT_ROUTINE_ID) ?: -1
    }

    fun onIntent(intent: RoutineEditIntent) {
        when (intent) {
            is RoutineEditIntent.OnConfirm -> {
                if (routineId == -1L) {
                    addRoutine(intent.selectedDayOfWeek, intent.description)
                } else {
                    editRoutine(intent.selectedDayOfWeek, intent.description)
                }
            }

            RoutineEditIntent.OnDelete -> {
                deleteRoutine()
            }
        }
    }

    private fun addRoutine(
        selectedDayOfWeek: List<Int>,
        description: String
    ) {
        launch {
            _state.value = RoutineEditState.Loading

            _event.emit(RoutineEditEvent.AddRoutine.Success)
            _state.value = RoutineEditState.Init
        }
    }

    private fun editRoutine(
        selectedDayOfWeek: List<Int>,
        description: String
    ) {
        launch {
            _state.value = RoutineEditState.Loading

            _event.emit(RoutineEditEvent.EditRoutine.Success)
            _state.value = RoutineEditState.Init
        }
    }

    private fun deleteRoutine() {
        launch {
            _state.value = RoutineEditState.Loading

            _event.emit(RoutineEditEvent.DeleteRoutine.Success)
            _state.value = RoutineEditState.Init
        }
    }
}
