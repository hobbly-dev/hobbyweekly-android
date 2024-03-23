package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class LikeCommentUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {
        return communityRepository.likeComment(
            id = id
        )
    }
}
