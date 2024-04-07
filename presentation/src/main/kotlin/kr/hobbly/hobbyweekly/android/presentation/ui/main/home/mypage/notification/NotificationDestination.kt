package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.notificationDestination(
    navController: NavController
) {
    composable(
        route = NotificationConstant.ROUTE
    ) {
        val viewModel: NotificationViewModel = hiltViewModel()

        val argument: NotificationArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            NotificationArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: NotificationData = let {
            val notificationPaging = viewModel.notificationPaging.collectAsLazyPagingItems()

            NotificationData(
                notificationPaging = notificationPaging
            )
        }

        ErrorObserver(viewModel)
        NotificationScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
