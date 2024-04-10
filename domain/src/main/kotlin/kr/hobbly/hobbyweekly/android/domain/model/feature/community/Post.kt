package kr.hobbly.hobbyweekly.android.domain.model.feature.community

import kotlinx.datetime.LocalDateTime

data class Post(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val imageList: List<String>,
    val likeCount: Int,
    val commentCount: Int,
    val isAnonymous: Boolean,
    val isSecret: Boolean,
    val member: Member,
    val board: Board,
) {
    companion object {
        val empty = Post(
            id = -1,
            title = "",
            content = "",
            createdAt = LocalDateTime(0, 1, 1, 0, 0),
            updatedAt = LocalDateTime(0, 1, 1, 0, 0),
            imageList = emptyList(),
            likeCount = 0,
            commentCount = 0,
            isAnonymous = false,
            isSecret = false,
            member = Member.empty,
            board = Board.empty,
        )
    }
}
