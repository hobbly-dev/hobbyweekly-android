package kr.hobbly.hobbyweekly.android.domain.model.feature.community

import kotlinx.datetime.LocalDateTime

data class BoardComment(
    val id: Long,
    val blockId: Long,
    val boardId: Long,
    val postId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val member: Member,
    val likeCount: Int,
    val child: List<BoardComment>,
    val isAnonymous: Boolean
) {
    companion object {
        val empty = BoardComment(
            id = -1,
            blockId = -1,
            boardId = -1,
            postId = -1,
            content = "",
            createdAt = LocalDateTime(0, 1, 1, 0, 0),
            updatedAt = LocalDateTime(0, 1, 1, 0, 0),
            member = Member.empty,
            likeCount = 0,
            child = emptyList(),
            isAnonymous = false
        )
    }
}
