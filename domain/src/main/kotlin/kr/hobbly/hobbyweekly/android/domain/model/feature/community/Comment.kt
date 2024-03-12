package kr.hobbly.hobbyweekly.android.domain.model.feature.community

import kotlinx.datetime.LocalDateTime

data class Comment(
    val id: Long,
    val member: Member,
    val blockId: Long,
    val boardId: Long,
    val postId: Long,
    val title: String,
    val child: List<Comment>,
    val createdAt: LocalDateTime
) {
    companion object {
        val empty = Comment(
            id = -1,
            member = Member.empty,
            blockId = -1,
            boardId = -1,
            postId = -1,
            title = "",
            child = emptyList(),
            createdAt = LocalDateTime(0, 1, 1, 0, 0)
        )
    }
}
