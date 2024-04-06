package kr.hobbly.hobbyweekly.android.domain.model.feature.routine

import kotlinx.datetime.LocalTime

data class Routine(
    val id: Long,
    val title: String,
    val blockId: Long,
    val blockName: String,
    val alarmTime: LocalTime?,
    val description: String,
    val isEnabled: Boolean,
    val smallRoutine: List<SmallRoutine>
)

data class SmallRoutine(
    val dayOfWeek: Int,
    val isDone: Boolean
)
