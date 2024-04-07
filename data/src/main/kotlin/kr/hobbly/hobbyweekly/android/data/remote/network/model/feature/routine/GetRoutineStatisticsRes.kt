package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics

@Serializable
data class GetRoutineStatisticsRes(
    @SerialName("challenges")
    val challenges: List<RoutineStatisticsRes>
) : DataMapper<List<RoutineStatistics>> {
    override fun toDomain(): List<RoutineStatistics> {
        return challenges.map { it.toDomain() }
    }
}
