package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.profile

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.registerProfileDestination(
    navController: NavController
) {
    composable(
        route = RegisterProfileConstant.ROUTE
    ) {
        val viewModel: RegisterProfileViewModel = hiltViewModel()

        val argument: RegisterProfileArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            RegisterProfileArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        ErrorObserver(viewModel)
        RegisterProfileScreen(
            navController = navController,
            argument = argument
        )
    }
}
