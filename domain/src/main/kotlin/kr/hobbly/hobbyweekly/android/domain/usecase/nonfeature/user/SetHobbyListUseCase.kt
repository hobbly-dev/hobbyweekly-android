package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class SetHobbyListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        hobbyList: List<String>
    ): Result<Unit> {
        return userRepository.setHobbyList(
            hobbyList = hobbyList
        )
    }
}
