package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.entry

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
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetAgreedTermListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetProfileUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetTermListUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class RegisterEntryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProfileUseCase: GetProfileUseCase,
    private val getTermListUseCase: GetTermListUseCase,
    private val getAgreedTermListUseCase: GetAgreedTermListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegisterEntryState> =
        MutableStateFlow(RegisterEntryState.Init)
    val state: StateFlow<RegisterEntryState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegisterEntryEvent> = MutableEventFlow()
    val event: EventFlow<RegisterEntryEvent> = _event.asEventFlow()

    init {
        launch {
            checkProgress()
        }
    }

    fun onIntent(intent: RegisterEntryIntent) {

    }

    private suspend fun checkProgress() {
        _state.value = RegisterEntryState.Loading

        zip(
            { getProfileUseCase() },
            { getTermListUseCase() },
            { getAgreedTermListUseCase() }
        ).onSuccess { (profile, termList, agreedTermList) ->
            _state.value = RegisterEntryState.Init

            val isNicknameValid = profile.nickname.isNotEmpty()
            val isTermAgreed = termList.all { term ->
                !term.isRequired || agreedTermList.contains(term.id)
            }
            val isHobbyChecked = profile.isHobbyChecked

            when {
                !isTermAgreed -> {
                    _event.emit(RegisterEntryEvent.NeedTermAgreement)
                }

                !isNicknameValid -> {
                    _event.emit(RegisterEntryEvent.NeedNickname)
                }

                !isHobbyChecked -> {
                    _event.emit(RegisterEntryEvent.NeedHobbyList)
                }

                else -> {
                    _event.emit(RegisterEntryEvent.NoProblem)
                }
            }
        }.onFailure { exception ->
            _state.value = RegisterEntryState.Init
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
