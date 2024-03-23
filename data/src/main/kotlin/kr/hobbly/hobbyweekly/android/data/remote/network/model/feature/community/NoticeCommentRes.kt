package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoticeCommentRes(
    @SerialName("commentId")
    val commentId: Long,
    @SerialName("content")
    val content: String,
    @SerialName("createdAt")
    val createdAt: LocalDateTime,
    @SerialName("updatedAt")
    val updatedAt: LocalDateTime,
    @SerialName("member")
    val member: MemberRes,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("childComments")
    val childComments: List<NoticeCommentRes>,
    @SerialName("anonymous")
    val anonymous: Boolean
)
