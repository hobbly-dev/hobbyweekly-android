package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.mypage.Notification
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodyRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral030
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.util.toDurationString
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox

@Composable
fun NotificationScreen(
    navController: NavController,
    argument: NotificationArgument,
    data: NotificationData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral030)
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
        }
        Spacer(modifier = Modifier.height(Space20))
        Text(
            text = "알림",
            modifier = Modifier.padding(horizontal = Space20),
            style = TitleRegular.merge(Neutral900)
        )
        Spacer(modifier = Modifier.height(Space20))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(
                count = data.notificationPaging.itemCount,
                key = { data.notificationPaging[it]?.id ?: -1 }
            ) { index ->
                val notification = data.notificationPaging[index] ?: return@items
                NotificationScreenItem(
                    notification = notification,
                    onClick = {
                        // TODO : MVP 이후 작업
                    }
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
private fun NotificationScreenItem(
    notification: Notification,
    onClick: (Notification) -> Unit
) {
    val formattedTime = notification.createdAt.toDurationString()

    Column(
        modifier = Modifier.clickable {
            onClick(notification)
        }
    ) {
        Spacer(modifier = Modifier.height(Space10))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(Space20))
            Box(
                modifier = Modifier
                    .size(Space40)
                    .background(
                        color = Red,
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
            Text(
                text = notification.content,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                style = BodyRegular.merge(Neutral900)
            )
            Spacer(modifier = Modifier.width(Space20))
            Text(
                text = formattedTime,
                style = BodyRegular.merge(Neutral900)
            )
            Spacer(modifier = Modifier.width(Space20))
        }
        Spacer(modifier = Modifier.height(Space10))
    }
}

@Preview
@Composable
private fun NotificationScreenPreview() {
    NotificationScreen(
        navController = rememberNavController(),
        argument = NotificationArgument(
            state = NotificationState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = NotificationData(
            notificationPaging = MutableStateFlow<PagingData<Notification>>(
                PagingData.from(
                    listOf(
                        Notification(
                            id = 1,
                            content = "종국님이 좋아요를 눌렀습니다",
                            isRead = false,
                            createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .atTime(0, 0, 0),
                        ),
                        Notification(
                            id = 2,
                            content = "새로운 답글이 달렸어요: 쓴소리 고맙다",
                            isRead = false,
                            createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(1, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                        ),
                        Notification(
                            id = 3,
                            content = "새로운 댓글이 달렸어요 : 홧팅이여!! ❤",
                            isRead = false,
                            createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(7, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                        )
                    )
                )
            ).collectAsLazyPagingItems(),
        )
    )
}
