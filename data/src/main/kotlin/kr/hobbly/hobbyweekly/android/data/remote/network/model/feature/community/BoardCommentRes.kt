package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardComment

@Serializable
data class BoardCommentRes(
    @SerialName("commentId")
    val commentId: Long,
    @SerialName("blockId")
    val blockId: Long,
    @SerialName("boardId")
    val boardId: Long,
    @SerialName("postId")
    val postId: Long,
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
    val childComments: List<BoardCommentRes>,
    @SerialName("isAnonymous")
    val isAnonymous: Boolean
) : DataMapper<BoardComment> {
    override fun toDomain(): BoardComment {
        return BoardComment(
            id = commentId,
            blockId = blockId,
            boardId = boardId,
            postId = postId,
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
