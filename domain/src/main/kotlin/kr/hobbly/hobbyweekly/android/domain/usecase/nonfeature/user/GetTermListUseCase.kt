package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class GetTermListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<Term>> {
        return userRepository.getTermList()
    }
}
