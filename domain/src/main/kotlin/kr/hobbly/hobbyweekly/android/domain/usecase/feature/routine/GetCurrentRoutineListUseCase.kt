package kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository

class GetCurrentRoutineListUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(): Result<List<Routine>> {
        return routineRepository.getCurrentRoutineList()
    }
}
