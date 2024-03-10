package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.blockDestination(
    navController: NavController
) {
    composable(
        route = BlockConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(BlockConstant.ROUTE_ARGUMENT_BLOCK_ID) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ) {
        val viewModel: BlockViewModel = hiltViewModel()

        val argument: BlockArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            BlockArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data by viewModel.blockData.collectAsStateWithLifecycle()

        ErrorObserver(viewModel)
        BlockScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
