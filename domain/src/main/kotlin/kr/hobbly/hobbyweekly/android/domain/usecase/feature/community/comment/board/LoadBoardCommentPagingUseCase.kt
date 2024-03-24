package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.comment.board

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardComment
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class LoadBoardCommentPagingUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Flow<PagingData<BoardComment>> {
        return communityRepository.loadBoardCommentPaging(
            id = id
        )
    }
}
