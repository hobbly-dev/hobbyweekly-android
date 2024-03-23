package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WriteNoticePostReq(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("isAnonymous")
    val isAnonymous: Boolean,
    @SerialName("images")
    val images: List<String>
)
