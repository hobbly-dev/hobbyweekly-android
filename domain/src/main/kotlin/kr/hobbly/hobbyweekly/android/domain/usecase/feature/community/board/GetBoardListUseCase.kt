package kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.board

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class GetBoardListUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<List<Board>> {
        return communityRepository.getBoardList(
            id = id
        )
    }
}
