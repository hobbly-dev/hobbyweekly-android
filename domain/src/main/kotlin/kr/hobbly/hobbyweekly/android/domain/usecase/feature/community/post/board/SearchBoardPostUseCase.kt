package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.post.board

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class SearchBoardPostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long,
        keyword: String
    ): Flow<PagingData<Post>> {
        return communityRepository.searchPostFromBoardPaging(
            id = id,
            keyword = keyword
        )
    }
}
