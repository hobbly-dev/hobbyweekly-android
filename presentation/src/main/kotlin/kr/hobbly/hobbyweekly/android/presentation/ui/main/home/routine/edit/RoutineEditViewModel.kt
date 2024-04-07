package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalTime
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.AddRoutineUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.EditRoutineUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.RemoveRoutineUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class RoutineEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addRoutineUseCase: AddRoutineUseCase,
    private val editRoutineUseCase: EditRoutineUseCase,
    private val removeRoutineUseCase: RemoveRoutineUseCase
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
                    addRoutine(
                        selectedDayOfWeek = intent.selectedDayOfWeek,
                        description = intent.description,
                        alarmTime = intent.alarmTime
                    )
                } else {
                    editRoutine(
                        selectedDayOfWeek = intent.selectedDayOfWeek,
                        description = intent.description,
                        alarmTime = intent.alarmTime
                    )
                }
            }

            RoutineEditIntent.OnDelete -> {
                deleteRoutine()
            }
        }
    }

    private fun addRoutine(
        selectedDayOfWeek: List<Int>,
        description: String,
        alarmTime: LocalTime?
    ) {
        launch {
            _state.value = RoutineEditState.Loading
            addRoutineUseCase(
                title = description,
                blockId = blockId,
                daysOfWeek = selectedDayOfWeek,
                alarmTime = alarmTime
            ).onSuccess { id ->
                _state.value = RoutineEditState.Init

                _event.emit(RoutineEditEvent.AddRoutine.Success)
            }.onFailure { exception ->
                _state.value = RoutineEditState.Init
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

    private fun editRoutine(
        selectedDayOfWeek: List<Int>,
        description: String,
        alarmTime: LocalTime?
    ) {
        // TODO : Description 회의 결과 파악 후 수정
        launch {
            _state.value = RoutineEditState.Loading
            editRoutineUseCase(
                id = routineId,
                daysOfWeek = selectedDayOfWeek,
                alarmTime = alarmTime
            ).onSuccess {
                _state.value = RoutineEditState.Init

                _event.emit(RoutineEditEvent.EditRoutine.Success)
            }.onFailure { exception ->
                _state.value = RoutineEditState.Init
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

    private fun deleteRoutine() {
        launch {
            _state.value = RoutineEditState.Loading
            removeRoutineUseCase(
                id = routineId
            ).onSuccess {
                _state.value = RoutineEditState.Init

                _event.emit(RoutineEditEvent.DeleteRoutine.Success)
            }.onFailure { exception ->
                _state.value = RoutineEditState.Init
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
