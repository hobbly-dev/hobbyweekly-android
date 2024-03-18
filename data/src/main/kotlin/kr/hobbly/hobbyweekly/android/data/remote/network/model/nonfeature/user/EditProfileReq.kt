package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditProfileReq(
    @SerialName("nickname")
    val nickname: String?,
    @SerialName("image")
    val image: String?
)
