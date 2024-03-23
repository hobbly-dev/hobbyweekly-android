package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class SearchBlockPagingUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        keyword: String
    ): Flow<PagingData<Block>> {
        return communityRepository.searchBlockPaging(
            keyword = keyword
        )
    }
}
