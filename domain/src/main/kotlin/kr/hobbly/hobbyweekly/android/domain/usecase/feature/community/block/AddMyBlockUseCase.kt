package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.feature.CommunityRepository

class AddMyBlockUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Long> {
        return communityRepository.addMyBlock(
            id = id
        )
    }
}
