package kr.hobbly.hobbyweekly.android.domain.model.feature.routine

data class RoutineStatistics(
    val id: Long,
    val blockName: String,
    val thumbnail: String,
    val title: String,
    val totalCount: Int,
    val completedCount: Int
)
