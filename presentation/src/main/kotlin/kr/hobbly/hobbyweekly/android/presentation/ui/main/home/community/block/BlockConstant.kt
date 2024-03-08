package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block

import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.CommunityConstant

object BlockConstant {
    const val ROUTE: String = "${CommunityConstant.ROUTE}/block"

    const val ROUTE_ARGUMENT_BLOCK_ID = "block_id"
    const val ROUTE_STRUCTURE = "$ROUTE?${ROUTE_ARGUMENT_BLOCK_ID}={${ROUTE_ARGUMENT_BLOCK_ID}}"
}
