package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.SmallRoutine
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral030
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral300
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral500
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Radius12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space48
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.view.BottomSheetScreen
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButton
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonType

@Composable
fun BoardRoutineScreen(
    navController: NavController,
    onDismissRequest: () -> Unit,
    onConfirm: (Routine) -> Unit,
    viewModel: BoardRoutineViewModel = hiltViewModel()
) {
    val argument: BoardRoutineArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        BoardRoutineArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            handler = viewModel.handler
        )
    }

    val data: BoardRoutineData = Unit.let {
        val routineList by viewModel.routineList.collectAsStateWithLifecycle()

        BoardRoutineData(
            routineList = routineList
        )
    }

    BoardRoutineScreen(
        navController = navController,
        argument = argument,
        data = data,
        onDismissRequest = onDismissRequest,
        onConfirm = onConfirm
    )
}

@Composable
private fun BoardRoutineScreen(
    navController: NavController,
    argument: BoardRoutineArgument,
    data: BoardRoutineData,
    onDismissRequest: () -> Unit,
    onConfirm: (Routine) -> Unit
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val routineList = data.routineList.filter {
        it.smallRoutineList.any { it.dayOfWeek == now.dayOfWeek.ordinal && it.isDone }
    }
    var selectedRoutine: Routine? by remember { mutableStateOf(null) }

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(White),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(Space40))
            Text(
                modifier = Modifier.padding(horizontal = Space20),
                text = "인증할 챌린지 선택",
                style = TitleSemiBold.merge(Neutral900)
            )
            Spacer(modifier = Modifier.height(Space20))
            if (routineList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(horizontal = Space20)
                        .clip(RoundedCornerShape(Radius12))
                        .background(Neutral050)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = Space20)
                            .align(Alignment.Center),
                        text = "금일 해당 커뮤니티의 챌린지가 존재하지 않습니다. 챌린지를 추가해주세요!",
                        style = TitleMedium.merge(Neutral400)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = Space20),
                    verticalArrangement = Arrangement.spacedBy(Space16)
                ) {
                    items(
                        items = routineList,
                        key = { it.id }
                    ) { routine ->
                        BoardRoutineScreenBlockItem(
                            routine = routine,
                            isSelected = selectedRoutine == routine,
                            onClick = {
                                selectedRoutine = if (selectedRoutine == it) {
                                    null
                                } else {
                                    it
                                }
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(Space80))
            ConfirmButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Space20, end = Space20, bottom = Space12),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                isEnabled = selectedRoutine != null,
                onClick = {
                    selectedRoutine?.let {
                        onDismissRequest()
                        onConfirm(it)
                    }
                }
            ) { style ->
                Text(
                    text = "확인",
                    style = style
                )
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
private fun BoardRoutineScreenBlockItem(
    routine: Routine,
    isSelected: Boolean,
    onClick: (Routine) -> Unit
) {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val isRoutineDone =
        routine.smallRoutineList.any { it.dayOfWeek == now.dayOfWeek.ordinal && it.isDone }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Radius12))
            .run {
                if (isSelected) {
                    background(Red)
                } else {
                    background(Neutral050)
                }
            }
    ) {
        Column(
            modifier = Modifier.run {
                if (isRoutineDone) {
                    this
                } else {
                    clickable {
                        onClick(routine)
                    }
                }
            }
        ) {
            Spacer(modifier = Modifier.height(Space10))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(Space20))
                Text(
                    text = routine.blockName,
                    style = if (isSelected) {
                        LabelMedium.merge(Neutral050)
                    } else {
                        LabelMedium.merge(Neutral900)
                    }
                )
                Spacer(modifier = Modifier.width(Space20))
                Text(
                    text = routine.title,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    style = if (isSelected) {
                        LabelMedium.merge(Neutral050)
                    } else {
                        LabelRegular.merge(Neutral500)
                    }
                )
                if (isRoutineDone) {
                    Spacer(modifier = Modifier.width(Space20))
                    Box(
                        modifier = Modifier
                            .size(Space48)
                            .background(
                                color = Neutral300,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = "인증\n완료",
                            modifier = Modifier
                                .align(Alignment.Center),
                            style = LabelRegular.merge(Neutral030)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(Space20))
            }
            Spacer(modifier = Modifier.height(Space10))
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun BoardRoutineScreenPreview1() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    BoardRoutineScreen(
        navController = rememberNavController(),
        argument = BoardRoutineArgument(
            state = BoardRoutineState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = BoardRoutineData(
            routineList = listOf(
                Routine(
                    id = 0L,
                    title = "상체 운동 30회",
                    blockId = 0L,
                    blockName = "헬스 블록",
                    alarmTime = null,
                    isAlarmEnabled = true,
                    smallRoutineList = listOf(
                        SmallRoutine(
                            dayOfWeek = now.dayOfWeek.ordinal,
                            isDone = true
                        )
                    )
                )
            )
        ),
        onDismissRequest = {},
        onConfirm = {}
    )
}

@Preview(apiLevel = 34)
@Composable
private fun BoardRoutineScreenPreview2() {
    BoardRoutineScreen(
        navController = rememberNavController(),
        argument = BoardRoutineArgument(
            state = BoardRoutineState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = BoardRoutineData(
            routineList = emptyList()
        ),
        onDismissRequest = {},
        onConfirm = {}
    )
}
