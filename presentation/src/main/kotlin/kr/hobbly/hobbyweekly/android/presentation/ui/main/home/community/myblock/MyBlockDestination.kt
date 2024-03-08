package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.myblock

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.myBlockDestination(
    navController: NavController
) {
    composable(
        route = MyBlockConstant.ROUTE
    ) {
        val viewModel: MyBlockViewModel = hiltViewModel()

        val argument: MyBlockArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            MyBlockArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: MyBlockData = let {
            val initialData = viewModel.initialData

            MyBlockData(
                initialData = initialData
            )
        }

        ErrorObserver(viewModel)
        MyBlockScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
