package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper

@Serializable
data class WriteRoutinePostRes(
    @SerialName("postId")
    val postId: Long
) : DataMapper<Long> {
    override fun toDomain(): Long {
        return postId
    }
}
