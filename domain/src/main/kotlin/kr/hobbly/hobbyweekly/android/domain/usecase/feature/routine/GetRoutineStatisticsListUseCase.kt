package kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine

import javax.inject.Inject
import kotlinx.datetime.LocalDate
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository

class GetRoutineStatisticsListUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(
        id: Long,
        date: LocalDate
    ): Result<List<RoutineStatistics>> {
        return routineRepository.getRoutineStatisticsList(
            id = id,
            date = date
        )
    }
}
