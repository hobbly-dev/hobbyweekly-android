package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetNotificationListRes(
    @SerialName("result")
    val result: List<NotificationRes>,
    @SerialName("hasPrevious")
    val hasPrevious: Boolean,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("sort")
    val sort: GetNotificationListSortRes
)

@Serializable
data class GetNotificationListSortRes(
    @SerialName("sorted")
    val sorted: Boolean,
    @SerialName("direction")
    val direction: String,
    @SerialName("orderProperty")
    val orderProperty: String
)
