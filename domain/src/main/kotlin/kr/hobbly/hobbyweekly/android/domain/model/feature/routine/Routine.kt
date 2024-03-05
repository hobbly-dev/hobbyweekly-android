package kr.hobbly.hobbyweekly.android.domain.model.feature.routine

import androidx.annotation.IntRange
import kotlinx.datetime.LocalTime

data class Routine(
    val id: Long,
    val blockName: String,
    @IntRange(from = 0, to = 6) val dayOfWeek: Int,
    val description: String,
    val alarmTime: LocalTime?,
    val isEnabled: Boolean,
    val isConfirmed: Boolean
)
