package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment.board

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class WriteBoardCommentUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        return communityRepository.writeBoardComment(
            id = id,
            content = content,
            isAnonymous = isAnonymous
        )
    }
}
