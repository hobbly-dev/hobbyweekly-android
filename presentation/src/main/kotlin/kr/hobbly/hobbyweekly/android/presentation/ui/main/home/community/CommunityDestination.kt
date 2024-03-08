package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block.blockDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.boardDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.myblock.myBlockDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.popularblock.popularBlockDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit.postEditDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.postDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.searchblock.searchBlockDestination

fun NavGraphBuilder.communityDestination(
    navController: NavController
) {
    myBlockDestination(navController)
    popularBlockDestination(navController)
    searchBlockDestination(navController)
    blockDestination(navController)
    boardDestination(navController)
    postDestination(navController)
    postEditDestination(navController)
}
