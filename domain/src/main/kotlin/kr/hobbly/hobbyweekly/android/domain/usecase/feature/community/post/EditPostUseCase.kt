package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.feature.CommunityRepository

class EditPostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        return communityRepository.editPost(
            id = id,
            title = title,
            content = content,
            isAnonymous = isAnonymous,
            isSecret = isSecret,
            imageList = imageList
        )
    }
}
