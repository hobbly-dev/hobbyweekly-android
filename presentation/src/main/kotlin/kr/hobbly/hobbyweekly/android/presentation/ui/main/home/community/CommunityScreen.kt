package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
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
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodySemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral030
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral300
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral500
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Radius12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space32
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space6
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space60
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.isEmpty
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.makeRoute
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.util.toDurationString
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.ProfileImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.textfield.SearchTextField
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.HomeArgument
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block.BlockConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.myblock.MyBlockConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.popularblock.PopularBlockConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.PostConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.search.CommunitySearchConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.notification.NotificationConstant

@Composable
fun CommunityScreen(
    navController: NavController,
    parentArgument: HomeArgument
) {
    val viewModel: CommunityViewModel = hiltViewModel()

    val argument: CommunityArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        CommunityArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            handler = viewModel.handler
        )
    }

    val data: CommunityData = Unit.let {
        val myBlockList by viewModel.myBlockList.collectAsStateWithLifecycle()
        val popularBlockList by viewModel.popularBlockList.collectAsStateWithLifecycle()
        val popularPostPaging = viewModel.popularPostPaging.collectAsLazyPagingItems()

        CommunityData(
            myBlockList = myBlockList,
            popularBlockList = popularBlockList,
            popularPostPaging = popularPostPaging
        )
    }

    ErrorObserver(viewModel)
    CommunityScreen(
        navController = navController,
        argument = argument,
        data = data
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommunityScreen(
    navController: NavController,
    argument: CommunityArgument,
    data: CommunityData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val refreshState = rememberPullToRefreshState()

    fun navigateToNotification() {
        navController.safeNavigate(NotificationConstant.ROUTE)
    }

    fun navigateToCommunitySearch() {
        navController.safeNavigate(CommunitySearchConstant.ROUTE)
    }

    fun navigateToMyBlock() {
        navController.safeNavigate(MyBlockConstant.ROUTE)
    }

    fun navigateToPopularBlock() {
        navController.safeNavigate(PopularBlockConstant.ROUTE)
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

    fun navigateToPost(
        post: Post
    ) {
        val route = makeRoute(
            PostConstant.ROUTE,
            listOf(
                PostConstant.ROUTE_ARGUMENT_BLOCK_ID to post.board.blockId,
                PostConstant.ROUTE_ARGUMENT_BOARD_ID to post.board.id,
                PostConstant.ROUTE_ARGUMENT_POST_ID to post.id
            )
        )
        navController.safeNavigate(route)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        val (topBar, contents) = createRefs()
        Box(
            modifier = Modifier
                .nestedScroll(refreshState.nestedScrollConnection)
                .constrainAs(contents) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(Space24))
                SearchTextField(
                    text = "",
                    modifier = Modifier.padding(horizontal = Space20),
                    hintText = "관심있는 취미를 입력하세요",
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
                        navigateToCommunitySearch()
                    }
                )
                Spacer(modifier = Modifier.height(Space40))
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
                            CommunityScreenMyBlockItem(
                                block = block,
                                onClick = {
                                    logEvent(
                                        "clk_comm_blk",
                                        mapOf("clk_comm_blk_id" to it.id)
                                    )
                                    navigateToBlock(it)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Space32))
                Row(
                    modifier = Modifier
                        .padding(horizontal = Space20)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "인기블록",
                        style = TitleSemiBoldSmall.merge(Neutral900)
                    )
                    Spacer(modifier = Modifier.width(Space6))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_hot),
                        contentDescription = null,
                        tint = Red
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (data.popularBlockList.isNotEmpty()) {
                        RippleBox(
                            onClick = {
                                navigateToPopularBlock()
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
                if (data.popularBlockList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = Space20)
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Text(
                            text = "인기블록이 없습니다",
                            modifier = Modifier.align(Alignment.Center),
                            style = LabelRegular.merge(Neutral400)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(Space12))
                    Column(
                        modifier = Modifier.padding(horizontal = Space20),
                        verticalArrangement = Arrangement.spacedBy(Space16)
                    ) {
                        data.popularBlockList.forEach { block ->
                            CommunityScreenPopularBlockItem(
                                block = block,
                                onClick = {
                                    logEvent(
                                        "clk_comm_blk",
                                        mapOf("clk_comm_blk_id" to it.id)
                                    )
                                    navigateToBlock(it)
                                }
                            )
                        }
                    }
                }
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
                            CommunityScreenPopularPostItem(
                                post = post,
                                onClick = {
                                    navigateToPost(it)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Space32))
            }
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = refreshState,
                indicator = { pullRefreshState ->
                    PullToRefreshDefaults.Indicator(
                        state = pullRefreshState,
                        modifier = Modifier.background(Neutral030)
                    )
                }
            )
        }
        Box(
            modifier = Modifier
                .height(Space56)
                .background(White)
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            Text(
                text = "취미블록 커뮤니티",
                modifier = Modifier.align(Alignment.Center),
                style = TitleSemiBoldSmall.merge(Neutral900)
            )
            RippleBox(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = Space20),
                onClick = {
                    navigateToNotification()
                }
            ) {
                Icon(
                    modifier = Modifier.size(Space24),
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = null,
                    tint = Neutral900
                )
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }

    LaunchedEffectWithLifecycle(Unit, handler) {
        intent(CommunityIntent.Refresh)
    }

    LaunchedEffectWithLifecycle(refreshState.isRefreshing, handler) {
        if (refreshState.isRefreshing) {
            intent(CommunityIntent.Refresh)
        }
    }

    LaunchedEffectWithLifecycle(state, handler) {
        if (state != CommunityState.Loading) {
            refreshState.endRefresh()
        }
    }
}

