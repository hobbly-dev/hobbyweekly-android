package kr.hobbly.hobbyweekly.android.data.remote.network.model.nonfeature.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper

@Serializable
data class GetTermListAgreeStateListRes(
    @SerialName("terms")
    val terms: List<Long>
) : DataMapper<List<Long>> {
    override fun toDomain(): List<Long> {
        return terms
    }
}
