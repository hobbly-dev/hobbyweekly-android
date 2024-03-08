package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.popularblock

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.popularBlockDestination(
    navController: NavController
) {
    composable(
        route = PopularBlockConstant.ROUTE
    ) {
        val viewModel: PopularBlockViewModel = hiltViewModel()

        val argument: PopularBlockArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            PopularBlockArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: PopularBlockData = let {
            val initialData = viewModel.initialData

            PopularBlockData(
                initialData = initialData
            )
        }

        ErrorObserver(viewModel)
        PopularBlockScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
