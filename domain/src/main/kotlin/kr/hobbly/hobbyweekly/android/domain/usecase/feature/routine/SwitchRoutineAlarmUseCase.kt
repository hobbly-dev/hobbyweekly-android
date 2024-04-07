package kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository

class SwitchRoutineAlarmUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(
        id: Long,
        isEnabled: Boolean
    ): Result<Unit> {
        return routineRepository.switchRoutineAlarm(
            id = id,
            isEnabled = isEnabled
        )
    }
}
