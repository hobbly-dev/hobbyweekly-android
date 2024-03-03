package kr.hobbly.hobbyweekly.android.presentation.ui.main.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.common.notification.notificationDestination

fun NavGraphBuilder.homeDestination(
    navController: NavController
) {
    notificationDestination(navController)
    composable(
        route = HomeConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(HomeConstant.ROUTE_ARGUMENT_SCREEN) {
                type = NavType.StringType
                defaultValue = HomeType.values().first().route
            }
        )
    ) {
        val viewModel: HomeViewModel = hiltViewModel()

        val argument: HomeArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            HomeArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: HomeData = let {
            val initialHomeType = viewModel.initialHomeType
            val homeTypeList = HomeType.values()

            HomeData(
                initialHomeType = initialHomeType,
                homeTypeList = homeTypeList
            )
        }

        ErrorObserver(viewModel)
        HomeScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
