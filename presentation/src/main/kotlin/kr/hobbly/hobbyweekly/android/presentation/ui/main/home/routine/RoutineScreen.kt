package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle

@Composable
fun RoutineScreen(
    navController: NavController
) {
    val viewModel: RoutineViewModel = hiltViewModel()

    val argument: RoutineArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        RoutineArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            handler = viewModel.handler
        )
    }

    val data: RoutineData = Unit.let {
        val initialData = viewModel.initialData

        RoutineData(
            initialData = initialData
        )
    }

    ErrorObserver(viewModel)
    RoutineScreen(
        navController = navController,
        argument = argument,
        data = data
    )
}

@Composable
private fun RoutineScreen(
    navController: NavController,
    argument: RoutineArgument,
    data: RoutineData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
private fun RoutineScreenPreview() {
    RoutineScreen(
        navController = rememberNavController(),
        argument = RoutineArgument(
            state = RoutineState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = RoutineData(
            initialData = ""
        )
    )
}
