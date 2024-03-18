package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.mypage.RoutineStatistics
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodySemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral300
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Radius12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space60
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.makeRoute
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.dropdown.TextDropdownMenu
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery.GalleryScreen
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeArgument
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block.BlockConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.myblock.MyBlockConstant

@Composable
fun MyPageScreen(
    navController: NavController,
    parentArgument: HomeArgument,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val argument: MyPageArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        MyPageArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            handler = viewModel.handler
        )
    }

    val data: MyPageData = Unit.let {
        val profile by viewModel.profile.collectAsStateWithLifecycle()
        val myBlockList by viewModel.myBlockList.collectAsStateWithLifecycle()
        val routineStatistics by viewModel.routineStatistics.collectAsStateWithLifecycle()

        MyPageData(
            profile = profile,
            myBlockList = myBlockList,
            routineStatistics = routineStatistics
        )
    }

    ErrorObserver(viewModel)
    MyPageScreen(
        navController = navController,
        argument = argument,
        data = data
    )
}

@Composable
private fun MyPageScreen(
    navController: NavController,
    argument: MyPageArgument,
    data: MyPageData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val menu: List<String> = listOf(
        "로그아웃",
        "회원탈퇴"
    )

    var showingDate: LocalDate by remember { mutableStateOf(now.date) }

    val isLastMonth: Boolean =
        showingDate.year == now.date.year && showingDate.month == now.date.month
    val formattedDate: String = if (showingDate.year == now.date.year) {
        "${showingDate.month.number}월"
    } else {
        "${showingDate.year}년 ${showingDate.month.number}월"
    }

    var isMenuShowing by remember { mutableStateOf(false) }
    var isGalleryShowing by remember { mutableStateOf(false) }

    fun navigateToMyBlock() {
        navController.safeNavigate(MyBlockConstant.ROUTE)
    }

    fun navigateToBlock(
        block: Block
    ) {
        val route = makeRoute(
            BlockConstant.ROUTE,
            listOf(
                BlockConstant.ROUTE_ARGUMENT_BLOCK_ID to block.id
            )
        )
        navController.safeNavigate(route)
    }

    if (isGalleryShowing) {
        GalleryScreen(
            navController = navController,
            onDismissRequest = { isGalleryShowing = false },
            onResult = {
                val image = it.firstOrNull() ?: return@GalleryScreen
                intent(MyPageIntent.OnProfileImageSet(image))
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral050)
            .verticalScroll(rememberScrollState())
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
                    isMenuShowing = true
                }
            ) {
                Icon(
                    modifier = Modifier.size(Space24),
                    painter = painterResource(R.drawable.ic_more_vertical),
                    contentDescription = null,
                    tint = Neutral900
                )
                TextDropdownMenu(
                    items = menu,
                    isExpanded = isMenuShowing,
                    onDismissRequest = { isMenuShowing = false },
                    onClick = {
                        when (it) {
                            "로그아웃" -> {
                                intent(MyPageIntent.Logout)
                            }

                            "회원탈퇴" -> {
                                intent(MyPageIntent.Withdraw)
                            }
                        }
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
        ) {
            Spacer(modifier = Modifier.height(Space20))
            Row(
                modifier = Modifier
                    .padding(horizontal = Space20)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RippleBox(
                    onClick = {
                        showingDate = showingDate.minus(1, DateTimeUnit.MONTH)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(Space24),
                        painter = painterResource(R.drawable.ic_arrow_left),
                        contentDescription = null,
                        tint = Red
                    )
                }
                Text(
                    text = formattedDate,
                    style = LabelMedium.merge(Neutral900)
                )
                if (isLastMonth) {
                    Icon(
                        modifier = Modifier.size(Space24),
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Neutral400
                    )
                } else {
                    RippleBox(
                        onClick = {
                            showingDate = showingDate.plus(1, DateTimeUnit.MONTH)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(Space24),
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = null,
                            tint = Red
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(Space20))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MyPageScreenStatistics(
                    modifier = Modifier.size(150.dp),
                    routineStatistics = data.routineStatistics
                )
            }
            Spacer(modifier = Modifier.height(Space12))
            Text(
                text = "월간 루틴 달성률",
                modifier = Modifier
                    .padding(horizontal = Space20)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = LabelMedium.merge(Neutral400)
            )
            Spacer(modifier = Modifier.height(Space20))
        }
        Box(
            modifier = Modifier.background(White)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = Space20)
                    .clip(
                        RoundedCornerShape(
                            topStart = Radius12,
                            topEnd = Radius12
                        )
                    )
                    .fillMaxWidth()
                    .background(Neutral050)
            ) {
                Spacer(modifier = Modifier.height(Space60))
                Text(
                    text = data.profile.nickname,
                    modifier = Modifier
                        .padding(horizontal = Space20)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = LabelMedium.merge(Neutral900)
                )
                Spacer(modifier = Modifier.height(Space12))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Neutral200
                )
                Spacer(modifier = Modifier.height(Space20))
                Row(
                    modifier = Modifier
                        .padding(horizontal = Space20)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "내블록",
                        style = TitleSemiBoldSmall.merge(Neutral900)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (data.myBlockList.isNotEmpty()) {
                        RippleBox(
                            onClick = {
                                navigateToMyBlock()
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "더보기",
                                    style = LabelRegular.merge(Neutral300)
                                )
                                Icon(
                                    modifier = Modifier.size(21.dp),
                                    painter = painterResource(id = R.drawable.ic_chevron_right),
                                    contentDescription = null,
                                    tint = Neutral300
                                )
                            }
                        }
                    }
                }
                if (data.myBlockList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = Space20)
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Text(
                            text = "내 취미블록이 없습니다 블록을 추가해주세요",
                            modifier = Modifier.align(Alignment.Center),
                            style = LabelRegular.merge(Neutral400)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(Space12))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(Space16),
                        contentPadding = PaddingValues(start = Space20, end = Space20)
                    ) {
                        items(
                            items = data.myBlockList,
                            key = { it.id }
                        ) { block ->
                            MyPageScreenMyBlockItem(
                                block = block,
                                onClick = {
                                    navigateToBlock(it)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Space80))
            }
            Box(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Neutral200)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                isGalleryShowing = true
                            }
                    ) {
                        AsyncImage(
                            model = data.profile.image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Red,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = null,
                            tint = White
                        )
                    }
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
private fun MyPageScreenMyBlockItem(
    block: Block,
    onClick: (Block) -> Unit
) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(Radius12))
    ) {
        Column(
            modifier = Modifier.clickable {
                onClick(block)
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PostImage(
                data = block.thumbnail,
                modifier = Modifier.size(Space60)
            )
            Spacer(modifier = Modifier.height(Space8))
            Text(
                text = block.name,
                style = BodySemiBold.merge(Neutral900)
            )
        }
    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    MyPageScreen(
        navController = rememberNavController(),
        argument = MyPageArgument(
            state = MyPageState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = MyPageData(
            profile = Profile(
                id = 1,
                nickname = "장성혁",
                image = "https://avatars.githubusercontent.com/u/48707913?v=4",
                isHobbyChecked = true
            ),
            myBlockList = listOf(
                Block(
                    id = 1,
                    name = "영어 블록",
                    description = "영어를 공부하고 인증하는 모임",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                ),
                Block(
                    id = 2,
                    name = "요리 블록",
                    description = "취미로 요리를 하는 사람들의 모임",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                ),
                Block(
                    id = 3,
                    name = "여행 블록",
                    description = "여행을 취미로 하는 사람들의 모임",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                ),
                Block(
                    id = 4,
                    name = "공부 블록",
                    description = "공부를 취미로 하는 사람들의 모임",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                ),
                Block(
                    id = 5,
                    name = "코딩 블록",
                    description = "코딩을 취미로 하는 사람들의 모임",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                ),
            ),
            routineStatistics = RoutineStatistics(
                achievementRageList = listOf(
                    1f, 0.5f, 0f, 1f
                )
            )
        )
    )
}
