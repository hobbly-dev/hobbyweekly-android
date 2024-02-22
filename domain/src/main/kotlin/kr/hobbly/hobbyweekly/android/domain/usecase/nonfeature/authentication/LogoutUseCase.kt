package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authenticationRepository.logout()
    }
}
