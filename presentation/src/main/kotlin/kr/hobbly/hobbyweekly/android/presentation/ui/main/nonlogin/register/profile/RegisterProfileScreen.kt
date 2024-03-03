package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.HeadlineRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButton
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonType
import kr.hobbly.hobbyweekly.android.presentation.common.view.textfield.TypingTextField
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage
import kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery.GalleryScreen
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.hobby.RegisterHobbyConstant

@Composable
fun RegisterProfileScreen(
    navController: NavController,
    argument: RegisterProfileArgument
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    var image: GalleryImage? by rememberSaveable { mutableStateOf(null) }
    var nickname: String by rememberSaveable { mutableStateOf("") }

    var isNicknameDuplicated: Boolean by remember { mutableStateOf(false) }
    val isConfirmButtonEnabled = nickname.isNotBlank() && !isNicknameDuplicated

    var isGalleryShowing by remember { mutableStateOf(false) }

    if (isGalleryShowing) {
        GalleryScreen(
            navController = navController,
            onDismissRequest = { isGalleryShowing = false },
            onResult = { image = it }
        )
    }

    fun navigateToRegisterHobby() {
        navController.navigate(RegisterHobbyConstant.ROUTE) {
            popUpTo(RegisterProfileConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Box {
            Card(
                modifier = Modifier.size(150.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(White),
                elevation = cardElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                border = BorderStroke(3.dp, Red)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            isGalleryShowing = true
                        }
                ) {
                    image?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } ?: Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(Space40),
                        painter = painterResource(R.drawable.img_user_default),
                        contentDescription = null
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(
                            color = Red,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        modifier = Modifier.size(34.dp),
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        tint = White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(Space20))
        Text(
            text = "프로필 설정",
            style = HeadlineRegular
        )
        Spacer(modifier = Modifier.height(Space10))
        Text(
            text = "사용하실 닉네임을 입력해주세요",
            style = TitleRegular
        )
        Spacer(modifier = Modifier.height(Space40))
        TypingTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Space40),
            text = nickname,
            hintText = "닉네임",
            onValueChange = {
                nickname = it
                isNicknameDuplicated = false
            },
        )
        Spacer(modifier = Modifier.weight(1f))
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
                intent(
                    RegisterProfileIntent.OnConfirm(
                        nickname = nickname,
                        image = image
                    )
                )
            }
        ) { style ->
            Text(
                text = "다음",
                style = style
            )
        }
    }

    fun checkNickname(event: RegisterProfileEvent.CheckNickname) {
        when (event) {
            RegisterProfileEvent.CheckNickname.Failure -> {
                isNicknameDuplicated = true
            }
        }
    }

    fun setProfile(event: RegisterProfileEvent.SetProfile) {
        when (event) {
            RegisterProfileEvent.SetProfile.Success -> {
                navigateToRegisterHobby()
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is RegisterProfileEvent.CheckNickname -> {
                    checkNickname(event)
                }

                is RegisterProfileEvent.SetProfile -> {
                    setProfile(event)
                }
            }
        }
    }
}

@Preview
@Composable
private fun RegisterProfileScreenPreview() {
    RegisterProfileScreen(
        navController = rememberNavController(),
        argument = RegisterProfileArgument(
            state = RegisterProfileState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        )
    )
}
