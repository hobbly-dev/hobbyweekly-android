package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgreeTermListReq(
    @SerialName("terms")
    val terms: List<Long>
)
