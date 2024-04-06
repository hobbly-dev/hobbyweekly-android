package kr.hobbly.hobbyweekly.android.domain.model.feature.community

data class Board(
    val id: Long,
    val blockId: Long,
    val type: BoardType,
    val name: String,
    val hasNewPost: Boolean
) {
    companion object {
        val empty = Board(
            id = 0,
            blockId = 0,
            type = BoardType.Regular,
            name = "",
            hasNewPost = false
        )
    }
}

sealed class BoardType(val name: String) {
    data object Notice : BoardType(name = "NOTICE")
    data object Routine : BoardType(name = "ROUTINE")
    data object Regular : BoardType(name = "REGULAR")

    companion object {
        fun from(type: String): BoardType {
            return when (type) {
                Notice.name -> Notice
                Routine.name -> Routine
                Regular.name -> Regular
                else -> throw IllegalArgumentException("Unknown board type: $type")
            }
        }
    }
}
