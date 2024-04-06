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
    @SerialName("description")
    val description: String,
    @SerialName("isEnabled")
    val isEnabled: Boolean,
    @SerialName("alarmTime")
    val alarmTime: LocalTime? = null,
    @SerialName("smallRoutine")
    val smallRoutine: List<SmallRoutineRes>
) : DataMapper<Routine> {
    override fun toDomain(): Routine {
        return Routine(
            id = bigRoutineId,
            title = title,
            blockId = blockId,
            blockName = blockName,
            alarmTime = alarmTime,
            description = description,
            isEnabled = isEnabled,
            smallRoutine = smallRoutine.map { it.toDomain() }
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
