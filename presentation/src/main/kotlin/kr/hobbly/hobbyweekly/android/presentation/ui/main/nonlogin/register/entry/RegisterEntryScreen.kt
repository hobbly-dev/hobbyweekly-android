package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.entry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.hobby.RegisterHobbyConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.profile.RegisterProfileConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.term.RegisterTermConstant

@Composable
fun RegisterEntryScreen(
    navController: NavController,
    argument: RegisterEntryArgument
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    fun navigateToRegisterTerm() {
        navController.navigate(RegisterTermConstant.ROUTE) {
            popUpTo(RegisterEntryConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun navigateToRegisterProfile() {
        navController.navigate(RegisterProfileConstant.ROUTE) {
            popUpTo(RegisterEntryConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun navigateToRegisterHobby() {
        navController.navigate(RegisterHobbyConstant.ROUTE) {
            popUpTo(RegisterEntryConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                RegisterEntryEvent.NeedNickname -> {
                    navigateToRegisterProfile()
                }

                RegisterEntryEvent.NeedTermAgreement -> {
                    navigateToRegisterTerm()
                }

                RegisterEntryEvent.NeedHobbyList -> {
                    navigateToRegisterHobby()
                }

                RegisterEntryEvent.NoProblem -> {
                    
                }
            }
        }
    }
}

@Preview
@Composable
private fun RegisterEntryScreenPreview() {
    RegisterEntryScreen(
        navController = rememberNavController(),
        argument = RegisterEntryArgument(
            state = RegisterEntryState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        )
    )
}
