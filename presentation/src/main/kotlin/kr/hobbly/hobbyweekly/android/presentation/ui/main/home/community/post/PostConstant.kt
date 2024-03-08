package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.CommunityConstant

object PostConstant {
    const val ROUTE: String = "${CommunityConstant.ROUTE}/post"

    const val ROUTE_ARGUMENT_BLOCK_ID = "block_id"
    const val ROUTE_ARGUMENT_BOARD_ID = "board_id"
    const val ROUTE_ARGUMENT_POST_ID = "post_id"
    const val ROUTE_STRUCTURE =
        ROUTE + "?${ROUTE_ARGUMENT_BLOCK_ID}={${ROUTE_ARGUMENT_BLOCK_ID}}" +
                "&${ROUTE_ARGUMENT_BOARD_ID}={${ROUTE_ARGUMENT_BOARD_ID}}" +
                "&${ROUTE_ARGUMENT_POST_ID}={${ROUTE_ARGUMENT_POST_ID}}"
}
