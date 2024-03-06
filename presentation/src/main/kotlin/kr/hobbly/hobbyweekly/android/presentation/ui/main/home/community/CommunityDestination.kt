package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.boardDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit.postEditDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.postDestination

fun NavGraphBuilder.communityDestination(
    navController: NavController
) {
    boardDestination(navController)
    postDestination(navController)
    postEditDestination(navController)
}
