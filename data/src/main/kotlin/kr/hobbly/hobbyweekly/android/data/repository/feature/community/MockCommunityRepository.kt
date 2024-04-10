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
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardType
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Comment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Member
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.repository.feature.CommunityRepository

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
    ): Result<Long> {
        randomShortDelay()

        return Result.success(0L)
    }

    override suspend fun removeMyBlock(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
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

    override suspend fun getBoardList(
        id: Long
    ): Result<List<Board>> {
        randomShortDelay()

        return Result.success(
            listOf(
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
            )
        )
    }

    override suspend fun getPopularPostPaging(): Flow<PagingData<Post>> {
        randomShortDelay()

        return flowOf(
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
        )
    }

    override suspend fun getPopularPostFromBlockPaging(
        id: Long
    ): Flow<PagingData<Post>> {
        randomShortDelay()

        return flowOf(
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
        )
    }

    override suspend fun searchPostFromBlockPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<Post>> {
        randomShortDelay()

        return flowOf(
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
        )
    }

    override suspend fun getBoard(
        id: Long
    ): Result<Board> {
        randomShortDelay()

        return Result.success(
            Board(
                id = 1,
                blockId = 1,
                type = BoardType.Notice,
                name = "공지사항",
                hasNewPost = true
            )
        )
    }

    override suspend fun searchPostFromBoardPaging(
        id: Long,
        keyword: String
    ): Flow<PagingData<Post>> {
        randomShortDelay()

        return flowOf(
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
        )
    }

    override suspend fun writePost(
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

    override suspend fun loadPost(
        id: Long
    ): Result<Post> {
        randomShortDelay()

        return Result.success(
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
            )
        )
    }

    override suspend fun editPost(
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

    override suspend fun removePost(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun likePost(
        id: Long
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun reportPost(
        id: Long,
        reason: String
    ): Result<Unit> {
        randomShortDelay()

        return Result.success(Unit)
    }

    override suspend fun loadCommentPaging(
        id: Long
    ): Flow<PagingData<Comment>> {
        randomShortDelay()

        return flowOf(
            PagingData.from(
                listOf(
                    Comment(
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
                    Comment(
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
                            Comment(
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
                            Comment(
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

    override suspend fun writeComment(
        id: Long,
        content: String,
        isAnonymous: Boolean
    ): Result<Long> {
        randomShortDelay()

        return Result.success(0L)
    }

    override suspend fun removeComment(
        id: Long
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
    ): Result<Long> {
        randomShortDelay()

        return Result.success(0L)
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
