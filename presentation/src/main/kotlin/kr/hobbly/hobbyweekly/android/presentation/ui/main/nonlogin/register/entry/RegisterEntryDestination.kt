package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.entry

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.registerEntryDestination(
    navController: NavController
) {
    composable(
        route = RegisterEntryConstant.ROUTE
    ) {
        val viewModel: RegisterEntryViewModel = hiltViewModel()

        val argument: RegisterEntryArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            RegisterEntryArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                coroutineContext = viewModel.coroutineContext
            )
        }

        ErrorObserver(viewModel)
        RegisterEntryScreen(
            navController = navController,
            argument = argument
        )
    }
}
