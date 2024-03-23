package kr.hobbly.hobbyweekly.android.domain.model.feature.community

data class Block(
    val id: Long,
    val name: String,
    val content: String,
    val image: String,
    val thumbnail: String,
    val memberCount: Int
) {
    companion object {
        val empty = Block(
            id = 0,
            name = "",
            content = "",
            image = "",
            thumbnail = "",
            memberCount = 0
        )
    }
}

