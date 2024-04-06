package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class LoadBoardPostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Post> {
        return communityRepository.loadPost(
            id = id
        )
    }
}
