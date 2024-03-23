package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

@Serializable
data class GetPopularBlockRes(
    @SerialName("blocks")
    val blocks: List<BlockRes>
) : DataMapper<List<Block>> {
    override fun toDomain(): List<Block> {
        return blocks.map { it.toDomain() }
    }
}
