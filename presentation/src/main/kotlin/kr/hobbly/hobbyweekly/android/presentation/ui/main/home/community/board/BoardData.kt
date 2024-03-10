package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post

@Immutable
data class BoardData(
    val board: Board,
    val postList: List<Post>
) {
    companion object {
        val empty = BoardData(
            board = Board.empty,
            postList = emptyList()
        )
    }
}
