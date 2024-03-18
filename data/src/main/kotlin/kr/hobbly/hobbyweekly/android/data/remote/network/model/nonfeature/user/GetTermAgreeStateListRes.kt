package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTermAgreeStateListRes(
    @SerialName("terms")
    val terms: List<GetTermAgreeStateItemRes>
)

@Serializable
data class GetTermAgreeStateItemRes(
    @SerialName("termId")
    val termId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("isAgreed")
    val isAgreed: Boolean
)
