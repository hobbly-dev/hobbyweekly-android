package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.hobby

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.registerHobbyDestination(
    navController: NavController
) {
    composable(
        route = RegisterHobbyConstant.ROUTE
    ) {
        val viewModel: RegisterHobbyViewModel = hiltViewModel()

        val argument: RegisterHobbyArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            RegisterHobbyArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: RegisterHobbyData = let {
            val hobbyList by viewModel.hobbyList.collectAsStateWithLifecycle()

            RegisterHobbyData(
                hobbyList = hobbyList
            )
        }

        ErrorObserver(viewModel)
        RegisterHobbyScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
