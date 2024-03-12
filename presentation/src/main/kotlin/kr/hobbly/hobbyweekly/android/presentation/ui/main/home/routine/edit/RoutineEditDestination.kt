package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.routineEditDestination(
    navController: NavController
) {
    composable(
        route = RoutineEditConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(RoutineEditConstant.ROUTE_ARGUMENT_ROUTINE_ID) {
                type = NavType.LongType
                defaultValue = -1
            },
            navArgument(RoutineEditConstant.ROUTE_ARGUMENT_BLOCK_ID) {
                type = NavType.LongType
                defaultValue = -1
            }
        )
    ) {
        val viewModel: RoutineEditViewModel = hiltViewModel()

        val argument: RoutineEditArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            RoutineEditArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: RoutineEditData = let {
            val block by viewModel.block.collectAsStateWithLifecycle()

            RoutineEditData(
                block = block
            )
        }

        ErrorObserver(viewModel)
        RoutineEditScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
