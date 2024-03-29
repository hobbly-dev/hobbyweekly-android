package kr.hobbly.hobbyweekly.android.presentation.ui.main.home

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space30
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space36
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.CommunityScreen
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.MyPageScreen
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.RoutineScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    argument: HomeArgument,
    data: HomeData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    val pagerState = rememberPagerState(
        pageCount = { data.homeTypeList.size },
    )
    var selectedHomeType: HomeType by rememberSaveable { mutableStateOf(data.initialHomeType) }

    val perMissionAlbumLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (data.homeTypeList.getOrNull(page)) {
                HomeType.Routine -> {
                    RoutineScreen(
                        navController = navController,
                        parentArgument = argument
                    )
                }

                HomeType.Community -> {
                    CommunityScreen(
                        navController = navController,
                        parentArgument = argument
                    )
                }

                HomeType.MyPage -> {
                    MyPageScreen(
                        navController = navController,
                        parentArgument = argument
                    )
                }

                null -> Unit
            }
        }

        HomeBottomBarScreen(
            itemList = data.homeTypeList,
            selectedHomeType = selectedHomeType,
            onClick = {
                selectedHomeType = it
            }
        )
    }

    LaunchedEffect(perMissionAlbumLauncher) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            perMissionAlbumLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    LaunchedEffect(selectedHomeType) {
        pagerState.animateScrollToPage(data.homeTypeList.indexOf(selectedHomeType))
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is HomeEvent.ChangeHomeType -> {
                    selectedHomeType = event.homeType
                }
            }
        }
    }
}

@Composable
private fun HomeBottomBarScreen(
    itemList: List<HomeType>,
    selectedHomeType: HomeType,
    onClick: (HomeType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Space56),
        horizontalArrangement = Arrangement.spacedBy(Space30, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemList.forEach { item ->
            val isSelected = item == selectedHomeType
            Box(
                modifier = Modifier
                    .clip(CircleShape)
//                    .background(
//                        if (isSelected) Red else Neutral100
//                    )
            ) {
                Image(
                    modifier = Modifier
                        .size(Space36)
                        .clickable {
                            onClick(item)
                        },
                    painter = painterResource(item.iconRes),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),
        argument = HomeArgument(
            state = HomeState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = HomeData(
            initialHomeType = HomeType.MyPage,
            homeTypeList = emptyList()
        )
    )
}
