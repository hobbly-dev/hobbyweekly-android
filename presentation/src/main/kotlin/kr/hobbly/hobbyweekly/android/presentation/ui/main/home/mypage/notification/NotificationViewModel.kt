package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.mypage.Notification
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<NotificationState> =
        MutableStateFlow(NotificationState.Init)
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<NotificationEvent> = MutableEventFlow()
    val event: EventFlow<NotificationEvent> = _event.asEventFlow()

    private val _notificationPaging: MutableStateFlow<PagingData<Notification>> =
        MutableStateFlow(PagingData.empty())
    val notificationPaging: StateFlow<PagingData<Notification>> = _notificationPaging.asStateFlow()

    fun onIntent(intent: NotificationIntent) {

    }
}
