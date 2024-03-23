package kr.hobbly.hobbyweekly.android.domain.model.feature.community

import kotlinx.datetime.LocalDateTime

data class BoardPost(
    val id: Long,
    val blockId: Long,
    val boardId: Long,
    val member: Member,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val images: List<String>,
    val likeCount: Int,
    val commentCount: Int,
    val isAnonymous: Boolean,
    val isSecret: Boolean
) {
    companion object {
        val empty = BoardPost(
            id = -1,
            blockId = -1,
            boardId = -1,
            member = Member.empty,
            title = "",
            content = "",
            createdAt = LocalDateTime(0, 1, 1, 0, 0),
            updatedAt = LocalDateTime(0, 1, 1, 0, 0),
            images = emptyList(),
            likeCount = 0,
            commentCount = 0,
            isAnonymous = false,
            isSecret = false
        )
    }
}
