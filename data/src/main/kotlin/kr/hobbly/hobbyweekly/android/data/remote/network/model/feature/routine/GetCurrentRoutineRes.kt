package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine

@Serializable
data class GetCurrentRoutineRes(
    @SerialName("routines")
    val routines: List<RoutineRes>
) : DataMapper<List<Routine>> {
    override fun toDomain(): List<Routine> {
        return routines.map { it.toDomain() }
    }
}
