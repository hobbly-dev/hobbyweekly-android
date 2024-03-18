package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginReq(
    @SerialName("socialId")
    val socialId: String,
    @SerialName("socialType")
    val socialType: String,
    @SerialName("firebaseToken")
    val firebaseToken: String
)
