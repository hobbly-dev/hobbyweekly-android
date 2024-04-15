package kr.hobbly.hobbyweekly.android.domain.model.nonfeature.notification

import kotlinx.datetime.LocalDateTime

data class Notification(
    val id: Long,
    val content: String,
    val isRead: Boolean,
    val createdAt: LocalDateTime,
    val deeplink: String
)
