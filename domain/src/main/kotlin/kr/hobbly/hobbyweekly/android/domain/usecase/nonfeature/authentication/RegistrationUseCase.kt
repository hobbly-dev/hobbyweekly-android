package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository

class RegistrationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): Result<Long> {
        return authenticationRepository.register(
            username = username,
            password = password
        )
    }
}
