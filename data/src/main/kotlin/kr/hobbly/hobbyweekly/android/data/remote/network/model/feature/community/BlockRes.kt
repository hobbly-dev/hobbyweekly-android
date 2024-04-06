package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block

@Serializable
data class BlockRes(
    @SerialName("blockId")
    val blockId: Long,
    @SerialName("blockName")
    val blockName: String,
    @SerialName("content")
    val content: String,
    @SerialName("image")
    val image: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("memberCount")
    val memberCount: Int
) : DataMapper<Block> {
    override fun toDomain(): Block {
        return Block(
            id = blockId,
            name = blockName,
            content = content,
            image = image,
            thumbnail = thumbnail,
            memberCount = memberCount
        )
    }
}
