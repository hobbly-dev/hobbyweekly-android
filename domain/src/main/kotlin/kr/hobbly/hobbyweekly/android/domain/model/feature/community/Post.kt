package kr.hobbly.hobbyweekly.android.domain.model.feature.community

import kotlinx.datetime.LocalDateTime

data class Post(
    val id: Long,
    val member: Member,
    val blockId: Long,
    val boardId: Long,
    val title: String,
    val description: String,
    val images: List<String>,
    val commentCount: Long,
    val likeCount: Long,
    val scrapCount: Long,
    val isLike: Boolean,
    val isScrap: Boolean,
    val createdAt: LocalDateTime
) {
    companion object {
        val empty = Post(
            id = -1,
            member = Member.empty,
            blockId = -1,
            boardId = -1,
            title = "",
            description = "",
            images = emptyList(),
            commentCount = 0,
            likeCount = 0,
            scrapCount = 0,
            isLike = false,
            isScrap = false,
            createdAt = LocalDateTime(0, 1, 1, 0, 0)
        )
    }
}
