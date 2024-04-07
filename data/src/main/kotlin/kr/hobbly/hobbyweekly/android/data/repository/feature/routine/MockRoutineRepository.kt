package kr.hobbly.hobbyweekly.android.data.repository.feature.routine

import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.SmallRoutine
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository

class MockRoutineRepository @Inject constructor() : RoutineRepository {

    override suspend fun getCurrentRoutineList(): Result<List<Routine>> {
        randomShortDelay()

        return Result.success(
            listOf(
                Routine(
                    id = 0L,
                    title = "블록 이름",
                    blockId = 0L,
                    blockName = "영어 블록",
                    alarmTime = null,
                    isEnabled = true,
                    smallRoutine = listOf(
                        SmallRoutine(
                            dayOfWeek = 0,
                            isDone = true
                        ),
                        SmallRoutine(
                            dayOfWeek = 1,
                            isDone = true
                        ),
                        SmallRoutine(
                            dayOfWeek = 2,
                            isDone = false
                        )
                    )
                )
            )
        )
    }

    override suspend fun writeRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long> {
        randomShortDelay()

        return Result.success(0L)
    }

    override suspend fun getLatestRoutineList(): Result<List<Routine>> {
        randomShortDelay()

        return Result.success(
            listOf(
                Routine(
                    id = 0L,
                    title = "블록 이름",
                    blockId = 0L,
                    blockName = "영어 블록",
                    alarmTime = null,
                    isEnabled = true,
                    smallRoutine = listOf(
                        SmallRoutine(
                            dayOfWeek = 0,
                            isDone = true
                        ),
                        SmallRoutine(
                            dayOfWeek = 1,
                            isDone = true
                        ),
                        SmallRoutine(
                            dayOfWeek = 2,
                            isDone = false
                        )
                    )
                )
            )
        )
    }

    override suspend fun addRoutine(
        title: String,
        blockId: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<Long> {
        randomShortDelay()

        return Result.success(0L)
    }

    override suspend fun editRoutine(
        id: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun switchRoutineAlarm(
        id: Long,
        isEnabled: Boolean
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun getRoutineStatisticsList(
        id: Long,
        date: LocalDate
    ): Result<List<RoutineStatistics>> {
        randomShortDelay()

        return Result.success(
            listOf(
                RoutineStatistics(
                    title = "해리포터 원문보기",
                    totalCount = 5,
                    completedCount = 5
                ),
                RoutineStatistics(
                    title = "영화자막 번역하기",
                    totalCount = 4,
                    completedCount = 2
                ),
                RoutineStatistics(
                    title = "해외 친구들 만나기",
                    totalCount = 4,
                    completedCount = 1
                )
            )
        )
    }


    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
