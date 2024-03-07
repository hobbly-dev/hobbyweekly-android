package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board

import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.CommunityConstant

object BoardConstant {
    const val ROUTE: String = "${CommunityConstant.ROUTE}/board"

    const val ROUTE_ARGUMENT_COMMUNITY_ID = "community_id"
    const val ROUTE_STRUCTURE = "$ROUTE?${ROUTE_ARGUMENT_COMMUNITY_ID}={${ROUTE_ARGUMENT_COMMUNITY_ID}}"
}
