package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlin.math.max
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
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardType
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Member
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodyRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral300
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral500
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral600
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Radius12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space32
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space6
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space60
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Warning
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.isEmpty
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.makeRoute
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.util.toDurationString
import kr.hobbly.hobbyweekly.android.presentation.common.view.DialogScreen
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButton
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonType
import kr.hobbly.hobbyweekly.android.presentation.common.view.dropdown.TextDropdownMenu
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.ProfileImage
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.board.BoardConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.PostConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit.RoutineEditConstant

@Composable
fun BlockScreen(
    navController: NavController,
    argument: BlockArgument,
    data: BlockData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val context = LocalContext.current

    var isMenuShowing by remember { mutableStateOf(false) }
    var isRemoveMyBlockSuccessDialogShowing by remember { mutableStateOf(false) }
    val menu: List<String> = mutableListOf<String>().apply {
        if (data.isMyBlock) {
            add("삭제하기")
        }
    }

    fun navigateToBoard(
        block: Block,
        board: Board
    ) {
        val route = makeRoute(
            BoardConstant.ROUTE,
            listOf(
                BoardConstant.ROUTE_ARGUMENT_BLOCK_ID to block.id,
                BoardConstant.ROUTE_ARGUMENT_BOARD_ID to board.id
            )
        )
        navController.safeNavigate(route)
    }

    fun navigateToPost(
        post: Post
    ) {
        val route = makeRoute(
            PostConstant.ROUTE,
            listOf(
                PostConstant.ROUTE_ARGUMENT_BLOCK_ID to post.blockId,
                PostConstant.ROUTE_ARGUMENT_BOARD_ID to post.board.id,
                PostConstant.ROUTE_ARGUMENT_POST_ID to post.id
            )
        )
        navController.safeNavigate(route)
    }

    fun navigateToRoutineEdit() {
        val route = makeRoute(
            RoutineEditConstant.ROUTE,
            listOf(
                BlockConstant.ROUTE_ARGUMENT_BLOCK_ID to data.block.id
            )
        )
        navController.safeNavigate(route)
    }

    if (isRemoveMyBlockSuccessDialogShowing) {
        DialogScreen(
            title = "내 블록 삭제",
            message = "내 블록에서 삭제되었습니다.",
            isCancelable = false,
            onDismissRequest = {
                isRemoveMyBlockSuccessDialogShowing = false
            },
            onConfirm = {
                navController.safeNavigateUp()
            }
        )
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
            if (data.isMyBlock) {
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
                        items = menu,
                        isExpanded = isMenuShowing,
                        onDismissRequest = { isMenuShowing = false },
                        onClick = { text ->
                            if (text == "삭제하기") {
                                intent(BlockIntent.OnRemove)
                            }
                        }
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopStart)
                    .verticalScroll(rememberScrollState())
            ) {
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
                    modifier = Modifier
                        .padding(horizontal = Space20)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "공지사항",
                        style = TitleSemiBoldSmall.merge(Neutral900)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (!data.noticePostPaging.isEmpty()) {
                        RippleBox(
                            onClick = {
                                data.boardList.find { it.type == BoardType.Notice }
                                    ?.let { noticeBoard ->
                                        navigateToBoard(
                                            block = data.block,
                                            board = noticeBoard
                                        )
                                    }
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
                if (data.noticePostPaging.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = Space20)
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Text(
                            text = "등록된 공지가 없습니다",
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
                            count = data.noticePostPaging.itemCount,
                            key = { index -> data.noticePostPaging[index]?.id ?: -1 }
                        ) { index ->
                            val post = data.noticePostPaging[index] ?: return@items
                            BlockScreenPostItem(
                                post = post,
                                onClick = {
                                    navigateToPost(it)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Space32))
                if (data.boardList.isNotEmpty()) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Neutral200
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        data.boardList.forEach { board ->
                            BlockScreenBoardItem(
                                board = board,
                                onClick = {
                                    navigateToBoard(
                                        block = data.block,
                                        board = it
                                    )
                                }
                            )
                        }
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Neutral200
                )
                Spacer(modifier = Modifier.height(Space32))
                Row(
                    modifier = Modifier
                        .padding(horizontal = Space20)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "핫한 인기글",
                        style = TitleSemiBoldSmall.merge(Neutral900)
                    )
                    Spacer(modifier = Modifier.width(Space6))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_hot),
                        contentDescription = null,
                        tint = Red
                    )
                }
                if (data.popularPostPaging.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = Space20)
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Text(
                            text = "핫한 인기글이 없습니다",
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
                            count = data.popularPostPaging.itemCount,
                            key = { index -> data.popularPostPaging[index]?.id ?: -1 }
                        ) { index ->
                            val post = data.popularPostPaging[index] ?: return@items
                            BlockScreenPostItem(
                                post = post,
                                onClick = {
                                    navigateToPost(it)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Space12))
            }
            if (!data.isMyBlock) {
                ConfirmButton(
                    modifier = Modifier
                        .padding(start = Space20, end = Space20, bottom = Space12)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Large,
                        type = ConfirmButtonType.Primary
                    ),
                    onClick = {
                        navigateToRoutineEdit()
                    }
                ) { style ->
                    Text(
                        text = "블록 추가하기",
                        style = style
                    )
                }
            }
        }
    }

    fun removeBlock(event: BlockEvent.RemoveBlock) {
        when (event) {
            BlockEvent.RemoveBlock.Success -> {
                isRemoveMyBlockSuccessDialogShowing = true
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is BlockEvent.RemoveBlock -> {
                    removeBlock(event)
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(Unit, handler) {
        intent(BlockIntent.Refresh)
    }
}

@Composable
fun BlockScreenBoardItem(
    board: Board,
    onClick: (Board) -> Unit
) {
    Box(
        modifier = Modifier.clickable {
            onClick(board)
        }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = Space20, vertical = Space12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(Space16),
                painter = painterResource(R.drawable.ic_talk),
                contentDescription = null,
                tint = Neutral600
            )
            Spacer(modifier = Modifier.width(Space12))
            Text(
                text = board.name,
                style = LabelMedium.merge(Neutral900)
            )
            if (board.hasNewPost) {
                Spacer(modifier = Modifier.width(Space12))
                Image(
                    modifier = Modifier.size(Space12),
                    painter = painterResource(R.drawable.ic_new),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun BlockScreenPostItem(
    post: Post,
    onClick: (Post) -> Unit
) {
    val maxImageCount = 2

    val leftImageCount = max(0, post.imageList.size - maxImageCount)

    val formattedDate = post.createdAt.toDurationString()

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Radius12))
            .background(Neutral050)
            .widthIn(max = 250.dp)
    ) {
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
                    data = post.member.image,
                    modifier = Modifier.size(Space24)
                )
                Spacer(modifier = Modifier.width(Space8))
                Text(
                    text = post.member.nickname,
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
            Text(
                text = post.title,
                modifier = Modifier.padding(horizontal = Space20),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = BodyRegular.merge(Neutral500)
            )
            Spacer(modifier = Modifier.height(Space8))
            Row(
                modifier = Modifier.padding(horizontal = Space20),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Space6)
            ) {
                post.imageList.take(maxImageCount).forEach { image ->
                    PostImage(
                        data = image,
                        modifier = Modifier.size(Space60)
                    )
                }
                if (leftImageCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(Space60)
                            .clip(RoundedCornerShape(Radius12))
                            .background(Neutral200),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+$leftImageCount",
                            style = BodyRegular.merge(Neutral500)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(Space12))
        }
    }
}

@Preview
@Composable
private fun BlockScreenPreview() {
    BlockScreen(
        navController = rememberNavController(),
        argument = BlockArgument(
            state = BlockState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = BlockData(
            block = Block(
                id = 1,
                name = "영어 블록",
                content = "영어를 공부하고 인증하는 모임",
                image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                memberCount = 100
            ),
            isMyBlock = false,
            boardList = listOf(
                Board(
                    id = 1,
                    blockId = 1,
                    type = BoardType.Notice,
                    name = "공지사항",
                    hasNewPost = true
                ),
                Board(
                    id = 2,
                    blockId = 1,
                    type = BoardType.Routine,
                    name = "인증게시판",
                    hasNewPost = true
                ),
                Board(
                    id = 3,
                    blockId = 1,
                    type = BoardType.Regular,
                    name = "기타게시판",
                    hasNewPost = false
                )
            ),
            noticePostPaging = MutableStateFlow<PagingData<Post>>(
                PagingData.from(
                    listOf(
                        Post(
                            id = 1,
                            blockId = 1,
                            title = "영어 인증합니다",
                            content = "영어 공부 인증 올립니다 오늘 영어공부를 하면서 배운 내용입니다.",
                            createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .atTime(0, 0, 0),
                            updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .atTime(0, 0, 0),
                            imageList = listOf(
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp"
                            ),
                            commentCount = 99,
                            likeCount = 99,
                            isAnonymous = false,
                            isSecret = false,
                            member = Member(
                                id = 1,
                                nickname = "히카루",
                                image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                            ),
                            board = Board(
                                id = 1,
                                blockId = 1,
                                type = BoardType.Notice,
                                name = "공지사항",
                                hasNewPost = true
                            )
                        ),
                        Post(
                            id = 2,
                            blockId = 1,
                            title = "개발 인증합니다",
                            content = "개발 했습니다. 오늘 개발을 하면서 배운 내용입니다.",
                            createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(1, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                            updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(1, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                            imageList = listOf(
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                            ),
                            commentCount = 1,
                            likeCount = 1,
                            isAnonymous = false,
                            isSecret = false,
                            member = Member(
                                id = 1,
                                nickname = "박상준",
                                image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                            ),
                            board = Board(
                                id = 1,
                                blockId = 1,
                                type = BoardType.Notice,
                                name = "공지사항",
                                hasNewPost = true
                            )
                        ),
                        Post(
                            id = 3,
                            blockId = 1,
                            title = "휴식 인증합니다",
                            content = "휴식 했습니다.",
                            createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(7, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                            updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(7, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                            imageList = listOf(),
                            commentCount = 0,
                            likeCount = 0,
                            isAnonymous = false,
                            isSecret = false,
                            member = Member(
                                id = 1,
                                nickname = "장성혁",
                                image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                            ),
                            board = Board(
                                id = 1,
                                blockId = 1,
                                type = BoardType.Notice,
                                name = "공지사항",
                                hasNewPost = true
                            )
                        )
                    ),
                )
            ).collectAsLazyPagingItems(),
            popularPostPaging =
            MutableStateFlow<PagingData<Post>>(
                PagingData.from(
                    listOf(
                        Post(
                            id = 1,
                            blockId = 1,
                            title = "영어 인증합니다",
                            content = "영어 공부 인증 올립니다 오늘 영어공부를 하면서 배운 내용입니다.",
                            createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .atTime(0, 0, 0),
                            updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .atTime(0, 0, 0),
                            imageList = listOf(
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp"
                            ),
                            commentCount = 99,
                            likeCount = 99,
                            isAnonymous = false,
                            isSecret = false,
                            member = Member(
                                id = 1,
                                nickname = "히카루",
                                image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                            ),
                            board = Board(
                                id = 1,
                                blockId = 1,
                                type = BoardType.Notice,
                                name = "공지사항",
                                hasNewPost = true
                            )
                        ),
                        Post(
                            id = 2,
                            blockId = 1,
                            title = "개발 인증합니다",
                            content = "개발 했습니다. 오늘 개발을 하면서 배운 내용입니다.",
                            createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(1, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                            updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(1, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                            imageList = listOf(
                                "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                            ),
                            commentCount = 1,
                            likeCount = 1,
                            isAnonymous = false,
                            isSecret = false,
                            member = Member(
                                id = 1,
                                nickname = "박상준",
                                image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                            ),
                            board = Board(
                                id = 1,
                                blockId = 1,
                                type = BoardType.Notice,
                                name = "공지사항",
                                hasNewPost = true
                            )
                        ),
                        Post(
                            id = 3,
                            blockId = 1,
                            title = "휴식 인증합니다",
                            content = "휴식 했습니다.",
                            createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(7, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                            updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                .minus(7, DateTimeUnit.DAY)
                                .atTime(0, 0, 0),
                            imageList = listOf(),
                            commentCount = 0,
                            likeCount = 0,
                            isAnonymous = false,
                            isSecret = false,
                            member = Member(
                                id = 1,
                                nickname = "장성혁",
                                image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                            ),
                            board = Board(
                                id = 1,
                                blockId = 1,
                                type = BoardType.Notice,
                                name = "공지사항",
                                hasNewPost = true
                            )
                        )
                    )
                )
            ).collectAsLazyPagingItems()
        )
    )
}
