package kr.hobbly.hobbyweekly.android.presentation.ui.main.splash

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
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.GetCurrentRoutineListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.GetLatestRoutineListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication.token.CheckRefreshTokenFilledUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val checkRefreshTokenFilledUseCase: CheckRefreshTokenFilledUseCase,
    private val getCurrentRoutineListUseCase: GetCurrentRoutineListUseCase,
    private val getLatestRoutineListUseCase: GetLatestRoutineListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<SplashState> = MutableStateFlow(SplashState.Init)
    val state: StateFlow<SplashState> = _state.asStateFlow()

    private val _event: MutableEventFlow<SplashEvent> = MutableEventFlow()
    val event: EventFlow<SplashEvent> = _event.asEventFlow()

    init {
        launch {
            login()
        }
    }

    fun onIntent(intent: SplashIntent) {

    }

    private suspend fun login() {
        val isRefreshTokenFilled = checkRefreshTokenFilledUseCase()

        if (isRefreshTokenFilled) {
            zip(
                { getCurrentRoutineListUseCase() },
                { getLatestRoutineListUseCase() }
            ).onSuccess { (currentRoutineList, latestRoutineList) ->
                // TODO : check progress
                _event.emit(SplashEvent.Login.Success(currentRoutineList, latestRoutineList))
            }.onFailure { exception ->
                _event.emit(SplashEvent.Login.Fail)
            }
        } else {
            _event.emit(SplashEvent.Login.Fail)
        }
    }
}
