package kr.hobbly.hobbyweekly.android.domain.model.feature.community

data class Board(
    val id: Long,
    val blockId: Long,
    val name: String,
    val hasNewPost: Boolean
)
