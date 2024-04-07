package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block

import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post

@Immutable
data class BlockData(
    val block: Block,
    val isMyBlock: Boolean,
    val boardList: List<Board>,
    val noticePostPaging: LazyPagingItems<Post>,
    val popularPostPaging: LazyPagingItems<Post>
)
