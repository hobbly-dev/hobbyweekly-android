package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WriteRoutinePostReq(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("isAnonymous")
    val isAnonymous: Boolean,
    @SerialName("isSecret")
    val isSecret: Boolean,
    @SerialName("images")
    val images: List<String>
)
