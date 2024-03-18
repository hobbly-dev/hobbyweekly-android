package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class AgreeTermListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        termList: List<Long>
    ): Result<Unit> {
        return userRepository.agreeTermList(
            termList = termList
        )
    }
}
