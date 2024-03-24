package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral500
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Radius12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space60
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldXSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.isEmpty
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.makeRoute
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.textfield.SearchTextField
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.block.BlockConstant

@Composable
fun CommunitySearchScreen(
    navController: NavController,
    argument: CommunitySearchArgument,
    data: CommunitySearchData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val focusRequester = remember { FocusRequester() }

    var keyword: String by remember { mutableStateOf("") }

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
        Spacer(modifier = Modifier.height(Space24))
        SearchTextField(
            text = keyword,
            modifier = Modifier
                .padding(horizontal = Space20)
                .focusRequester(focusRequester),
            hintText = "관심있는 취미를 입력하세요",
            onValueChange = { keyword = it },
            keyboardActions = KeyboardActions(
                onSearch = {
                    intent(CommunitySearchIntent.Search(keyword))
                }
            ),
            leadingIconContent = {
                Icon(
                    modifier = Modifier.size(Space16),
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    tint = Neutral900
                )
            },
        )
        Spacer(modifier = Modifier.height(Space40))
        if (state == CommunitySearchState.SearchResult) {
            if (data.searchBlockList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = Space20)
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Text(
                        text = "검색 결과가 없습니다",
                        modifier = Modifier.align(Alignment.Center),
                        style = LabelRegular.merge(Neutral400)
                    )
                }
            } else {
//                Text(
//                    text = "검색결과 ${data.searchBlockList.size}개",
//                    modifier = Modifier.padding(horizontal = Space20),
//                    style = TitleSemiBoldXSmall.merge(Neutral900)
//                )
//                Spacer(modifier = Modifier.height(Space20))
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = Space20)
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Space16)
                ) {
                    items(
                        count = data.searchBlockList.itemCount,
                        key = { data.searchBlockList[it]?.id ?: -1 }
                    ) { index ->
                        val block = data.searchBlockList[index] ?: return@items
                        CommunitySearchScreenBlockItem(
                            block = block,
                            onClick = {
                                navigateToBlock(it)
                            }
                        )
                    }
                }
            }
        } else {
            if (data.suggestBlockList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = Space20)
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Text(
                        text = "추천 블록이 없습니다",
                        modifier = Modifier.align(Alignment.Center),
                        style = LabelRegular.merge(Neutral400)
                    )
                }
            } else {
                Text(
                    text = "이런 취미 블록은 어때요?",
                    modifier = Modifier.padding(horizontal = Space20),
                    style = TitleSemiBoldXSmall.merge(Neutral900)
                )
                Spacer(modifier = Modifier.height(Space20))
                LazyColumn(
                    modifier = Modifier.padding(horizontal = Space20),
                    verticalArrangement = Arrangement.spacedBy(Space16)
                ) {
                    items(
                        items = data.suggestBlockList,
                        key = { it.id }
                    ) { block ->
                        CommunitySearchScreenBlockItem(
                            block = block,
                            onClick = {
                                navigateToBlock(it)
                            }
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
private fun CommunitySearchScreenBlockItem(
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

@Preview
@Composable
private fun CommunitySearchScreenPreview() {
    CommunitySearchScreen(
        navController = rememberNavController(),
        argument = CommunitySearchArgument(
            state = CommunitySearchState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = CommunitySearchData(
            suggestBlockList = listOf(
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
                )
            ),
            searchBlockList = MutableStateFlow(
                PagingData.from(
                    listOf(
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
                        )
                    )
                )
            ).collectAsLazyPagingItems()
        )
    )
}
