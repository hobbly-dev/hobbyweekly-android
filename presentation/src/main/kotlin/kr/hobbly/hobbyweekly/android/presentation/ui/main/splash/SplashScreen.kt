package kr.hobbly.hobbyweekly.android.presentation.ui.main.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.HeadlineRegular
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.NonLoginConstant

@Composable
fun SplashScreen(
    navController: NavController,
    argument: SplashArgument,
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    fun navigateToHome() {
        navController.navigate(HomeConstant.ROUTE) {
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
                navigateToHome()
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
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = ""
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = HeadlineRegular
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is SplashEvent.Login -> login(event)
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
