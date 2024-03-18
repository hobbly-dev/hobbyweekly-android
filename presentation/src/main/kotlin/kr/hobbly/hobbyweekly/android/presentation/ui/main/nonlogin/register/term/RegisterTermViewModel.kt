package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.term

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
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.AgreeTermListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetAgreedTermListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetTermListUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class RegisterTermViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTermListUseCase: GetTermListUseCase,
    private val getAgreedTermListUseCase: GetAgreedTermListUseCase,
    private val agreeTermListUseCase: AgreeTermListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegisterTermState> =
        MutableStateFlow(RegisterTermState.Init)
    val state: StateFlow<RegisterTermState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegisterTermEvent> = MutableEventFlow()
    val event: EventFlow<RegisterTermEvent> = _event.asEventFlow()

    private val _termList: MutableStateFlow<List<Term>> = MutableStateFlow(emptyList())
    val termList: StateFlow<List<Term>> = _termList.asStateFlow()

    init {
        launch {
            getTermList()
        }
    }

    fun onIntent(intent: RegisterTermIntent) {
        when (intent) {
            is RegisterTermIntent.OnConfirm -> {
                agreeTermState(intent.checkedTermList)
            }
        }
    }

    private suspend fun getTermList() {
        zip(
            { getTermListUseCase() },
            { getAgreedTermListUseCase() }
        ).onSuccess { (termList, agreedTermList) ->
            _termList.value = termList.filter { term ->
                term.id !in agreedTermList
            }
        }.onFailure { exception ->
            _state.value = RegisterTermState.Init
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

    private fun agreeTermState(
        checkedTermList: List<Long>
    ) {
        launch {
            _state.value = RegisterTermState.Loading
            agreeTermListUseCase(
                termList = checkedTermList
            ).onSuccess {
                _event.emit(RegisterTermEvent.AgreeTerm.Success)
                _state.value = RegisterTermState.Init
            }.onFailure { exception ->
                _state.value = RegisterTermState.Init
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
