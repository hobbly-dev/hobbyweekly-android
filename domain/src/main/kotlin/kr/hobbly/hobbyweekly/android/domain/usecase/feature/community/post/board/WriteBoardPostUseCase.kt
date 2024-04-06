package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class WriteBoardPostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long> {
        return communityRepository.writePost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        )
    }
}
