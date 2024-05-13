package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.statistics

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlin.random.Random
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
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodyRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodySemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space4
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox

@Composable
fun MyPageStatisticsScreen(
    navController: NavController,
    argument: MyPageStatisticsArgument,
    data: MyPageStatisticsData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val totalCount = data.routineStatisticsList.sumOf { it.totalCount }
    val completedCount = data.routineStatisticsList.sumOf { it.completedCount }
    val completedPercent = if (completedCount == 0) {
        0
    } else {
        100 * completedCount / totalCount
    }
    val recentDate: LocalDate = now.date.minus(
        now.date.dayOfWeek.ordinal,
        DateTimeUnit.DAY
    ).minus(
        1,
        DateTimeUnit.WEEK
    )

    var showingDate: LocalDate by remember { mutableStateOf(recentDate) }

    val isLastWeek: Boolean = showingDate == recentDate
    val week = (showingDate.dayOfMonth - 1) / 7 + 1
    val formattedDate: String = if (showingDate.year == now.date.year) {
        "${showingDate.month.number}월 ${week}주차"

    } else {
        "${showingDate.year}년 ${showingDate.month.number}월 ${week}주차"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                text = "주간챌린지 달성률",
                modifier = Modifier.align(Alignment.Center),
                style = TitleSemiBoldSmall.merge(Neutral900)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
        ) {
            item {
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
                    if (isLastWeek) {
                        Icon(
                            modifier = Modifier.size(Space24),
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = null,
                            tint = Neutral400
                        )
                    } else {
                        RippleBox(
                            onClick = {
                                showingDate = showingDate.plus(1, DateTimeUnit.WEEK)
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
                MyPageStatisticsScreenStatistics(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Space40)
                        .padding(horizontal = Space20),
                    dataList = data.routineStatisticsList
                )
                Spacer(modifier = Modifier.height(Space12))
                Text(
                    text = "총 ${totalCount}개 챌린지 중에 총 ${completedCount}개 달성 (${completedPercent}%)",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Space20),
                    textAlign = TextAlign.End,
                    style = LabelMedium.merge(Neutral400)
                )
                Spacer(modifier = Modifier.height(Space40))
            }
            items(
                items = data.routineStatisticsList
            ) { statistics ->
                MyPageStatisticsScreenItem(
                    statistics = statistics
                )
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }

    LaunchedEffectWithLifecycle(showingDate, handler) {
        intent(MyPageStatisticsIntent.OnDateChanged(showingDate))
    }
}

@Composable
fun MyPageStatisticsScreenItem(
    statistics: RoutineStatistics
) {
    val totalCount = statistics.totalCount
    val completedCount = statistics.completedCount
    val completedPercent = if (completedCount == 0) {
        0
    } else {
        100 * completedCount / totalCount
    }
    val seed = statistics.title.sumOf { it.code }
    val color = Color(0xFF000000 or LongRange(0x000000, 0xFFFFFF).random(Random(seed)))

    Column {
        Spacer(modifier = Modifier.height(Space10))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(Space20))
            Box(
                modifier = Modifier
                    .size(Space40)
                    .background(
                        color = color,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .size(Space20)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.img_user_default),
                    contentDescription = null,
                    tint = White
                )
            }
            Spacer(modifier = Modifier.width(Space20))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = statistics.title,
                    overflow = TextOverflow.Ellipsis,
                    style = BodySemiBold.merge(Neutral900)
                )
                Spacer(modifier = Modifier.height(Space4))
                Text(
                    text = "${completedPercent}%",
                    overflow = TextOverflow.Ellipsis,
                    style = BodyRegular.merge(Neutral400)
                )
            }
            Spacer(modifier = Modifier.width(Space20))
            Text(
                text = "${totalCount}개중 ${completedCount}개 달성",
                style = BodyRegular.merge(Neutral400)
            )
            Spacer(modifier = Modifier.width(Space20))
        }
        Spacer(modifier = Modifier.height(Space10))
    }
}

@Preview
@Composable
private fun MyPageStatisticsScreenPreview() {
    MyPageStatisticsScreen(
        navController = rememberNavController(),
        argument = MyPageStatisticsArgument(
            state = MyPageStatisticsState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = MyPageStatisticsData(
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
