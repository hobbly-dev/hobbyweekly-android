package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Blue
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodyRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Green
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral030
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Orange
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Pink
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Purple
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Radius10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Radius12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space30
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space32
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space36
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space4
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space6
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Transparent
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Yellow
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.view.CustomSwitch
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.common.notification.NotificationConstant

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
        val routineList by viewModel.routineList.collectAsStateWithLifecycle()

        RoutineData(
            routineList = routineList
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
    val localConfiguration = LocalConfiguration.current

    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val routineList: MutableList<Routine> =
        remember { mutableStateListOf(*data.routineList.toTypedArray()) }
    var selectedDate: LocalDate by remember { mutableStateOf(now) }

    val currentRoutineList: List<Routine> = routineList.filter {
        it.dayOfWeek == selectedDate.dayOfWeek.ordinal
    }
    val isConfirmedDayOfWeek: List<Int> = routineList.filter { it.isConfirmed }
        .map { it.dayOfWeek }
        .toSet()
        .toList()

    fun navigateToNotification() {
        navController.navigate(NotificationConstant.ROUTE)
    }

    fun navigateToAddRoutine() {

    }

    fun navigateToEditRoutine(routine: Routine) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(White)
        ) {
            item {
                RoutineScreenBlock(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Neutral030)
                        .height(250.dp),
                    isConfirmedDayOfWeek = isConfirmedDayOfWeek
                )

                Box(
                    modifier = Modifier.background(Neutral030)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    topStart = Radius12,
                                    topEnd = Radius12
                                )
                            )
                            .fillMaxWidth()
                            .background(White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(Space36))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "HOBBYWEEKLY",
                                style = TitleSemiBold.merge(Neutral900)
                            )
                            Spacer(modifier = Modifier.width(Space4))
                            Icon(
                                modifier = Modifier.size(Space24),
                                painter = painterResource(R.drawable.ic_calendar),
                                contentDescription = null,
                                tint = Neutral900
                            )
                        }
                        Spacer(modifier = Modifier.height(Space20))
                        RoutineScreenCalendar(
                            selectedDate = selectedDate,
                            onSelect = { date ->
                                selectedDate = date
                            }
                        )
                        Spacer(modifier = Modifier.height(Space30))
                    }
                }
            }

            items(
                items = currentRoutineList,
//                key = {
//                    it.id
//                }
            ) { routine ->
                RoutineScreenItem(
                    routine = routine,
                    onEnableStateChanged = {
                        val index = routineList.indexOfFirst { it.id == routine.id }
                        val fixedRoutine = routine.copy(isEnabled = it)
                        routineList[index] = fixedRoutine
                        intent(RoutineIntent.OnEditRoutine(fixedRoutine))
                    },
                    onConfirm = {
                        val index = routineList.indexOfFirst { it.id == routine.id }
                        val fixedRoutine = routine.copy(isConfirmed = true)
                        routineList[index] = fixedRoutine
                        intent(RoutineIntent.OnEditRoutine(fixedRoutine))
                    },
                    onEdit = {
                        navigateToEditRoutine(routine)
                    }
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Space40)
                            .clip(RoundedCornerShape(Radius12))
                            .background(Neutral100)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigateToAddRoutine()
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.height(Space16))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(White)
                                    .size(Space32)
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(Space16)
                                        .align(Alignment.Center),
                                    painter = painterResource(R.drawable.ic_plus),
                                    contentDescription = null,
                                    tint = Neutral900
                                )
                            }
                            Spacer(modifier = Modifier.height(Space16))
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(Space80))
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
private fun RoutineScreenCalendar(
    selectedDate: LocalDate,
    onSelect: (LocalDate) -> Unit
) {
    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val currentWeekFirstDay: LocalDate = now.minus(now.dayOfWeek.ordinal, DateTimeUnit.DAY)
    val currentWeekDayList: List<LocalDate> = (0..6).map {
        currentWeekFirstDay.plus(it, DateTimeUnit.DAY)
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(Space4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        currentWeekDayList.forEach { date ->
            val isSelected = date == selectedDate
            val text = when (date.dayOfWeek.ordinal) {
                0 -> "MO"
                1 -> "TU"
                2 -> "WE"
                3 -> "TH"
                4 -> "FR"
                5 -> "SA"
                6 -> "SU"
                else -> ""
            }
            val containerColor = if (!isSelected) {
                Transparent
            } else when (date.dayOfWeek.ordinal) {
                0 -> Red
                1 -> Orange
                2 -> Yellow
                3 -> Green
                4 -> Blue
                5 -> Purple
                6 -> Pink
                else -> Transparent
            }
            val textColor = if (!isSelected) {
                Neutral400
            } else {
                White
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Radius10))
                    .background(containerColor)
            ) {
                Box(
                    modifier = Modifier.clickable {
                        onSelect(date)
                    }
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = Space8, vertical = Space6),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = text,
                            style = BodyRegular.merge(textColor)
                        )
                        Spacer(modifier = Modifier.height(Space16))
                        Text(
                            text = date.dayOfMonth.toString(),
                            style = BodyRegular.merge(textColor)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RoutineScreenItem(
    routine: Routine,
    onEnableStateChanged: (Boolean) -> Unit,
    onConfirm: () -> Unit,
    onEdit: () -> Unit
) {
    val containerColor = if (!routine.isEnabled) {
        Neutral200
    } else when (routine.dayOfWeek) {
        0 -> Red
        1 -> Orange
        2 -> Yellow
        3 -> Green
        4 -> Blue
        5 -> Purple
        6 -> Pink
        else -> Neutral200
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = Space40)
                .clip(RoundedCornerShape(Radius12))
                .background(containerColor)
        ) {
            Column(
                modifier = Modifier.clickable {
                    onEdit()
                }
            ) {
                Spacer(modifier = Modifier.height(Space8))
                Row(
                    modifier = Modifier.padding(horizontal = Space12),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomSwitch(
                        isChecked = routine.isEnabled,
                        onCheckedChange = {
                            onEnableStateChanged(it)
                        },
                        color = containerColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
//                RippleBox(
//                    onClick = {
//                        isMenuExpanded = true
//                    }
//                ) {
//                    Icon(
//                        modifier = Modifier.size(Space24),
//                        painter = painterResource(R.drawable.ic_more_vertical),
//                        contentDescription = null,
//                        tint = White
//                    )
//                    DropdownMenu(
//                        modifier = Modifier
//                            .requiredSizeIn(maxHeight = localConfiguration.screenHeightDp.dp - Space80)
//                            .width(localConfiguration.screenWidthDp.dp * 2 / 5)
//                            .background(White),
//                        expanded = isMenuExpanded,
//                        onDismissRequest = { isMenuExpanded = false },
//                    ) {
//                        DropdownMenuItem(
//                            text = {
//                                Text(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    text = "수정하기",
//                                    style = TitleMedium.merge(Neutral900)
//                                )
//                            },
//                            onClick = {
//                                onEdit()
//                            },
//                            contentPadding = PaddingValues(0.dp)
//                        )
//                        DropdownMenuItem(
//                            text = {
//                                Text(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    text = "삭제하기",
//                                    style = TitleMedium.merge(Neutral900)
//                                )
//                            },
//                            onClick = {
//                                onDelete()
//                            },
//                            contentPadding = PaddingValues(0.dp)
//                        )
//                    }
//                }
                }
                Spacer(modifier = Modifier.height(Space12))
                Row(
                    modifier = Modifier.padding(horizontal = Space12),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = routine.blockName,
                        style = BodyRegular.merge(White)
                    )
                    Spacer(modifier = Modifier.width(Space10))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = routine.description,
                        style = BodyRegular.merge(White),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(Space10))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Radius12))
                            .background(White)
                    ) {
                        Box(
                            modifier = Modifier.clickable {
                                if (!routine.isConfirmed) {
                                    onConfirm()
                                }
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = Space12,
                                    vertical = Space4
                                ),
                                text = if (routine.isConfirmed) "인증완료" else "인증하기",
                                style = BodyRegular.merge(containerColor)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Space8))
            }
        }
        Spacer(modifier = Modifier.height(Space20))
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
            routineList = listOf(
                Routine(
                    id = 0,
                    blockName = "블록 이름",
                    dayOfWeek = 0,
                    description = "설명",
                    alarmTime = null,
                    isEnabled = true,
                    isConfirmed = false
                )
            )
        )
    )
}
