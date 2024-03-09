package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.searchblock

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.searchBlockDestination(
    navController: NavController
) {
    composable(
        route = SearchBlockConstant.ROUTE
    ) {
        val viewModel: SearchBlockViewModel = hiltViewModel()

        val argument: SearchBlockArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            SearchBlockArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: SearchBlockData = let {
            val suggestBlockList by viewModel.suggestBlockList.collectAsStateWithLifecycle()
            val searchBlockList by viewModel.searchBlockList.collectAsStateWithLifecycle()

            SearchBlockData(
                suggestBlockList = suggestBlockList,
                searchBlockList = searchBlockList
            )
        }

        ErrorObserver(viewModel)
        SearchBlockScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
