package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Comment

@Serializable
data class CommentRes(
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
    val childComments: List<CommentRes>,
    @SerialName("isAnonymous")
    val isAnonymous: Boolean
) : DataMapper<Comment> {
    override fun toDomain(): Comment {
        return Comment(
            id = commentId,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
            member = member.toDomain(),
            likeCount = likeCount,
            child = childComments.map { it.toDomain() },
            isAnonymous = isAnonymous
        )
    }
}
