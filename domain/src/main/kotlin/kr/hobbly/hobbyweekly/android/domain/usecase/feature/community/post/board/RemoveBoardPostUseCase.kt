package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class RemoveBoardPostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {
        return communityRepository.removePost(
            id = id
        )
    }
}
