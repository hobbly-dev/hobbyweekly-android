package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Comment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile

@Immutable
data class PostData(
    val block: Block,
    val board: Board,
    val post: Post,
    val isMyBlock: Boolean,
    val profile: Profile,
    val commentList: LazyPagingItems<Comment>
)
