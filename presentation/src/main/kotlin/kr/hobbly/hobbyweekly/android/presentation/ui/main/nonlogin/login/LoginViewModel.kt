package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.login

import androidx.lifecycle.SavedStateHandle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.zip
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.SocialType
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication.LoginUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetAgreedTermListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetProfileUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetTermListUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getTermListUseCase: GetTermListUseCase,
    private val getAgreedTermListUseCase: GetAgreedTermListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Init)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _event: MutableEventFlow<LoginEvent> = MutableEventFlow()
    val event: EventFlow<LoginEvent> = _event.asEventFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Login -> {
                login(intent.token)
            }
        }
    }

    private fun login(
        token: OAuthToken
    ) {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            launch {
                _state.value = LoginState.Loading
                loginUseCase(
                    socialId = tokenInfo?.id?.toString().orEmpty(),
                    socialType = SocialType.Kakao
                ).onSuccess {
                    checkProgress()
                }.onFailure { exception ->
                    _state.value = LoginState.Init
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

    private suspend fun checkProgress() {
        _state.value = LoginState.Loading

        zip(
            { getProfileUseCase() },
            { getTermListUseCase() },
            { getAgreedTermListUseCase() }
        ).onSuccess { (profile, termList, agreedTermList) ->
            _state.value = LoginState.Init

            val isNicknameValid = profile.nickname.isNotEmpty()
            val isTermAgreed = termList.all { term ->
                !term.isRequired || agreedTermList.contains(term.id)
            }
            val isHobbyChecked = profile.isHobbyChecked

            when {
                !isNicknameValid || !isTermAgreed || !isHobbyChecked -> {
                    _event.emit(LoginEvent.Login.NeedRegister)
                }

                else -> {
                    _event.emit(LoginEvent.Login.Success)
                }
            }
        }.onFailure { exception ->
            _state.value = LoginState.Init
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
