package kr.hobbly.hobbyweekly.android.domain.model.feature.mypage

import kotlinx.datetime.LocalDateTime

data class Notification(
    val id: Long,
    val content: String,
    val isRead: Boolean,
    val createdAt: LocalDateTime
)
