package kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine

import javax.inject.Inject
import kotlinx.datetime.LocalTime
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository

class AddRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(
        title: String,
        blockId: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<Long> {
        return routineRepository.addRoutine(
            title = title,
            blockId = blockId,
            daysOfWeek = daysOfWeek,
            alarmTime = alarmTime
        )
    }
}
