package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.tracking.SetTrackingProfileUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetProfileUseCase

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val getProfileUseCase: GetProfileUseCase,
    private val setTrackingProfileUseCase: SetTrackingProfileUseCase
) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): Result<Long> {
        return authenticationRepository.login(
            username = username,
            password = password
        ).onSuccess {
            getProfileUseCase().onSuccess { profile ->
                setTrackingProfileUseCase(
                    profile = profile
                )
            }.getOrThrow()
        }
    }
}
