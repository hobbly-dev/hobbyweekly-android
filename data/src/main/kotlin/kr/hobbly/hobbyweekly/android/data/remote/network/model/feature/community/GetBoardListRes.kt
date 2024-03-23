package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetBoardListRes(
    @SerialName("boards")
    val boards: List<BoardRes>
)
