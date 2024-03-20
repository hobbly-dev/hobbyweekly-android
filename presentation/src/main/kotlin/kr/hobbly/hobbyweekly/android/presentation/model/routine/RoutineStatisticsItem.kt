package kr.hobbly.hobbyweekly.android.presentation.model.routine

data class RoutineStatisticsItem(
    val dayOfWeek: Int,
    val routineCount: Int,
    val confirmedRoutineCount: Int
)
