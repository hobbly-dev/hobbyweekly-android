package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class ReportBoardPostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long,
        reason: String
    ): Result<Unit> {
        return communityRepository.reportPost(
            id = id,
            reason = reason
        )
    }
}
