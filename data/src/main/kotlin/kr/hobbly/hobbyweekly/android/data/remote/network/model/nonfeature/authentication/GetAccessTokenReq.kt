package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAccessTokenReq(
    @SerialName("refreshToken")
    val refreshToken: String
)
