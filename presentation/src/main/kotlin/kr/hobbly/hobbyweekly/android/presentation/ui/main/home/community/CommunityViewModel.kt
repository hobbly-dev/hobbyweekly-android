package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Member
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<CommunityState> = MutableStateFlow(CommunityState.Init)
    val state: StateFlow<CommunityState> = _state.asStateFlow()

    private val _event: MutableEventFlow<CommunityEvent> = MutableEventFlow()
    val event: EventFlow<CommunityEvent> = _event.asEventFlow()

    private val _communityData: MutableStateFlow<CommunityData> = MutableStateFlow(
        CommunityData(
            myBlockList = listOf(),
            popularBlockList = listOf(),
            popularPostList = listOf()
        )
    )
    val communityData: StateFlow<CommunityData> = _communityData.asStateFlow()

    init {
        _communityData.value = CommunityData(
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
            popularBlockList = listOf(
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
                )
            ),
            popularPostList = listOf(
                Post(
                    id = 1,
                    member = Member(
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
                    member = Member(
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
                    member = Member(
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
    }

    fun onIntent(intent: CommunityIntent) {

    }
}
