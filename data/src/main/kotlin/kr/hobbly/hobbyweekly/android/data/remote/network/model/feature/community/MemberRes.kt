package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberRes(
    @SerialName("memberId")
    val memberId: Long,
    @SerialName("nickname")
    val nickname: String = "",
    @SerialName("image")
    val image: String = ""
)
