package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine

import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditRoutineReq(
    @SerialName("daysOfWeek")
    val daysOfWeek: List<Int>,
    @SerialName("alarmTime")
    val alarmTime: LocalTime?
)
