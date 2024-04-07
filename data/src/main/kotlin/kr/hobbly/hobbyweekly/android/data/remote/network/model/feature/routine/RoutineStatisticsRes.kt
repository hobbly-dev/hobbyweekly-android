package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics

@Serializable
data class RoutineStatisticsRes(
    @SerialName("title")
    val title: String = "",
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("completedCount")
    val completedCount: Int
) : DataMapper<RoutineStatistics> {
    override fun toDomain(): RoutineStatistics {
        return RoutineStatistics(
            title = title,
            totalCount = totalCount,
            completedCount = completedCount
        )
    }
}
