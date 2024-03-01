package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.login.LoginConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.login.loginDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.registerNavGraph

fun NavGraphBuilder.nonLoginNavGraphNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = LoginConstant.ROUTE,
        route = NonLoginConstant.ROUTE
    ) {
        registerNavGraph(navController = navController)
        loginDestination(navController = navController)
    }
}
