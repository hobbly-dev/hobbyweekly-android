package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post

@Serializable
data class BoardPostRes(
    @SerialName("postId")
    val postId: Long,
    @SerialName("blockId")
    val blockId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String = "",
    @SerialName("images")
    val images: List<String>,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("commentCount")
    val commentCount: Int,
    @SerialName("createdAt")
    val createdAt: LocalDateTime,
    @SerialName("updatedAt")
    val updatedAt: LocalDateTime,
    @SerialName("isAnonymous")
    val isAnonymous: Boolean,
    @SerialName("isSecret")
    val isSecret: Boolean,
    @SerialName("member")
    val member: MemberRes,
    @SerialName("board")
    val board: BoardRes,
) : DataMapper<Post> {
    override fun toDomain(): Post {
        return Post(
            id = postId,
            blockId = blockId,
            board = board.toDomain(),
            member = member.toDomain(),
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
            imageList = images,
            likeCount = likeCount,
            commentCount = commentCount,
            isAnonymous = isAnonymous,
            isSecret = isSecret
        )
    }
}
