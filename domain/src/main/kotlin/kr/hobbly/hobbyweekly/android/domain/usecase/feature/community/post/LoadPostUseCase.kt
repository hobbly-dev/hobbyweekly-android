package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.repository.feature.CommunityRepository

class LoadPostUseCase @Inject constructor(
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
