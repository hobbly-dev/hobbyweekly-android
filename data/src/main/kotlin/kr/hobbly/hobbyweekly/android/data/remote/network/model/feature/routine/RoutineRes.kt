package kr.hobbly.hobbyweekly.android.data.remote.network.model.feature.routine

import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.hobbly.hobbyweekly.android.data.remote.mapper.DataMapper
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.SmallRoutine

@Serializable
data class RoutineRes(
    @SerialName("bigRoutineId")
    val bigRoutineId: Long,
    @SerialName("title")
    val title: String = "",
    @SerialName("blockId")
    val blockId: Long,
    @SerialName("blockName")
    val blockName: String,
    @SerialName("isAlarmEnabled")
    val isAlarmEnabled: Boolean,
    @SerialName("alarmTime")
    val alarmTime: LocalTime? = null,
    @SerialName("smallRoutines")
    val smallRoutines: List<SmallRoutineRes>
) : DataMapper<Routine> {
    override fun toDomain(): Routine {
        return Routine(
            id = bigRoutineId,
            title = title,
            blockId = blockId,
            blockName = blockName,
            alarmTime = alarmTime,
            isAlarmEnabled = isAlarmEnabled,
            smallRoutineList = smallRoutines.map { it.toDomain() }
        )
    }
}

@Serializable
data class SmallRoutineRes(
    @SerialName("dayOfWeek")
    val dayOfWeek: Int,
    @SerialName("isDone")
    val isDone: Boolean = false
) : DataMapper<SmallRoutine> {
    override fun toDomain(): SmallRoutine {
        return SmallRoutine(
            dayOfWeek = dayOfWeek,
            isDone = isDone
        )
    }
}
