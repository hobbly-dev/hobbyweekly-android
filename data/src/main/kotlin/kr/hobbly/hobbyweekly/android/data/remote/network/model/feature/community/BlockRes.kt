package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockRes(
    @SerialName("blockId")
    val blockId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("content")
    val content: String,
    @SerialName("image")
    val image: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("memberCount")
    val memberCount: Int
)
