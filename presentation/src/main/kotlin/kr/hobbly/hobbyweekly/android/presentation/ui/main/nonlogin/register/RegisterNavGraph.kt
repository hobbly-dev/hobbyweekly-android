package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.entry.RegisterEntryConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.entry.registerEntryDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.hobby.registerHobbyDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.profile.registerProfileDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.result.registerResultDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.term.registerTermDestination

fun NavGraphBuilder.registerNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = RegisterEntryConstant.ROUTE,
        route = RegisterConstant.ROUTE
    ) {
        registerEntryDestination(navController = navController)
        registerTermDestination(navController = navController)
        registerProfileDestination(navController = navController)
        registerHobbyDestination(navController = navController)
        registerResultDestination(navController = navController)
    }
}
