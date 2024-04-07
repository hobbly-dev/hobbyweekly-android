package kr.hobbly.hobbyweekly.android.domain.repository.feature

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics

interface RoutineRepository {

    suspend fun getCurrentRoutineList(): Result<List<Routine>>

    suspend fun writeRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long>

    suspend fun getLatestRoutineList(): Result<List<Routine>>

    suspend fun addRoutine(
        title: String,
        blockId: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<Long>

    suspend fun editRoutine(
        id: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<Unit>

    suspend fun switchRoutineAlarm(
        id: Long,
        isEnabled: Boolean
    ): Result<Unit>

    suspend fun getRoutineStatisticsList(
        id: Long,
        date: LocalDate
    ): Result<List<RoutineStatistics>>
}
