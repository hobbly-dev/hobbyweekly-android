package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification.notificationDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.statistics.myPageStatisticsDestination

fun NavGraphBuilder.myPageDestination(
    navController: NavController
) {
    notificationDestination(navController)
    myPageStatisticsDestination(navController)
}
