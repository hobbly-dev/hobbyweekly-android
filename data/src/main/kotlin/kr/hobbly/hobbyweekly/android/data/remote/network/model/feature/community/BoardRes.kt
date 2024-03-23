package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board

@Serializable
data class BoardRes(
    @SerialName("boardId")
    val boardId: Long,
    @SerialName("blockId")
    val blockId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("hasNewPost")
    val hasNewPost: Boolean
) : DataMapper<Board> {
    override fun toDomain(): Board {
        return Board(
            id = boardId,
            blockId = blockId,
            title = title,
            hasNewPost = hasNewPost
        )
    }
}
