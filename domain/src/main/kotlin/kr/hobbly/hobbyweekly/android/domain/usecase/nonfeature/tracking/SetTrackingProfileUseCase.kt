package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.tracking

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TrackingRepository

class SetTrackingProfileUseCase @Inject constructor(
    private val trackingRepository: TrackingRepository
) {
    suspend operator fun invoke(
        profile: Profile
    ): Result<Unit> {
        return trackingRepository.setProfile(
            profile = profile
        )
    }
}
