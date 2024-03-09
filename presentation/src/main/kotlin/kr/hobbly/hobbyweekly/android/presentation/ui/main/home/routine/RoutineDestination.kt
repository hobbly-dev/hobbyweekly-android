package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit.routineEditDestination

fun NavGraphBuilder.routineDestination(
    navController: NavController
) {
    routineEditDestination(navController)
}
