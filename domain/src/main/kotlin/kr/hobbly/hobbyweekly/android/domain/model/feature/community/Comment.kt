package kr.hobbly.hobbyweekly.android.domain.model.feature.community

import kotlinx.datetime.LocalDateTime

data class Comment(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val member: Member,
    val likeCount: Int,
    val child: List<Comment>,
    val isAnonymous: Boolean
) {
    companion object {
        val empty = Comment(
            id = -1,
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
