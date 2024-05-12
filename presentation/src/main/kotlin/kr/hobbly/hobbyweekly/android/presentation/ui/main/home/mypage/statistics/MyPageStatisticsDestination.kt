package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.statistics

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.myPageStatisticsDestination(
    navController: NavController
) {
    composable(
        route = MyPageStatisticsConstant.ROUTE
    ) {
        val viewModel: MyPageStatisticsViewModel = hiltViewModel()

        val argument: MyPageStatisticsArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            MyPageStatisticsArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: MyPageStatisticsData = Unit.let {
            val routineStatisticsList by viewModel.routineStatisticsList.collectAsStateWithLifecycle()

            MyPageStatisticsData(
                routineStatisticsList = routineStatisticsList
            )
        }

        ErrorObserver(viewModel)
        MyPageStatisticsScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
