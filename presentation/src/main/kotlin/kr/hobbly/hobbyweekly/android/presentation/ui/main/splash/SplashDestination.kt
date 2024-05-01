package kr.hobbly.hobbyweekly.android.presentation.ui.main.splash

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.hobbly.hobbyweekly.android.presentation.common.ANIMATION_DURATION
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.splashDestination(
    navController: NavController
) {
    composable(
        route = SplashConstant.ROUTE,
        enterTransition = { fadeIn(animationSpec = tween(ANIMATION_DURATION)) },
        exitTransition = { fadeOut(animationSpec = tween(ANIMATION_DURATION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(ANIMATION_DURATION)) },
        popExitTransition = { fadeOut(animationSpec = tween(ANIMATION_DURATION)) },
    ) {
        val viewModel: SplashViewModel = hiltViewModel()

        val argument: SplashArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            SplashArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        ErrorObserver(viewModel)
        SplashScreen(
            navController = navController,
            argument = argument
        )
    }
}
