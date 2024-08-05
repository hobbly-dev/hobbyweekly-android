package kr.hobbly.hobbyweekly.android.presentation.ui.main.splash

import androidx.compose.runtime.Immutable
import kotlin.coroutines.CoroutineContext
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine

@Immutable
data class SplashArgument(
    val state: SplashState,
    val event: EventFlow<SplashEvent>,
    val intent: (SplashIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface SplashState {
    data object Init : SplashState
}


sealed interface SplashEvent {
    sealed interface Login : SplashEvent {
        data class Success(
            val currentRoutineList: List<Routine>,
            val latestRoutineList: List<Routine>
        ) : Login

        data object Fail : Login
    }
}

sealed interface SplashIntent
