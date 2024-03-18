package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class GetAgreedTermListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<Long>> {
        return userRepository.getAgreedTermList()
    }
}
