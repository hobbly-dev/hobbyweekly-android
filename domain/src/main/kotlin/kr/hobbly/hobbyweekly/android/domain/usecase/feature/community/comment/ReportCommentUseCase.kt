package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class ReportCommentUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long,
        reason: String
    ): Result<Unit> {
        return communityRepository.reportComment(
            id = id,
            reason = reason
        )
    }
}
