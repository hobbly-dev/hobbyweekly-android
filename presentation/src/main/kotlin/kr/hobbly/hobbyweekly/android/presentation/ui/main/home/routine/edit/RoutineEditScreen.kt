package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.datetime.LocalTime
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelSemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral300
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space32
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space4
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space6
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Warning
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.view.DialogScreen
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButton
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonType
import kr.hobbly.hobbyweekly.android.presentation.common.view.dropdown.TextDropdownMenu
import kr.hobbly.hobbyweekly.android.presentation.common.view.textfield.TypingTextField
import kr.hobbly.hobbyweekly.android.presentation.ui.main.common.timepicker.TimePickerScreen
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeConstant

@Composable
fun RoutineEditScreen(
    navController: NavController,
    argument: RoutineEditArgument,
    data: RoutineEditData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val context = LocalContext.current

    val selectedDayOfWeek: MutableList<Int> = remember { mutableStateListOf<Int>() }
    var description: String by remember { mutableStateOf("") }
    var alarmTime: LocalTime? by remember { mutableStateOf(null) }

    val isConfirmButtonEnabled = selectedDayOfWeek.isNotEmpty() && state != RoutineEditState.Loading
    val formattedTime = alarmTime?.let {
        val format = "%s %02d:%02d"
        val fixedHour = if (it.hour == 0) 24 else it.hour
        val timeHour = (fixedHour - 1) % 12 + 1
        val timeMinute = it.minute
        val timeAmPm = if (fixedHour < 12 || fixedHour == 24) "오전" else "오후"
        runCatching {
            String.format(format, timeAmPm, timeHour, timeMinute)
        }.onFailure { exception ->
            scope.launch(handler) {
                throw exception
            }
        }.getOrDefault("?? ??:??")
    } ?: "시간 없음"

    var isMenuShowing by remember { mutableStateOf(false) }
    var isTimePickerShowing: Boolean by remember { mutableStateOf(false) }
    var isAddDialogShowing: Boolean by remember { mutableStateOf(false) }
    var isEditDialogShowing: Boolean by remember { mutableStateOf(false) }
    var isDeleteDialogShowing: Boolean by remember { mutableStateOf(false) }

    fun navigateToHome() {
        navController.popBackStack(HomeConstant.ROUTE, false)
    }

    if (isTimePickerShowing) {
        TimePickerScreen(
            localTime = alarmTime ?: LocalTime(0, 0),
            onDismissRequest = { isTimePickerShowing = false },
            onCancel = { alarmTime = null },
            onConfirm = { alarmTime = it }
        )
    }

    if (isAddDialogShowing) {
        DialogScreen(
            title = "루틴 추가",
            message = "루틴이 추가되었습니다.",
            isCancelable = false,
            onDismissRequest = {
                isAddDialogShowing = false
            },
            onConfirm = {
                navigateToHome()
            }
        )
    }

    if (isEditDialogShowing) {
        DialogScreen(
            title = "루틴 수정",
            message = "루틴이 수정되었습니다.",
            isCancelable = false,
            onDismissRequest = {
                isEditDialogShowing = false
            },
            onConfirm = {
                navigateToHome()
            }
        )
    }

    if (isDeleteDialogShowing) {
        DialogScreen(
            title = "루틴 삭제",
            message = "루틴이 삭제되었습니다.",
            isCancelable = false,
            onDismissRequest = {
                isDeleteDialogShowing = false
            },
            onConfirm = {
                navigateToHome()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .height(Space56)
                .background(White)
                .fillMaxWidth()
        ) {
            RippleBox(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = Space20),
                onClick = {
                    navController.safeNavigateUp()
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(Space24),
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = null,
                    tint = Neutral900
                )
            }
            Text(
                text = "하비위클리",
                modifier = Modifier.align(Alignment.Center),
                style = TitleSemiBoldSmall.merge(Neutral900)
            )
            if (data.isEditMode) {
                RippleBox(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = Space20),
                    onClick = {
                        isMenuShowing = true
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Space24),
                        painter = painterResource(R.drawable.ic_more_vertical),
                        contentDescription = null,
                        tint = Neutral900
                    )
                    TextDropdownMenu(
                        items = listOf("삭제"),
                        isExpanded = isMenuShowing,
                        onDismissRequest = { isMenuShowing = false },
                        onClick = { text ->
                            if (text == "삭제") {
                                intent(RoutineEditIntent.OnDelete)
                            }
                        }
                    )
                }
            }
        }
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(data.block.thumbnail)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .background(Neutral200)
                .aspectRatio(2.5f),
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(Space24)
                            .align(Alignment.Center),
                        color = White,
                        strokeWidth = 2.dp
                    )
                }
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Space24)
                            .align(Alignment.Center),
                        painter = painterResource(R.drawable.ic_error),
                        contentDescription = null,
                        tint = Warning
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(Space32))
        Row(
            modifier = Modifier.padding(horizontal = Space20),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(Space16),
                painter = painterResource(R.drawable.ic_calendar),
                contentDescription = null,
                tint = Neutral900
            )
            Spacer(modifier = Modifier.width(Space4))
            Text(
                text = "요일 설정",
                style = LabelMedium.merge(Neutral900)
            )
        }
        Spacer(modifier = Modifier.height(Space12))
        Row(
            modifier = Modifier.padding(horizontal = Space20),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Space20)
        ) {
            (0..6).forEach { dayOfWeek ->
                val text = when (dayOfWeek) {
                    0 -> "월"
                    1 -> "화"
                    2 -> "수"
                    3 -> "목"
                    4 -> "금"
                    5 -> "토"
                    6 -> "일"
                    else -> ""
                }
                val isSelected = selectedDayOfWeek.contains(dayOfWeek)
                RippleBox(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Red else Neutral100
                        ),
                    onClick = {
                        if (state == RoutineEditState.Loading) return@RippleBox
                        if (isSelected) {
                            selectedDayOfWeek.remove(dayOfWeek)
                        } else {
                            selectedDayOfWeek.add(dayOfWeek)
                        }
                    }
                ) {
                    Text(
                        text = text,
                        modifier = Modifier.align(Alignment.Center),
                        style = LabelMedium.merge(
                            if (isSelected) White else Neutral900
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(Space40))
        Row(
            modifier = Modifier.padding(horizontal = Space20),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(Space16),
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = null,
                tint = Neutral900
            )
            Spacer(modifier = Modifier.width(Space4))
            Text(
                text = "목표 설정",
                style = LabelMedium.merge(Neutral900)
            )
        }
        Spacer(modifier = Modifier.height(Space12))
        TypingTextField(
            text = description,
            modifier = Modifier
                .heightIn(min = 100.dp)
                .padding(horizontal = Space20),
            maxLines = Int.MAX_VALUE,
            isEnabled = state != RoutineEditState.Loading && !data.isEditMode,
            onValueChange = { description = it },
        )
        Spacer(modifier = Modifier.height(Space40))
        Column(
            modifier = Modifier.clickable {
                if (state == RoutineEditState.Loading) return@clickable
                isTimePickerShowing = true
            }
        ) {
            Spacer(modifier = Modifier.height(Space6))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(Space20))
                Icon(
                    modifier = Modifier.size(Space16),
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = Neutral900
                )
                Spacer(modifier = Modifier.width(Space4))
                Text(
                    text = "알람 설정",
                    style = LabelMedium.merge(Neutral900)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (alarmTime == null) "알람을 설정해주세요" else formattedTime,
                    style = LabelSemiBold.merge(Neutral300)
                )
                Icon(
                    modifier = Modifier.size(Space24),
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    tint = Neutral300
                )
                Spacer(modifier = Modifier.width(Space20))
            }
            Spacer(modifier = Modifier.height(Space6))
        }
        Spacer(modifier = Modifier.height(Space80))
        ConfirmButton(
            modifier = Modifier
                .padding(start = Space20, end = Space20, bottom = Space12)
                .fillMaxWidth(),
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Primary
            ),
            isEnabled = isConfirmButtonEnabled,
            onClick = {
                intent(
                    RoutineEditIntent.OnConfirm(
                        selectedDayOfWeek = selectedDayOfWeek,
                        description = description,
                        alarmTime = alarmTime
                    )
                )
            }
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
    }

    fun loadData(event: RoutineEditEvent.LoadData) {
        when (event) {
            is RoutineEditEvent.LoadData.Success -> {
                selectedDayOfWeek.clear()
                selectedDayOfWeek.addAll(event.routine.smallRoutineList.map { it.dayOfWeek })
                alarmTime = event.routine.alarmTime
                description = event.routine.title
            }
        }
    }

    fun addRoutine(event: RoutineEditEvent.AddRoutine) {
        when (event) {
            is RoutineEditEvent.AddRoutine.Success -> {
                isAddDialogShowing = true
            }
        }
    }

    fun editRoutine(event: RoutineEditEvent.EditRoutine) {
        when (event) {
            is RoutineEditEvent.EditRoutine.Success -> {
                isEditDialogShowing = true
            }
        }
    }

    fun deleteRoutine(event: RoutineEditEvent.DeleteRoutine) {
        when (event) {
            is RoutineEditEvent.DeleteRoutine.Success -> {
                isDeleteDialogShowing = true
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is RoutineEditEvent.LoadData -> {
                    loadData(event)
                }

                is RoutineEditEvent.AddRoutine -> {
                    addRoutine(event)
                }

                is RoutineEditEvent.EditRoutine -> {
                    editRoutine(event)
                }

                is RoutineEditEvent.DeleteRoutine -> {
                    deleteRoutine(event)
                }
            }
        }
    }
}

@Preview
@Composable
private fun RoutineEditScreenPreview() {
    RoutineEditScreen(
        navController = rememberNavController(),
        argument = RoutineEditArgument(
            state = RoutineEditState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = RoutineEditData(
            block = Block(
                id = 1,
                name = "영어 블록",
                content = "영어를 공부하고 인증하는 모임",
                image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                memberCount = 100
            ),
            isEditMode = false
        )
    )
}
