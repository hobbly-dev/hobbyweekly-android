package kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.feature.RoutineRepository

class WriteRoutinePostUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long> {
        return routineRepository.writeRoutinePost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        )
    }
}
