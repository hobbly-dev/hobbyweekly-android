package kr.hobbly.hobbyweekly.android.domain.model.feature.community

import kotlinx.datetime.LocalDateTime

data class Post(
    val id: Long,
    val member: PostMember,
    val blockId: Long,
    val boardId: Long,
    val title: String,
    val description: String,
    val images: List<String>,
    val commentCount: Long,
    val likeCount: Long,
    val scrapCount: Long,
    val createdAt: LocalDateTime
)

data class PostMember(
    val id: Long,
    val name: String,
    val thumbnail: String
)
