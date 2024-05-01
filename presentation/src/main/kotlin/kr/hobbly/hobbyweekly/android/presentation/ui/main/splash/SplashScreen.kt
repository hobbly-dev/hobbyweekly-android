package kr.hobbly.hobbyweekly.android.presentation.ui.main.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.registerInstantRoutineList
import kr.hobbly.hobbyweekly.android.presentation.common.util.registerRepeatRoutineList
import kr.hobbly.hobbyweekly.android.presentation.common.util.unregisterAlarmAll
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.NonLoginConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.RegisterConstant

@Composable
fun SplashScreen(
    navController: NavController,
    argument: SplashArgument,
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val context = LocalContext.current

    fun navigateToRegister() {
        navController.navigate(RegisterConstant.ROUTE) {
            popUpTo(SplashConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun navigateToNonLogin() {
        navController.navigate(NonLoginConstant.ROUTE) {
            popUpTo(SplashConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun login(event: SplashEvent.Login) {
        when (event) {
            is SplashEvent.Login.Success -> {
                context.unregisterAlarmAll(event.currentRoutineList + event.latestRoutineList)
                context.registerInstantRoutineList(event.currentRoutineList)
                context.registerRepeatRoutineList(event.latestRoutineList)

                navigateToRegister()
            }

            is SplashEvent.Login.Fail -> {
                navigateToNonLogin()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(Space80),
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = ""
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            scope.launch {
                when (event) {
                    is SplashEvent.Login -> login(event)
                }
            }
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        navController = rememberNavController(),
        argument = SplashArgument(
            state = SplashState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        )
    )
}
