package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.postDestination(
    navController: NavController
) {
    composable(
        route = PostConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(PostConstant.ROUTE_ARGUMENT_BOARD_ID) {
                type = NavType.LongType
                defaultValue = -1L
            },
            navArgument(PostConstant.ROUTE_ARGUMENT_POST_ID) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ) {
        val viewModel: PostViewModel = hiltViewModel()

        val argument: PostArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            PostArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: PostData = let {
            val initialData = viewModel.initialData

            PostData(
                initialData = initialData
            )
        }

        ErrorObserver(viewModel)
        PostScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
