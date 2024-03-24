package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.search

import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.BoardConstant

object BoardSearchConstant {
    const val ROUTE: String = "${BoardConstant.ROUTE}/search"

    const val ROUTE_ARGUMENT_BLOCK_ID = "block_id"
    const val ROUTE_ARGUMENT_BOARD_ID = "board_id"
    const val ROUTE_STRUCTURE = ROUTE +
            "?${ROUTE_ARGUMENT_BLOCK_ID}={${ROUTE_ARGUMENT_BLOCK_ID}}" +
            "&${ROUTE_ARGUMENT_BOARD_ID}={${ROUTE_ARGUMENT_BOARD_ID}}"
}
