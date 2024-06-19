package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.result

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.HeadlineRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Success
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButton
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonType
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeConstant

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterResultScreen(
    navController: NavController,
    argument: RegisterResultArgument
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    val introductionImageList = listOf(
        R.drawable.img_introduction_1,
        R.drawable.img_introduction_2,
        R.drawable.img_introduction_3,
        R.drawable.img_introduction_4,
        R.drawable.img_introduction_5,
        R.drawable.img_introduction_6,
        R.drawable.img_introduction_7,
        R.drawable.img_introduction_8,
        R.drawable.img_introduction_9,
        R.drawable.img_introduction_10,
        R.drawable.img_introduction_11,
        R.drawable.img_introduction_12,
        R.drawable.img_introduction_13,
        R.drawable.img_introduction_14,
    )
    val pagerState = rememberPagerState(
        pageCount = { Int.MAX_VALUE }
    )

    fun navigateToHome() {
        navController.safeNavigate(HomeConstant.ROUTE) {
            popUpTo(RegisterResultConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "회원가입 완료",
            style = HeadlineRegular.merge(Success)
        )
        Spacer(modifier = Modifier.height(Space10))
        Text(
            text = "프로필 설정이 완료되었습니다\n하비위클리, 사용방법을 살펴보세요!",
            style = TitleRegular.merge(Neutral900)
        )
        Spacer(modifier = Modifier.height(Space40))
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(Space20),
            pageSpacing = Space40,
            state = pagerState,
            userScrollEnabled = true
        ) { index ->
            val fixedIndex = index % introductionImageList.size
            val imageId = introductionImageList.getOrNull(fixedIndex) ?: return@HorizontalPager

            Image(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit,
                painter = painterResource(id = imageId),
                contentDescription = ""
            )
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
            onClick = {
                navigateToHome()
            }
        ) { style ->
            Text(
                text = "서비스 이용하기",
                style = style
            )
        }
    }

    LaunchedEffect(pagerState) {
        launch(handler) {
            while (true) {
                delay(2000L)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % pagerState.pageCount,
                    animationSpec = tween()
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
private fun RegisterResultScreenPreview() {
    RegisterResultScreen(
        navController = rememberNavController(),
        argument = RegisterResultArgument(
            state = RegisterResultState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        )
    )
}
