package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess
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
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodySemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelSemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral300
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral600
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Radius12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space4
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space60
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Transparent
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.view.DialogScreen
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.dropdown.TextDropdownMenu
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery.GalleryConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeArgument
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.myblock.MyBlockConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.statistics.MyPageStatisticsConstant
import timber.log.Timber

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
        val routineStatisticsList by viewModel.routineStatisticsList.collectAsStateWithLifecycle()

        MyPageData(
            profile = profile,
            routineStatisticsList = routineStatisticsList
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
    val context = LocalContext.current

    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val menu: List<String> = listOf(
        "로그아웃",
        "문의하기",
        "정책약관",
        "회원탈퇴"
    )
    val recentDate: LocalDate = now.date.minus(
        now.date.dayOfWeek.ordinal,
        DateTimeUnit.DAY
    ).minus(
        1,
        DateTimeUnit.WEEK
    )

    var showingDate: LocalDate by remember { mutableStateOf(recentDate) }
    val unselectedBlockList: MutableList<Long> = remember { mutableStateListOf() }

    val isLastWeek: Boolean = showingDate == recentDate
    val week = (showingDate.dayOfMonth - 1) / 7 + 1
    val formattedDate: String = if (showingDate.year == now.date.year) {
        "${showingDate.month.number}월 ${week}주차"
    } else {
        "${showingDate.year}년 ${showingDate.month.number}월 ${week}주차"
    }

    var isMenuShowing by remember { mutableStateOf(false) }
    var isLogoutSuccessDialogShowing by remember { mutableStateOf(false) }
    var isWithdrawSuccessDialogShowing by remember { mutableStateOf(false) }
    var isLastStatisticsUnavailableDialogShowing by remember { mutableStateOf(false) }

    fun navigateToMyBlock() {
        navController.safeNavigate(MyBlockConstant.ROUTE)
    }

    fun navigateToMyPageStatistics() {
        navController.safeNavigate(MyPageStatisticsConstant.ROUTE)
    }

    fun navigateToInquiry() {
        runCatching {
            val link =
                Uri.parse("https://forms.gle/3W6qTYfEpusS5dnf6")
            val browserIntent = Intent(Intent.ACTION_VIEW, link)
            ContextCompat.startActivity(context, browserIntent, null)
        }.onFailure { exception ->
            Timber.d(exception)
//            Sentry.withScope {
//                it.level = SentryLevel.INFO
//                Sentry.captureException(exception)
//            }
            Firebase.crashlytics.recordException(exception)
        }
    }

    fun navigateToTerm() {
        runCatching {
            val link =
                Uri.parse("https://sites.google.com/view/hobblyofficial/hobby-weekly?authuser=0#h.86okzz2wkj1r")
            val browserIntent = Intent(Intent.ACTION_VIEW, link)
            ContextCompat.startActivity(context, browserIntent, null)
        }.onFailure { exception ->
            Timber.d(exception)
//            Sentry.withScope {
//                it.level = SentryLevel.INFO
//                Sentry.captureException(exception)
//            }
            Firebase.crashlytics.recordException(exception)
        }
    }

    fun navigateToGallery() {
        navController.safeNavigate(GalleryConstant.ROUTE)
    }

    // TODO : Migrate after Ktor 3.0.0
    fun restartApp() {
        context.packageManager.getLaunchIntentForPackage(context.packageName)?.let { intent ->
            context.startActivity(
                Intent.makeRestartActivityTask(intent.component)
            )
        }
        exitProcess(0)
    }

    if (isLogoutSuccessDialogShowing) {
        DialogScreen(
            isCancelable = false,
            title = "마이페이지 알람",
            message = "로그아웃했습니다.\n다시 로그인해주세요.",
            onConfirm = {
                restartApp()
            },
            onDismissRequest = {
                isLogoutSuccessDialogShowing = false
            }
        )
    }

    if (isWithdrawSuccessDialogShowing) {
        DialogScreen(
            isCancelable = false,
            title = "마이페이지 알람",
            message = "탈퇴 완료 했습니다.\n다시 회원가입해주세요.",
            onConfirm = {
                restartApp()
            },
            onDismissRequest = {
                isWithdrawSuccessDialogShowing = false
            }
        )
    }

    if (isLastStatisticsUnavailableDialogShowing) {
        DialogScreen(
            isCancelable = true,
            title = "마이페이지 알람",
            message = "통계는 이미 진행된 주차만\n확인이 가능합니다.",
            onDismissRequest = {
                isLastStatisticsUnavailableDialogShowing = false
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
                text = "루틴 통계",
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
                                logEvent("btn_sign_out", emptyMap())
                                intent(MyPageIntent.Logout)
                            }

                            "문의하기" -> {
                                logEvent("btn_question", emptyMap())
                                navigateToInquiry()
                            }

                            "정책약관" -> {
                                logEvent("btn_policy", emptyMap())
                                navigateToTerm()
                            }

                            "회원탈퇴" -> {
                                logEvent("btn_sign_delete", emptyMap())
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
                        showingDate = showingDate.minus(1, DateTimeUnit.WEEK)
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
                RippleBox(
                    onClick = {
                        if (isLastWeek) {
                            isLastStatisticsUnavailableDialogShowing = true
                        } else {
                            showingDate = showingDate.plus(1, DateTimeUnit.WEEK)
                        }
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
            Spacer(modifier = Modifier.height(Space20))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MyPageScreenStatistics(
                    modifier = Modifier.size(150.dp),
                    dataList = data.routineStatisticsList.filter { !unselectedBlockList.contains(it.id) },
                    thickness = 0.4f
                )
            }
            Spacer(modifier = Modifier.height(Space12))
            Row(
                modifier = Modifier
                    .padding(horizontal = Space20)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "주간 챌린지 달성률",
                    textAlign = TextAlign.Center,
                    style = LabelMedium.merge(Neutral300)
                )
                Spacer(modifier = Modifier.width(Space10))
                RippleBox(
                    onClick = {
                        logEvent("clk_stat_plus", emptyMap())
                        navigateToMyPageStatistics()
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
                    if (data.routineStatisticsList.isNotEmpty()) {
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
                if (data.routineStatisticsList.isEmpty()) {
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
                            items = data.routineStatisticsList.groupBy {
                                it.id
                            }.map { (key, value) ->
                                val item = value.firstOrNull()
                                RoutineStatistics(
                                    id = item?.id ?: -1L,
                                    blockName = item?.blockName.orEmpty(),
                                    thumbnail = item?.thumbnail.orEmpty(),
                                    title = item?.title.orEmpty(),
                                    totalCount = value.sumOf { it.totalCount },
                                    completedCount = value.sumOf { it.completedCount }
                                )
                            }
                        ) { statistics ->
                            MyPageScreenStatisticsItem(
                                statistics = statistics,
                                isSelected = !unselectedBlockList.contains(statistics.id),
                                onClick = {
                                    logEvent(
                                        "clk_stat_blk",
                                        mapOf("clk_stat_blk_id" to it.id)
                                    )
                                    if (unselectedBlockList.contains(it.id)) {
                                        unselectedBlockList.remove(it.id)
                                    } else {
                                        unselectedBlockList.add(it.id)
                                    }
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
                    if (state == MyPageState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(Space24)
                                .align(Alignment.Center),
                            color = White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    navigateToGallery()
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

    fun logout(event: MyPageEvent.Logout) {
        when (event) {
            MyPageEvent.Logout.Success -> {
                isLogoutSuccessDialogShowing = true
            }
        }
    }

    fun withdraw(event: MyPageEvent.Withdraw) {
        when (event) {
            MyPageEvent.Withdraw.Success -> {
                isWithdrawSuccessDialogShowing = true
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is MyPageEvent.Logout -> {
                    logout(event)
                }

                is MyPageEvent.Withdraw -> {
                    withdraw(event)
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(showingDate, handler) {
        intent(MyPageIntent.OnDateChanged(showingDate))
    }
}

@Composable
private fun MyPageScreenStatisticsItem(
    statistics: RoutineStatistics,
    isSelected: Boolean,
    onClick: (RoutineStatistics) -> Unit
) {
    val percent = if (statistics.totalCount == 0) {
        0
    } else {
        (statistics.completedCount.toFloat() / statistics.totalCount.toFloat() * 100).toInt()
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Radius12))
            .background(
                if (isSelected) {
                    Neutral100
                } else {
                    Transparent
                }
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    onClick(statistics)
                }
                .padding(Space4)
                .clip(RoundedCornerShape(Radius12))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    PostImage(
                        data = statistics.thumbnail,
                        modifier = Modifier
                            .size(Space60)
                            .align(Alignment.Center)
                    )
                    Text(
                        text = "$percent%",
                        modifier = Modifier.align(Alignment.Center),
                        style = LabelSemiBold.merge(White)
                    )
                }
                Spacer(modifier = Modifier.height(Space8))
                Text(
                    text = statistics.blockName,
                    style = BodySemiBold.merge(Neutral600)
                )
            }
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
            routineStatisticsList = listOf(
                RoutineStatistics(
                    id = 1L,
                    blockName = "독서 블록",
                    thumbnail = "https://via.placeholder.com/150",
                    title = "해리포터 원문보기",
                    totalCount = 5,
                    completedCount = 5
                ),
                RoutineStatistics(
                    id = 2L,
                    blockName = "영어 블록",
                    thumbnail = "https://via.placeholder.com/150",
                    title = "영화자막 번역하기",
                    totalCount = 4,
                    completedCount = 2
                ),
                RoutineStatistics(
                    id = 3L,
                    blockName = "공부 블록",
                    thumbnail = "https://via.placeholder.com/150",
                    title = "해외 친구들 만나기",
                    totalCount = 4,
                    completedCount = 1
                )
            )
        )
    )
}
