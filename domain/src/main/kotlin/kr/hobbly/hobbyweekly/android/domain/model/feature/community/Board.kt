package kr.hobbly.hobbyweekly.android.domain.model.feature.community

data class Board(
    val id: Long,
    val blockId: Long,
    val title: String,
    val hasNewPost: Boolean
) {
    companion object {
        val empty = Board(
            id = 0,
            blockId = 0,
            title = "",
            hasNewPost = false
        )
    }
}
