package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.search

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.communitySearchDestination(
    navController: NavController
) {
    composable(
        route = CommunitySearchConstant.ROUTE
    ) {
        val viewModel: CommunitySearchViewModel = hiltViewModel()

        val argument: CommunitySearchArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            CommunitySearchArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: CommunitySearchData = let {
            val suggestBlockList by viewModel.suggestBlockList.collectAsStateWithLifecycle()
            val searchBlockList by viewModel.searchBlockList.collectAsStateWithLifecycle()

            CommunitySearchData(
                suggestBlockList = suggestBlockList,
                searchBlockList = searchBlockList
            )
        }

        ErrorObserver(viewModel)
        CommunitySearchScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