@Composable
private fun CommunityScreenMyBlockItem(
    block: Block,
    onClick: (Block) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Radius12))
            .background(White)
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

@Composable
private fun CommunityScreenPopularBlockItem(
    block: Block,
    onClick: (Block) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Radius12))
            .background(Neutral050)
    ) {
        Column(
            modifier = Modifier.clickable {
                onClick(block)
            }
        ) {
            Spacer(modifier = Modifier.height(Space10))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(Space10))
                PostImage(
                    data = block.thumbnail,
                    modifier = Modifier.size(Space60)
                )
                Spacer(modifier = Modifier.width(Space12))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = block.name,
                        style = LabelMedium.merge(Neutral900)
                    )
                    Spacer(modifier = Modifier.height(Space10))
                    Text(
                        text = block.content,
                        style = LabelRegular.merge(Neutral500)
                    )
                }
                Spacer(modifier = Modifier.width(Space10))
            }
            Spacer(modifier = Modifier.height(Space10))
        }
    }
}

@Composable
private fun CommunityScreenPopularPostItem(
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
            Spacer(modifier = Modifier.height(Space8))
            Text(
                text = post.board.name,
                modifier = Modifier.padding(horizontal = Space20),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = BodyRegular.merge(Neutral900)
            )
            Spacer(modifier = Modifier.height(Space12))
        }
    }
}

@Preview
@Composable
private fun CommunityScreenPreview() {
    CommunityScreen(
        navController = rememberNavController(),
        argument = CommunityArgument(
            state = CommunityState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = CommunityData(
            myBlockList = listOf(
                Block(
                    id = 1,
                    name = "영어 블록",
                    content = "영어를 공부하고 인증하는 모임",
                    image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    memberCount = 100
                ),
                Block(
                    id = 2,
                    name = "요리 블록",
                    content = "취미로 요리를 하는 사람들의 모임",
                    image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    memberCount = 100
                ),
                Block(
                    id = 3,
                    name = "여행 블록",
                    content = "여행을 취미로 하는 사람들의 모임",
                    image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    memberCount = 100
                ),
                Block(
                    id = 4,
                    name = "공부 블록",
                    content = "공부를 취미로 하는 사람들의 모임",
                    image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    memberCount = 100
                ),
                Block(
                    id = 5,
                    name = "코딩 블록",
                    content = "코딩을 취미로 하는 사람들의 모임",
                    image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    memberCount = 100
                ),
            ),
            popularBlockList = listOf(
                Block(
                    id = 4,
                    name = "공부 블록",
                    content = "공부를 취미로 하는 사람들의 모임",
                    image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    memberCount = 100
                ),
                Block(
                    id = 5,
                    name = "코딩 블록",
                    content = "코딩을 취미로 하는 사람들의 모임",
                    image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    memberCount = 100
                )
            ),
            popularPostPaging = MutableStateFlow<PagingData<Post>>(
                PagingData.from(
                    listOf(
                        Post(
                            id = 1,
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
            ).collectAsLazyPagingItems(),
        )
    )
}
