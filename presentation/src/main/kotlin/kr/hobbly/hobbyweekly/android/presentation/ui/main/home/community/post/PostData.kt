package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardComment
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile

@Immutable
data class PostData(
    val post: BoardPost,
    val profile: Profile,
    val commentList: LazyPagingItems<BoardComment>
)
