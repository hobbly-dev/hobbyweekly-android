package kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine

import javax.inject.Inject
import kotlinx.datetime.LocalTime
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository

class EditRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(
        id: Long,
        daysOfWeek: List<Int>,
        alarmTime: LocalTime?
    ): Result<Unit> {
        return routineRepository.editRoutine(
            id = id,
            daysOfWeek = daysOfWeek,
            alarmTime = alarmTime
        )
    }
}
