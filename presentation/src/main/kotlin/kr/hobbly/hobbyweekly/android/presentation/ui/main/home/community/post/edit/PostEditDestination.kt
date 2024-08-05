package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.postEditDestination(
    navController: NavController
) {
    composable(
        route = PostEditConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(PostEditConstant.ROUTE_ARGUMENT_BLOCK_ID) {
                type = NavType.LongType
                defaultValue = -1L
            },
            navArgument(PostEditConstant.ROUTE_ARGUMENT_BOARD_ID) {
                type = NavType.LongType
                defaultValue = -1L
            },
            navArgument(PostEditConstant.ROUTE_ARGUMENT_POST_ID) {
                type = NavType.LongType
                defaultValue = -1L
            },
            navArgument(PostEditConstant.ROUTE_ARGUMENT_ROUTINE_ID) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ) {
        val viewModel: PostEditViewModel = hiltViewModel()

        val argument: PostEditArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            PostEditArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                coroutineContext = viewModel.coroutineContext
            )
        }

        val data: PostEditData = let {
            val block by viewModel.block.collectAsStateWithLifecycle()
            val board by viewModel.board.collectAsStateWithLifecycle()
            val blockId = viewModel.blockId
            val boardId = viewModel.boardId
            val postId = viewModel.postId
            val routineId = viewModel.postId

            PostEditData(
                block = block,
                board = board,
                blockId = blockId,
                boardId = boardId,
                postId = postId,
                routineId = routineId
            )
        }

        ErrorObserver(viewModel)
        PostEditScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
