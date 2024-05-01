package kr.hobbly.hobbyweekly.android.data.repository.feature.routine

import javax.inject.Inject
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kr.hobbly.hobbyweekly.android.data.remote.network.api.feature.RoutineApi
import kr.hobbly.hobbyweekly.android.data.remote.network.util.toDomain
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository

class RealRoutineRepository @Inject constructor(
    private val routineApi: RoutineApi
) : RoutineRepository {

    override suspend fun getCurrentRoutineList(): Result<List<Routine>> {
        return routineApi.getCurrentRoutineList().toDomain()
    }

    override suspend fun getRoutine(
        id: Long
    ): Result<Routine> {
        return routineApi.getRoutine(
            id = id
        ).toDomain()
    }

    override suspend fun writeRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long> {
        return routineApi.writeRoutinePost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        ).toDomain()
    }

    override suspend fun getLatestRoutineList(): Result<List<Routine>> {
        return routineApi.getLatestRoutineList().toDomain()
    }

    override suspend fun addRoutine(
        title: String,
        blockId: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<Long> {
        return routineApi.addRoutine(
            title = title,
            blockId = blockId,
            daysOfWeek = daysOfWeek,
            alarmTime = alarmTime
        ).toDomain()
    }

    override suspend fun editRoutine(
        id: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<Unit> {
        return routineApi.editRoutine(
            id = id,
            daysOfWeek = daysOfWeek,
            alarmTime = alarmTime
        )
    }

    override suspend fun removeRoutine(
        id: Long
    ): Result<Unit> {
        return routineApi.removeRoutine(
            id = id
        )
    }

    override suspend fun quitRoutine(
        id: Long
    ): Result<Unit> {
        return routineApi.quitRoutine(
            id = id
        )
    }

    override suspend fun switchRoutineAlarm(
        id: Long,
        isEnabled: Boolean
    ): Result<Unit> {
        return routineApi.switchRoutineAlarm(
            id = id,
            isEnabled = isEnabled
        )
    }

    override suspend fun getRoutineStatisticsList(
        id: Long,
        date: LocalDate
    ): Result<List<RoutineStatistics>> {
        return routineApi.getRoutineStatisticsList(
            id = id,
            date = date
        ).toDomain()
    }
}
