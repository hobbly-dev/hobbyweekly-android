package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.feature.CommunityRepository

class RemoveCommentUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {
        return communityRepository.removeComment(
            id = id
        )
    }
}
