package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.search

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.boardSearchDestination(
    navController: NavController
) {
    composable(
        route = BoardSearchConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(BoardSearchConstant.ROUTE_ARGUMENT_BLOCK_ID) {
                type = NavType.LongType
                defaultValue = -1L
            },
            navArgument(BoardSearchConstant.ROUTE_ARGUMENT_BOARD_ID) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ) {
        val viewModel: BoardSearchViewModel = hiltViewModel()

        val argument: BoardSearchArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            BoardSearchArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: BoardSearchData = let {
            val searchPostList = viewModel.searchPostPaging.collectAsLazyPagingItems()

            BoardSearchData(
                searchPostPaging = searchPostList
            )
        }

        ErrorObserver(viewModel)
        BoardSearchScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
