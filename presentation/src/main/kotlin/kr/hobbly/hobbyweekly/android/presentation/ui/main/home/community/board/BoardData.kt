package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost

@Immutable
data class BoardData(
    val block: Block,
    val board: Board,
    val postList: List<BoardPost>
) {
    companion object {
        val empty = BoardData(
            block = Block.empty,
            board = Board.empty,
            postList = emptyList()
        )
    }
}
