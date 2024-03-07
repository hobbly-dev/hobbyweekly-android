package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.hobby

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.common.theme.HeadlineRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space32
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space44
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space52
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space6
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButton
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonType
import kr.hobbly.hobbyweekly.android.presentation.common.view.textfield.TypingTextField
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.result.RegisterResultConstant

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterHobbyScreen(
    navController: NavController,
    argument: RegisterHobbyArgument,
    data: RegisterHobbyData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    val minimumHobbyCount = 5
    val additionalHobbyList = remember { mutableStateListOf<String>() }
    val hobbyList = data.hobbyList + additionalHobbyList
    val checkedHobbyList = remember { mutableStateListOf<String>() }
    var customHobby: String by remember { mutableStateOf("") }

    val isMinimumHobbyChecked = hobbyList.count { hobby ->
        checkedHobbyList.contains(hobby)
    } >= minimumHobbyCount

    val isCustomHobbyValid = hobbyList.all { it != customHobby }
    val isConfirmButtonEnabled = state != RegisterHobbyState.Loading && isMinimumHobbyChecked

    fun navigateToRegisterResult() {
        navController.safeNavigate(RegisterResultConstant.ROUTE) {
            popUpTo(RegisterHobbyConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "프로필 설정",
            style = HeadlineRegular.merge(Neutral900)
        )
        Spacer(modifier = Modifier.height(Space10))
        Text(
            text = "당신의 취미성향을 파악하기 위해\n좋아하는 취미 ${minimumHobbyCount}가지 이상을 골라주세요",
            style = TitleRegular.merge(Neutral900)
        )
        Spacer(modifier = Modifier.height(Space52))
        FlowRow(
            modifier = Modifier.padding(horizontal = Space32),
            horizontalArrangement = Arrangement.spacedBy(Space8),
            verticalArrangement = Arrangement.spacedBy(Space8),
        ) {
            hobbyList.forEach { hobby ->
                RegisterHobbyScreenItem(
                    hobby = hobby,
                    isSelected = checkedHobbyList.contains(hobby),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            checkedHobbyList.add(hobby)
                        } else {
                            checkedHobbyList.remove(hobby)
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(Space20))
        TypingTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Space40),
            text = customHobby,
            hintText = "취미를 입력해주세요",
            isError = !isCustomHobbyValid,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isCustomHobbyValid && customHobby.isNotBlank()) {
                        additionalHobbyList.add(customHobby)
                        customHobby = ""
                    }
                }
            ),
            onValueChange = {
                customHobby = it
            },
        )
        if (!isCustomHobbyValid) {
            Spacer(modifier = Modifier.height(Space6))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Space44),
                text = "이미 존재하는 취미입니다.",
                style = LabelRegular.merge(Red),
            )
        }
        Spacer(modifier = Modifier.height(Space80))
        ConfirmButton(
            modifier = Modifier
                .padding(start = 22.5.dp, end = 22.5.dp, bottom = 55.dp)
                .fillMaxWidth(),
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Primary
            ),
            isEnabled = isConfirmButtonEnabled,
            onClick = {
                intent(RegisterHobbyIntent.OnConfirm(checkedHobbyList))
            }
        ) { style ->
            Text(
                text = "다음",
                style = style
            )
        }
    }

    fun patchHobby(event: RegisterHobbyEvent.PatchHobby) {
        when (event) {
            RegisterHobbyEvent.PatchHobby.Success -> {
                navigateToRegisterResult()
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is RegisterHobbyEvent.PatchHobby -> {
                    patchHobby(event)
                }
            }
        }
    }
}

@Composable
fun RegisterHobbyScreenItem(
    hobby: String,
    isSelected: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Red else Neutral400),
        shape = RoundedCornerShape(100.dp),
        elevation = CardDefaults.cardElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Box(
            modifier = Modifier.clickable {
                onCheckedChange(!isSelected)
            }
        ) {
            Box(
                modifier = Modifier.padding(horizontal = Space12, vertical = Space6),
            ) {
                Text(
                    text = hobby,
                    style = TitleMedium.merge(White)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RegisterHobbyScreenPreview() {
    RegisterHobbyScreen(
        navController = rememberNavController(),
        argument = RegisterHobbyArgument(
            state = RegisterHobbyState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = RegisterHobbyData(
            hobbyList = listOf(
                "축구",
                "뜨개질",
                "다도",
                "유도",
                "독서",
                "요리",
                "산책",
                "헬스",
                "미술",
                "개발",
                "여행",
                "회화",
                "전시회",
                "발표",
                "먹방",
                "등산"
            )
        )
    )
}
