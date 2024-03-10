package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.PostMember
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodyRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral030
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral500
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Radius12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space32
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space4
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space60
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.makeRoute
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.ProfileImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.textfield.SearchTextField
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.PostConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit.PostEditConstant

@Composable
fun BoardScreen(
    navController: NavController,
    argument: BoardArgument,
    data: BoardData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val focusRequester = remember { FocusRequester() }

    var keyword: String by remember { mutableStateOf("") }

    fun navigateToBoardSearch() {
        // TODO
    }

    fun navigateToPost(
        post: Post
    ) {
        val route = makeRoute(
            PostConstant.ROUTE,
            listOf(
                PostConstant.ROUTE_ARGUMENT_BLOCK_ID to post.blockId,
                PostConstant.ROUTE_ARGUMENT_BOARD_ID to post.boardId,
                PostConstant.ROUTE_ARGUMENT_POST_ID to post.id
            )
        )
        navController.safeNavigate(route)
    }

    fun navigateToPostAdd(
        board: Board
    ) {
        val route = makeRoute(
            PostEditConstant.ROUTE,
            listOf(
                PostEditConstant.ROUTE_ARGUMENT_BLOCK_ID to board.blockId,
                PostEditConstant.ROUTE_ARGUMENT_BOARD_ID to board.id
            )
        )
        navController.safeNavigate(route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
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
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Neutral030)
                    .align(Alignment.TopStart)
            ) {
                item {
                    Spacer(modifier = Modifier.height(Space24))
                    SearchTextField(
                        text = "",
                        modifier = Modifier.padding(horizontal = Space20),
                        hintText = "글, 제목, 내용",
                        onValueChange = {},
                        leadingIconContent = {
                            Icon(
                                modifier = Modifier.size(Space16),
                                painter = painterResource(R.drawable.ic_search),
                                contentDescription = null,
                                tint = Neutral900
                            )
                        },
                        onClick = {
                            navigateToBoardSearch()
                        }
                    )
                    Spacer(modifier = Modifier.height(Space24))
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Neutral050
                    )
                }
                items(data.postList) { post ->
                    BoardScreenPostItem(
                        post = post,
                        onClick = {
                            navigateToPost(post)
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(Space16)
                    .align(Alignment.BottomEnd)
            ) {
                FloatingActionButton(
                    modifier = Modifier.size(Space56),
                    shape = CircleShape,
                    containerColor = Red,
                    onClick = {
                        navigateToPostAdd(data.board)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(Space32),
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                        tint = White
                    )
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
private fun BoardScreenPostItem(
    post: Post,
    onClick: (Post) -> Unit
) {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val duration =
        now.toInstant(TimeZone.currentSystemDefault()) - (post.createdAt.toInstant(TimeZone.currentSystemDefault()))

    val formattedDate = when {
        duration.inWholeMinutes < 1 -> {
            "${duration.inWholeSeconds}초 전"
        }

        duration.inWholeHours < 1 -> {
            "${duration.inWholeMinutes}분 전"
        }

        duration.inWholeDays < 1 -> {
            "${duration.inWholeHours}시간 전"
        }

        duration.inWholeDays < 8 -> {
            "${duration.inWholeDays}일 전"
        }

        post.createdAt.date.year == now.year -> {
            val format = "%02d월 %02d일"
            runCatching {
                String.format(
                    format,
                    post.createdAt.date.month.number,
                    post.createdAt.date.dayOfMonth
                )
            }.getOrDefault("??월 ??일")
        }

        else -> {
            val format = "%02d년 %02d월 %02d일"
            runCatching {
                String.format(
                    format,
                    post.createdAt.date.year % 100,
                    post.createdAt.date.year,
                    post.createdAt.date.month.number
                )
            }.getOrDefault("??년 ??월 ??일")
        }
    }

    Column {
        Column(
            modifier = Modifier.clickable {
                onClick(post)
            }
        ) {
            Spacer(modifier = Modifier.height(Space12))
            Row(
                modifier = Modifier.padding(horizontal = Space20),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(
                    data = post.member.thumbnail,
                    modifier = Modifier.size(Space24)
                )
                Spacer(modifier = Modifier.width(Space8))
                Text(
                    text = post.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = LabelMedium.merge(Neutral900)
                )
                Spacer(modifier = Modifier.width(Space8))
                Text(
                    text = formattedDate,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = LabelMedium.merge(Neutral400)
                )
            }
            Spacer(modifier = Modifier.height(Space8))
            Row(
                modifier = Modifier.padding(horizontal = Space20)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = post.description,
                        modifier = Modifier.padding(end = Space20),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = BodyRegular.merge(Neutral500)
                    )
                    Spacer(modifier = Modifier.height(Space20))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(Space12),
                            painter = painterResource(R.drawable.ic_talk),
                            contentDescription = null,
                            tint = Neutral900
                        )
                        Spacer(modifier = Modifier.width(Space4))
                        Text(
                            text = if (post.commentCount > 99) "99+" else post.commentCount.toString(),
                            style = BodyRegular.merge(Neutral900)
                        )
                        Spacer(modifier = Modifier.width(Space4))
                        Icon(
                            modifier = Modifier.size(Space12),
                            painter = painterResource(R.drawable.ic_like),
                            contentDescription = null,
                            tint = if (post.isLike) Red else Neutral900
                        )
                        Spacer(modifier = Modifier.width(Space4))
                        Text(
                            text = if (post.likeCount > 99) "99+" else post.likeCount.toString(),
                            style = BodyRegular.merge(Neutral900)
                        )
                        Spacer(modifier = Modifier.width(Space4))
                        Icon(
                            modifier = Modifier.size(Space12),
                            painter = painterResource(R.drawable.ic_bookmark),
                            contentDescription = null,
                            tint = if (post.isScrap) Red else Neutral900
                        )
                        Spacer(modifier = Modifier.width(Space4))
                        Text(
                            text = if (post.scrapCount > 99) "99+" else post.scrapCount.toString(),
                            style = BodyRegular.merge(Neutral900)
                        )
                        Spacer(modifier = Modifier.width(Space4))
                    }
                }
                if (post.images.size > 1) {
                    Box(
                        modifier = Modifier
                            .size(Space60)
                            .clip(RoundedCornerShape(Radius12))
                            .background(Neutral200),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+${post.images.size}",
                            style = BodyRegular.merge(Neutral500)
                        )
                    }
                } else if (post.images.size == 1) {
                    PostImage(
                        data = post.images.firstOrNull(),
                        modifier = Modifier.size(Space60)
                    )
                }
            }
            Spacer(modifier = Modifier.height(Space12))
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Neutral050
        )
    }
}

