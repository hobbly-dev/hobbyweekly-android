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
import androidx.compose.ui.platform.LocalContext
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
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.SmallRoutine
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
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.makeRoute
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.util.registerRoutineList
import kr.hobbly.hobbyweekly.android.presentation.common.util.unregisterRoutine
import kr.hobbly.hobbyweekly.android.presentation.common.view.CustomSwitch
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.model.routine.RoutineStatisticsItem
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeArgument
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeIntent
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeState
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeType
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit.PostEditConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification.NotificationConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.block.RoutineBlockScreen
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit.RoutineEditConstant

@Composable
fun RoutineScreen(
    navController: NavController,
    parentArgument: HomeArgument
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
        val currentRoutineList by viewModel.currentRoutineList.collectAsStateWithLifecycle()
        val latestRoutineList by viewModel.latestRoutineList.collectAsStateWithLifecycle()

        RoutineData(
            currentRoutineList = currentRoutineList,
            latestRoutineList = latestRoutineList
        )
    }

    ErrorObserver(viewModel)
    RoutineScreen(
        navController = navController,
        parentArgument = parentArgument,
        argument = argument,
        data = data
    )
}

@Composable
private fun RoutineScreen(
    navController: NavController,
    parentArgument: HomeArgument,
    argument: RoutineArgument,
    data: RoutineData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val context = LocalContext.current

    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val routineList: MutableList<Routine> =
        remember { mutableStateListOf(*data.currentRoutineList.toTypedArray()) }
    var selectedDate: LocalDate by remember { mutableStateOf(now) }
    val selectedDayOfWeek = selectedDate.dayOfWeek.ordinal

    val currentRoutineList: List<Routine> = routineList.filter {
        it.smallRoutine.any { it.dayOfWeek == selectedDate.dayOfWeek.ordinal }
    }
    // TODO
    val routineStatisticsList: List<RoutineStatisticsItem> = routineList.map { routine ->
        routine.smallRoutine.map { it to it.isDone }
    }.flatten().groupBy { it.first }.map { (key, value) ->
        RoutineStatisticsItem(
            dayOfWeek = key.dayOfWeek,
            routineCount = value.count(),
            confirmedRoutineCount = value.count { it.second }
        )
    }

    var isRoutineBlockShowing: Boolean by remember { mutableStateOf(false) }

    fun navigateToNotification() {
        navController.safeNavigate(NotificationConstant.ROUTE)
    }

    fun navigateToAddRoutine(block: Block) {
        val route = makeRoute(
            RoutineEditConstant.ROUTE,
            listOf(
                RoutineEditConstant.ROUTE_ARGUMENT_BLOCK_ID to block.id
            )
        )
        navController.safeNavigate(route)
    }

    fun navigateToEditRoutine(routine: Routine) {
        val route = makeRoute(
            RoutineEditConstant.ROUTE,
            listOf(
                RoutineEditConstant.ROUTE_ARGUMENT_ROUTINE_ID to routine.id
            )
        )
        navController.navigate(route)
    }

    fun navigateToCommunity() {
        parentArgument.intent(HomeIntent.HomeTypeChange(HomeType.Community))
    }

    fun navigateToPostAdd(
        routine: Routine
    ) {
        val route = makeRoute(
            PostEditConstant.ROUTE,
            listOf(
                PostEditConstant.ROUTE_ARGUMENT_BLOCK_ID to routine.blockId,
                PostEditConstant.ROUTE_ARGUMENT_ROUTINE_ID to routine.id
            )
        )
        navController.safeNavigate(route)
    }

    if (isRoutineBlockShowing) {
        RoutineBlockScreen(
            navController = navController,
            onDismissRequest = { isRoutineBlockShowing = false },
            onClickBlock = { navigateToAddRoutine(it) },
            onConfirm = { navigateToCommunity() }
        )
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
                    routineStatisticsList = routineStatisticsList
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
                    selectedDayOfWeek = selectedDayOfWeek,
                    onEnableStateChanged = {
                        val index = routineList.indexOfFirst { it.id == routine.id }
                        val fixedRoutine = routine.copy(isEnabled = it)
                        routineList[index] = fixedRoutine
                        intent(RoutineIntent.OnSwitch(fixedRoutine))
                    },
                    onConfirm = {
                        navigateToPostAdd(routine)
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
                                    isRoutineBlockShowing = true
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

    fun updateAlarm(event: RoutineEvent.UpdateAlarm) {
        when (event) {
            is RoutineEvent.UpdateAlarm.Off -> {
                context.registerRoutineList(listOf(event.routine))
            }

            is RoutineEvent.UpdateAlarm.On -> {
                context.unregisterRoutine(event.routine)
            }

            is RoutineEvent.UpdateAlarm.Refresh -> {
                // TODO
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is RoutineEvent.UpdateAlarm -> {
                    updateAlarm(event)
                }
            }
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
    selectedDayOfWeek: Int,
    onEnableStateChanged: (Boolean) -> Unit,
    onConfirm: () -> Unit,
    onEdit: () -> Unit
) {
    val containerColor = if (!routine.isEnabled) {
        Neutral200
    } else when (selectedDayOfWeek) {
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
                        text = routine.title,
                        style = BodyRegular.merge(White)
                    )
                    Spacer(modifier = Modifier.width(Space10))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = routine.title,
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
                                if (routine.smallRoutine.find { it.dayOfWeek == selectedDayOfWeek }?.isDone == false) {
                                    onConfirm()
                                }
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = Space12,
                                    vertical = Space4
                                ),
                                text = if (routine.smallRoutine.find { it.dayOfWeek == selectedDayOfWeek }?.isDone == true) "인증완료" else "인증하기",
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
        parentArgument = HomeArgument(
            state = HomeState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        argument = RoutineArgument(
            state = RoutineState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = RoutineData(
            currentRoutineList = listOf(
                Routine(
                    id = 0L,
                    title = "블록 이름",
                    blockId = 0L,
                    blockName = "영어 블록",
                    alarmTime = null,
                    isEnabled = true,
                    smallRoutine = listOf(
                        SmallRoutine(
                            dayOfWeek = 0,
                            isDone = true
                        ),
                        SmallRoutine(
                            dayOfWeek = 1,
                            isDone = true
                        ),
                        SmallRoutine(
                            dayOfWeek = 2,
                            isDone = false
                        )
                    )
                )
            ),
            latestRoutineList = listOf(
                Routine(
                    id = 0L,
                    title = "블록 이름",
                    blockId = 0L,
                    blockName = "영어 블록",
                    alarmTime = null,
                    isEnabled = true,
                    smallRoutine = listOf(
                        SmallRoutine(
                            dayOfWeek = 0,
                            isDone = true
                        ),
                        SmallRoutine(
                            dayOfWeek = 1,
                            isDone = true
                        ),
                        SmallRoutine(
                            dayOfWeek = 2,
                            isDone = false
                        )
                    )
                )
            )
        )
    )
}
