package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics

@Serializable
data class RoutineStatisticsRes(
    @SerialName("blockId")
    val blockId: Long,
    @SerialName("blockName")
    val blockName: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("title")
    val title: String = "",
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("completedCount")
    val completedCount: Int,
) : DataMapper<RoutineStatistics> {
    override fun toDomain(): RoutineStatistics {
        return RoutineStatistics(
            id = blockId,
            blockName = blockName,
            thumbnail = thumbnail,
            title = title,
            totalCount = totalCount,
            completedCount = completedCount
        )
    }
}
