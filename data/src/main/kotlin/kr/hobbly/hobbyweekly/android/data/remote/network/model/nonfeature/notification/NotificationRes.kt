package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.notification

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.notification.Notification

@Serializable
data class NotificationRes(
    @SerialName("notificationId")
    val notificationId: Long,
    @SerialName("content")
    val content: String,
    @SerialName("isRead")
    val isRead: Boolean,
    @SerialName("createdAt")
    val createdAt: LocalDateTime
) : DataMapper<Notification> {
    override fun toDomain(): Notification {
        return Notification(
            id = notificationId,
            content = content,
            isRead = isRead,
            createdAt = createdAt
        )
    }
}
