package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPopularBlockRes(
    @SerialName("blocks")
    val blocks: List<BlockRes>
)
