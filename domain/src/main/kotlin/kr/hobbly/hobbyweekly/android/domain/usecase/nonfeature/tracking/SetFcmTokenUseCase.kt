package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.tracking

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TrackingRepository

class SetFcmTokenUseCase @Inject constructor(
    private val trackingRepository: TrackingRepository
) {
    operator fun invoke(
        fcmToken: String
    ) {
        trackingRepository.fcmToken = fcmToken
    }
}
