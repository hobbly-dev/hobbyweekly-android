package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class GetRecommendBlockListUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): Result<List<Block>> {
        return communityRepository.getRecommendBlockList()
    }
}
