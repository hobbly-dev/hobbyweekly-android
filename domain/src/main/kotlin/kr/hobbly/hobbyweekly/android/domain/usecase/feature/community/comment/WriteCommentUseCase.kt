package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.feature.CommunityRepository

class WriteCommentUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Long> {
        return communityRepository.writeComment(
            id = id,
            content = content,
            isAnonymous = isAnonymous
        )
    }
}
