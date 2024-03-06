package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification.notificationDestination

fun NavGraphBuilder.myPageDestination(
    navController: NavController
) {
    notificationDestination(navController)
}
