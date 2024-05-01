package kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository

class QuitRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {
        return routineRepository.quitRoutine(
            id = id
        )
    }
}
