package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.login

import androidx.compose.runtime.Immutable
import com.kakao.sdk.auth.model.OAuthToken
import kotlin.coroutines.CoroutineContext
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow

@Immutable
data class LoginArgument(
    val state: LoginState,
    val event: EventFlow<LoginEvent>,
    val intent: (LoginIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val coroutineContext: CoroutineContext
)

sealed interface LoginState {
    data object Init : LoginState
    data object Loading : LoginState
}


sealed interface LoginEvent {
    sealed interface Login : LoginEvent {
        data object NeedRegister : Login
        data object Success : Login
    }
}

sealed interface LoginIntent {
    data class Login(val token: OAuthToken) : LoginIntent
}
