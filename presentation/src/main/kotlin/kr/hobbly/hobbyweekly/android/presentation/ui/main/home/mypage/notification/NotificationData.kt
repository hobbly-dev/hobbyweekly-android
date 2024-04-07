package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.notification.Notification

@Immutable
data class NotificationData(
    val notificationPaging: LazyPagingItems<Notification>
)
