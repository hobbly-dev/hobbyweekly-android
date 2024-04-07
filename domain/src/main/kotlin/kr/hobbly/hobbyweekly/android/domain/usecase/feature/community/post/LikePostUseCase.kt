package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.feature.CommunityRepository

class LikePostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {
        return communityRepository.likePost(
            id = id
        )
    }
}
