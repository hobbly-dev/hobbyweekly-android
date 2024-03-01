package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.result

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.registerResultDestination(
    navController: NavController
) {
    composable(
        route = RegisterResultConstant.ROUTE
    ) {
        val viewModel: RegisterResultViewModel = hiltViewModel()

        val argument: RegisterResultArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            RegisterResultArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        ErrorObserver(viewModel)
        RegisterResultScreen(
            navController = navController,
            argument = argument
        )
    }
}
