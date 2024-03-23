package kr.hobbly.hobbyweekly.android.data.repository.feature.community

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardComment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Member
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.CommunityRepository

class MockCommunityRepository @Inject constructor() : CommunityRepository {
    override suspend fun getBlock(
        id: Long
    ): Result<Block> {
        randomShortDelay()

        return Result.success(
            Block(
                id = 1,
                name = "영어 블록",
                content = "영어를 공부하고 인증하는 모임",
                image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                memberCount = 100
            )
        )
    }

    override suspend fun getPopularBlockList(): Result<List<Block>> {
        randomShortDelay()

        return Result.success(
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
                ),
            )
        )
    }

    override suspend fun getRecommendBlockList(): Result<List<Block>> {
        randomShortDelay()

        return Result.success(
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
                ),
            )
        )
    }

    override suspend fun searchBlockPaging(
        keyword: String
    ): Flow<PagingData<Block>> {
        randomShortDelay()

        return flowOf(
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
                    ),
                )
            )
        )
    }

    override suspend fun getBoardList(
        id: Long
    ): Result<List<Board>> {
        randomShortDelay()

        return Result.success(
            listOf(
                Board(
                    id = 1,
                    blockId = 1,
                    title = "자유게시판",
                    hasNewPost = true
                ),
                Board(
                    id = 2,
                    blockId = 1,
                    title = "인증게시판",
                    hasNewPost = true
                ),
                Board(
                    id = 3,
                    blockId = 1,
                    title = "기타게시판",
                    hasNewPost = false
                )
            )
        )
    }

    override suspend fun getMyBlockList(): Result<List<Block>> {
        randomShortDelay()

        return Result.success(
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
                ),
            )
        )
    }

    override suspend fun addMyBlock(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun removeMyBlock(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun writeBoardPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long> {
        randomShortDelay()

        return Result.success(0L)
    }

    override suspend fun writeRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Long> {
        randomShortDelay()

        return Result.success(0L)
    }

    override suspend fun writeNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        imageList: List<String>
    ): Result<Long> {
        randomShortDelay()

        return Result.success(0L)
    }

    override suspend fun searchBoardPostPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<BoardPost>> {
        randomShortDelay()

        return flowOf(
            PagingData.from(
                listOf(
                    BoardPost(
                        id = 1,
                        member = Member(
                            id = 1,
                            nickname = "히카루",
                            image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                        ),
                        blockId = 1,
                        boardId = 1,
                        title = "영어 인증합니다",
                        content = "영어 공부 인증 올립니다 오늘 영어공부를 하면서 배운 내용입니다.",
                        createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .atTime(0, 0, 0),
                        updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .atTime(0, 0, 0),
                        images = listOf(
                            "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                            "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                            "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                            "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp"
                        ),
                        commentCount = 99,
                        likeCount = 99,
                        isAnonymous = false,
                        isSecret = false
                    ),
                    BoardPost(
                        id = 2,
                        member = Member(
                            id = 1,
                            nickname = "박상준",
                            image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                        ),
                        blockId = 1,
                        boardId = 1,
                        title = "개발 인증합니다",
                        content = "개발 했습니다. 오늘 개발을 하면서 배운 내용입니다.",
                        createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .minus(1, DateTimeUnit.DAY)
                            .atTime(0, 0, 0),
                        updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .minus(1, DateTimeUnit.DAY)
                            .atTime(0, 0, 0),
                        images = listOf(
                            "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                        ),
                        commentCount = 1,
                        likeCount = 1,
                        isAnonymous = false,
                        isSecret = false
                    ),
                    BoardPost(
                        id = 3,
                        member = Member(
                            id = 1,
                            nickname = "장성혁",
                            image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                        ),
                        blockId = 1,
                        boardId = 1,
                        title = "휴식 인증합니다",
                        content = "휴식 했습니다.",
                        createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .minus(7, DateTimeUnit.DAY)
                            .atTime(0, 0, 0),
                        updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .minus(7, DateTimeUnit.DAY)
                            .atTime(0, 0, 0),
                        images = listOf(),
                        commentCount = 0,
                        likeCount = 0,
                        isAnonymous = false,
                        isSecret = false
                    )
                )
            )
        )
    }

    override suspend fun loadBoardPost(
        id: Long
    ): Result<BoardPost> {
        randomShortDelay()

        return Result.success(
            BoardPost(
                id = 1,
                member = Member(
                    id = 1,
                    nickname = "히카루",
                    image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                ),
                blockId = 1,
                boardId = 1,
                title = "영어 인증합니다",
                content = "영어 공부 인증 올립니다 오늘 영어공부를 하면서 배운 내용입니다.",
                createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                    .atTime(0, 0, 0),
                updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                    .atTime(0, 0, 0),
                images = listOf(
                    "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                    "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp"
                ),
                commentCount = 99,
                likeCount = 99,
                isAnonymous = false,
                isSecret = false
            )
        )
    }

    override suspend fun editBoardPost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun editRoutinePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        isSecret: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun editNoticePost(
        id: Long,
        title: String,
        content: String,
        isAnonymous: Boolean,
        imageList: List<String>
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun removeBoardPost(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun removeRoutinePost(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun removeNoticePost(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun likeBoardPost(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun likeRoutinePost(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun likeNoticePost(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun reportBoardPost(
        id: Long,
        reason: String
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun reportRoutinePost(
        id: Long,
        reason: String
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun reportNoticePost(
        id: Long,
        reason: String
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun loadBoardCommentPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<BoardComment>> {
        randomShortDelay()

        return flowOf(
            PagingData.from(
                listOf(
                    BoardComment(
                        id = 1L,
                        blockId = 1L,
                        boardId = 1L,
                        postId = 1L,
                        content = "좋은 글이네요!",
                        createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .atTime(0, 0, 0),
                        updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .atTime(0, 0, 0),
                        member = Member(
                            id = 1L,
                            nickname = "히카루",
                            image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                        ),
                        likeCount = 99,
                        child = listOf(),
                        isAnonymous = false
                    ),
                    BoardComment(
                        id = 2L,
                        blockId = 1L,
                        boardId = 1L,
                        postId = 1L,
                        content = "좋은 글이네요!",
                        createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .atTime(0, 0, 0),
                        updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                            .atTime(0, 0, 0),
                        member = Member(
                            id = 1L,
                            nickname = "히카루",
                            image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                        ),
                        likeCount = 99,
                        child = listOf(
                            BoardComment(
                                id = 3L,
                                blockId = 1L,
                                boardId = 1L,
                                postId = 1L,
                                content = "좋은 글이네요!",
                                createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                    .atTime(0, 0, 0),
                                updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                    .atTime(0, 0, 0),
                                member = Member(
                                    id = 1L,
                                    nickname = "히카루",
                                    image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                                ),
                                likeCount = 99,
                                child = listOf(),
                                isAnonymous = false
                            ),
                            BoardComment(
                                id = 4L,
                                blockId = 1L,
                                boardId = 1L,
                                postId = 1L,
                                content = "좋은 글이네요!",
                                createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                    .atTime(0, 0, 0),
                                updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                                    .atTime(0, 0, 0),
                                member = Member(
                                    id = 1L,
                                    nickname = "히카루",
                                    image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                                ),
                                likeCount = 99,
                                child = listOf(),
                                isAnonymous = false
                            )
                        ),
                        isAnonymous = false
                    )
                )
            )
        )
    }

    override suspend fun writeBoardComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun writeRoutineComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun writeNoticeComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun removeComment(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun reportComment(
        id: Long,
        reason: String
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun writeCommentReply(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun likeComment(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
