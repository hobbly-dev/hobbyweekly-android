package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.block

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
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
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space60
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.view.BottomSheetScreen
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButton
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonType
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.RoutineIntent

@Composable
fun RoutineBlockScreen(
    navController: NavController,
    onDismissRequest: () -> Unit,
    onClickBlock: (Block) -> Unit,
    onConfirm: () -> Unit,
    viewModel: RoutineBlockViewModel = hiltViewModel()
) {
    val argument: RoutineBlockArgument = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        RoutineBlockArgument(
            state = state,
            event = viewModel.event,
            intent = viewModel::onIntent,
            logEvent = viewModel::logEvent,
            handler = viewModel.handler
        )
    }

    val data: RoutineBlockData = Unit.let {
        val myBlockList by viewModel.myBlockList.collectAsStateWithLifecycle()

        RoutineBlockData(
            myBlockList = myBlockList
        )
    }

    RoutineBlockScreen(
        navController = navController,
        argument = argument,
        data = data,
        onDismissRequest = onDismissRequest,
        onClickBlock = onClickBlock,
        onConfirm = onConfirm
    )
}

@Composable
private fun RoutineBlockScreen(
    navController: NavController,
    argument: RoutineBlockArgument,
    data: RoutineBlockData,
    onDismissRequest: () -> Unit,
    onClickBlock: (Block) -> Unit,
    onConfirm: () -> Unit
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val localConfiguration = LocalConfiguration.current

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(White),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(Space40))
            Text(
                modifier = Modifier.padding(horizontal = Space20),
                text = "블록 설정",
                style = TitleSemiBold.merge(Neutral900)
            )
            Spacer(modifier = Modifier.height(Space20))
            if (data.myBlockList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(horizontal = Space20)
                        .clip(RoundedCornerShape(Radius12))
                        .background(Neutral050)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = Space20)
                            .align(Alignment.Center),
                        text = "취미블록이 없습니다. 블록 커뮤니티에서 취미블록을 찾아보세요!",
                        style = TitleMedium.merge(Neutral400)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = Space20),
                    verticalArrangement = Arrangement.spacedBy(Space16)
                ) {
                    items(
                        items = data.myBlockList,
                        key = { it.id }
                    ) { block ->
                        RoutineBlockScreenBlockItem(
                            block = block,
                            onClick = {
                                onDismissRequest()
                                onClickBlock(it)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(Space80))
            Text(
                modifier = Modifier
                    .padding(horizontal = Space20)
                    .fillMaxWidth(),
                text = "블록을 추가하려면 버튼을 클릭하세요",
                textAlign = TextAlign.Center,
                style = TitleMedium.merge(Neutral400)
            )
            Spacer(modifier = Modifier.height(Space20))
            ConfirmButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Space20, end = Space20, bottom = Space12),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                onClick = {
                    onDismissRequest()
                    onConfirm()
                }
            ) { style ->
                Text(
                    text = "블록 커뮤니티 가기",
                    style = style
                )
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }

    LaunchedEffectWithLifecycle(Unit, handler) {
        intent(RoutineBlockIntent.Refresh)
    }
}

@Composable
private fun RoutineBlockScreenBlockItem(
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

@Preview(apiLevel = 34)
@Composable
private fun RoutineBlockScreenPreview1() {
    RoutineBlockScreen(
        navController = rememberNavController(),
        argument = RoutineBlockArgument(
            state = RoutineBlockState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = RoutineBlockData(
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
            )
        ),
        onDismissRequest = {},
        onClickBlock = {},
        onConfirm = {}
    )
}

@Preview(apiLevel = 34)
@Composable
private fun RoutineBlockScreenPreview2() {
    RoutineBlockScreen(
        navController = rememberNavController(),
        argument = RoutineBlockArgument(
            state = RoutineBlockState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = RoutineBlockData(
            myBlockList = emptyList()
        ),
        onDismissRequest = {},
        onClickBlock = {},
        onConfirm = {}
    )
}
