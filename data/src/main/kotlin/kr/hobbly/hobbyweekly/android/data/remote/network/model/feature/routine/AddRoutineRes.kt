package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper

@Serializable
data class AddRoutineRes(
    @SerialName("bigRoutineId")
    val bigRoutineId: Long
) : DataMapper<Long> {
    override fun toDomain(): Long {
        return bigRoutineId
    }
}
