package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost

@Immutable
data class BlockData(
    val block: Block,
    val isMyBlock: Boolean,
    val boardList: List<Board>,
    val noticePostList: List<BoardPost>,
    val popularPostList: List<BoardPost>
) {
    companion object {
        val empty = BlockData(
            block = Block.empty,
            isMyBlock = false,
            boardList = emptyList(),
            noticePostList = emptyList(),
            popularPostList = emptyList()
        )
    }
}
