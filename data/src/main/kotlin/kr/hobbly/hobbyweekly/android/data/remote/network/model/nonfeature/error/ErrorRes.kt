package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorRes(
    @SerialName("detailStatusCode")
    val detailStatusCode: Long,
    @SerialName("message")
    val message: String
)
