package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class BlockUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {
        return userRepository.blockUser(
            id = id
        )
    }
}
