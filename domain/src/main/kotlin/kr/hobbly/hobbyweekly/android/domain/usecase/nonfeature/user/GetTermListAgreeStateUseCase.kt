package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.AgreedTerm
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class GetTermListAgreeStateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<AgreedTerm>> {
        return userRepository.getTermListAgreeState()
    }
}
