package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class WriteCommentReplyUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Long> {
        return communityRepository.writeCommentReply(
            id = id,
            content = content,
            isAnonymous = isAnonymous
        )
    }
}
