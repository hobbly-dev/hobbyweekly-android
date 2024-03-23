package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board

@Serializable
data class GetBoardListRes(
    @SerialName("boards")
    val boards: List<BoardRes>
) : DataMapper<List<Board>> {
    override fun toDomain(): List<Board> {
        return boards.map { it.toDomain() }
    }
}
