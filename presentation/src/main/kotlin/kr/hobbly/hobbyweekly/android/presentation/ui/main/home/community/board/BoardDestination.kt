package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.boardDestination(
    navController: NavController
) {
    composable(
        route = BoardConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(BoardConstant.ROUTE_ARGUMENT_BLOCK_ID) {
                type = NavType.LongType
                defaultValue = -1L
            },
            navArgument(BoardConstant.ROUTE_ARGUMENT_BOARD_ID) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ) {
        val viewModel: BoardViewModel = hiltViewModel()

        val argument: BoardArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            BoardArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: BoardData = Unit.let {
            val block by viewModel.block.collectAsStateWithLifecycle()
            val board by viewModel.board.collectAsStateWithLifecycle()
            val postList by viewModel.postList.collectAsStateWithLifecycle()

            BoardData(
                block = block,
                board = board,
                postList = postList
            )
        }

        ErrorObserver(viewModel)
        BoardScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
