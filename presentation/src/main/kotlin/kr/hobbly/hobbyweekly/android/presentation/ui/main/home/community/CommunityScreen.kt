package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.common.notification.NotificationConstant

@Composable
fun CommunityScreen(
    navController: NavController
) {
    val viewModel: CommunityViewModel = hiltViewModel()

    val argument: CommunityArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        CommunityArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            handler = viewModel.handler
        )
    }

    val data: CommunityData = Unit.let {
        val initialData = viewModel.initialData

        CommunityData(
            initialData = initialData
        )
    }

    ErrorObserver(viewModel)
    CommunityScreen(
        navController = navController,
        argument = argument,
        data = data
    )
}

@Composable
private fun CommunityScreen(
    navController: NavController,
    argument: CommunityArgument,
    data: CommunityData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    fun navigateToNotification() {
        navController.navigate(NotificationConstant.ROUTE)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Box(
            modifier = Modifier
                .height(Space56)
                .background(White)
                .fillMaxWidth()
        ) {
            Text(
                text = "하비위클리",
                modifier = Modifier.align(Alignment.Center),
                style = TitleSemiBoldSmall.merge(Neutral900)
            )
            RippleBox(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = Space20),
                onClick = {
                    navigateToNotification()
                }
            ) {
                Icon(
                    modifier = Modifier.size(Space24),
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = null,
                    tint = Neutral900
                )
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
private fun CommunityScreenPreview() {
    CommunityScreen(
        navController = rememberNavController(),
        argument = CommunityArgument(
            state = CommunityState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = CommunityData(
            initialData = ""
        )
    )
}
