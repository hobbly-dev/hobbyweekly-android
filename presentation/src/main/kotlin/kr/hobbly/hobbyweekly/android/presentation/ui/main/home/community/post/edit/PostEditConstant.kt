package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit

import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.PostConstant

object PostEditConstant {
    const val ROUTE: String = "${PostConstant.ROUTE}/edit"

    const val ROUTE_ARGUMENT_BLOCK_ID = "block_id"
    const val ROUTE_ARGUMENT_BOARD_ID = "board_id"
    const val ROUTE_ARGUMENT_POST_ID = "post_id"
    const val ROUTE_STRUCTURE =
        ROUTE + "?${ROUTE_ARGUMENT_BLOCK_ID}={${ROUTE_ARGUMENT_BLOCK_ID}}" +
                "&${ROUTE_ARGUMENT_BOARD_ID}={${ROUTE_ARGUMENT_BOARD_ID}}" +
                "&${ROUTE_ARGUMENT_POST_ID}={${ROUTE_ARGUMENT_POST_ID}}"
}
