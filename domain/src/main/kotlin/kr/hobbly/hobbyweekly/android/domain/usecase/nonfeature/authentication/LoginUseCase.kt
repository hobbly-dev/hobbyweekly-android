package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.SocialType
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.AuthenticationRepository
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.tracking.GetFcmTokenUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.tracking.SetTrackingProfileUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetProfileUseCase

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val getProfileUseCase: GetProfileUseCase,
    private val setTrackingProfileUseCase: SetTrackingProfileUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase
) {
    suspend operator fun invoke(
        socialId: String,
        socialType: SocialType
    ): Result<Unit> {
        return authenticationRepository.login(
            socialId = socialId,
            socialType = socialType,
            firebaseToken = getFcmTokenUseCase()
        ).mapCatching {
            getProfileUseCase().onSuccess { profile ->
                setTrackingProfileUseCase(
                    profile = profile
                )
            }.getOrThrow()
        }
    }
}
