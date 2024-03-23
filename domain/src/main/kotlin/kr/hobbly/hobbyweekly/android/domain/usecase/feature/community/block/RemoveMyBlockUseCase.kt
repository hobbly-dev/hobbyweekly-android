package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class RemoveMyBlockUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {
        return communityRepository.removeMyBlock(
            id = id
        )
    }
}
