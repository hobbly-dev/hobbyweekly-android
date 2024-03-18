package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTermListRes(
    @SerialName("terms")
    val terms: List<GetTermItemRes>
)

@Serializable
data class GetTermItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("required")
    val required: String,
    @SerialName("url")
    val url: String
)
