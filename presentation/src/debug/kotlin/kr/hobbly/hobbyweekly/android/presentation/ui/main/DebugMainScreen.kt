package kr.hobbly.hobbyweekly.android.presentation.ui.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlin.math.roundToInt
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.ANIMATION_DURATION
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Blue
import kr.hobbly.hobbyweekly.android.presentation.common.theme.HobbyWeeklyTheme
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.ui.main.debug.DebugConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.debug.debugDestination
import kr.hobbly.hobbyweekly.android.presentation.ui.main.splash.SplashConstant

@Composable
fun DebugMainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    HobbyWeeklyTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = SplashConstant.ROUTE,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            }
        ) {
            mainDestination(navController)
            debugDestination(navController)
        }

        ErrorObserver(viewModel)
        MainScreenRefreshFailDialog(navController, viewModel.refreshFailEvent)
        DebugPopup(navController)
    }
}

@Composable
private fun DebugPopup(
    navController: NavController
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    RippleBox(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        onClick = {
            navController.safeNavigate(DebugConstant.ROUTE)
        }
    ) {
        Icon(
            modifier = Modifier.size(Space24),
            painter = painterResource(R.drawable.ic_error),
            contentDescription = null,
            tint = Blue
        )
    }
}
