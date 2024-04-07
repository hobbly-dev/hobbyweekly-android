package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.notification.Notification
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetNotificationPagingUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNotificationPagingUseCase: GetNotificationPagingUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<NotificationState> =
        MutableStateFlow(NotificationState.Init)
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<NotificationEvent> = MutableEventFlow()
    val event: EventFlow<NotificationEvent> = _event.asEventFlow()

    private val _notificationPaging: MutableStateFlow<PagingData<Notification>> =
        MutableStateFlow(PagingData.empty())
    val notificationPaging: StateFlow<PagingData<Notification>> = _notificationPaging.asStateFlow()

    init {
        launch {
            getNotificationPagingUseCase()
                .cachedIn(viewModelScope)
                .catch { exception ->
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }.collect { notificationPaging ->
                    _notificationPaging.value = notificationPaging
                }
        }
    }

    fun onIntent(intent: NotificationIntent) {

    }
}