@Preview
@Composable
private fun BoardScreenPreview() {
    BoardScreen(
        navController = rememberNavController(),
        argument = BoardArgument(
            state = BoardState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = BoardData(
            board = Board(
                id = 1,
                blockId = 1,
                name = "자유게시판",
                hasNewPost = true
            ),
            postList = listOf(
                Post(
                    id = 1,
                    member = PostMember(
                        id = 1,
                        name = "히카루",
                        thumbnail = "https://avatars.githubusercontent.com/u/48707913?v=4"
                    ),
                    blockId = 1,
                    boardId = 1,
                    title = "영어 인증합니다",
                    description = "영어 공부 인증 올립니다 오늘 영어공부를 하면서 배운 내용입니다.",
                    images = listOf(
                        "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                        "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                        "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                        "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp"
                    ),
                    commentCount = 99,
                    likeCount = 99,
                    scrapCount = 99,
                    isLike = false,
                    isScrap = false,
                    createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                        .atTime(0, 0, 0)
                ),
                Post(
                    id = 2,
                    member = PostMember(
                        id = 1,
                        name = "박상준",
                        thumbnail = "https://avatars.githubusercontent.com/u/48707913?v=4"
                    ),
                    blockId = 1,
                    boardId = 1,
                    title = "개발 인증합니다",
                    description = "개발 했습니다. 오늘 개발을 하면서 배운 내용입니다.",
                    images = listOf(
                        "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    ),
                    commentCount = 1,
                    likeCount = 1,
                    scrapCount = 1,
                    isLike = false,
                    isScrap = false,
                    createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                        .minus(1, DateTimeUnit.DAY)
                        .atTime(0, 0, 0)
                ),
                Post(
                    id = 3,
                    member = PostMember(
                        id = 1,
                        name = "장성혁",
                        thumbnail = "https://avatars.githubusercontent.com/u/48707913?v=4"
                    ),
                    blockId = 1,
                    boardId = 1,
                    title = "휴식 인증합니다",
                    description = "휴식 했습니다.",
                    images = listOf(),
                    commentCount = 0,
                    likeCount = 0,
                    scrapCount = 0,
                    isLike = false,
                    isScrap = false,
                    createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                        .minus(1, DateTimeUnit.WEEK)
                        .atTime(0, 0, 0)
                )
            )
        )
    )
}
