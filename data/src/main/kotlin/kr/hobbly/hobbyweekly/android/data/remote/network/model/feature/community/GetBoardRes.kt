package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardType

@Serializable
data class GetBoardRes(
    @SerialName("boardId")
    val boardId: Long,
    @SerialName("blockId")
    val blockId: Long,
    @SerialName("boardType")
    val boardType: String,
    @SerialName("boardName")
    val boardName: String,
    @SerialName("hasNewPost")
    val hasNewPost: Boolean
) : DataMapper<Board> {
    override fun toDomain(): Board {
        return Board(
            id = boardId,
            blockId = blockId,
            type = BoardType.from(boardType),
            name = boardName,
            hasNewPost = hasNewPost
        )
    }
}
