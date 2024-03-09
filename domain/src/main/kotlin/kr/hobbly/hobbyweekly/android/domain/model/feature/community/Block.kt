package kr.hobbly.hobbyweekly.android.domain.model.feature.community

data class Block(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: String
) {
    companion object {
        val empty = Block(
            id = 0,
            name = "",
            description = "",
            thumbnail = ""
        )
    }
}

