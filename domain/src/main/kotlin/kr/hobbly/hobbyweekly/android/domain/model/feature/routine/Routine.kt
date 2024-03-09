package kr.hobbly.hobbyweekly.android.domain.model.feature.routine

import kotlinx.datetime.LocalTime

data class Routine(
    val id: Long,
    val blockName: String,
    val dayOfWeekList: List<Int>,
    val description: String,
    val alarmTime: LocalTime?,
    val isEnabled: Boolean,
    val isConfirmedList: List<Int>
)
