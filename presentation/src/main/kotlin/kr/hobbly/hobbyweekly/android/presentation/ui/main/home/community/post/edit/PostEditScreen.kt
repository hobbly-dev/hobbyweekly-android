package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.common.util.takeIfNotEmpty
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardType
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodyRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral030
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space4
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldXSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.makeRoute
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.view.DialogScreen
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.textfield.EmptyTextField
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage
import kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery.GalleryScreen
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.PostConstant
import timber.log.Timber

@Composable
fun PostEditScreen(
    navController: NavController,
    argument: PostEditArgument,
    data: PostEditData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val context = LocalContext.current

    var title: String by rememberSaveable { mutableStateOf("") }
    var content: String by rememberSaveable { mutableStateOf("") }
    var originalImageList: List<String> by rememberSaveable { mutableStateOf(emptyList()) }
    var newImageList: List<GalleryImage> by rememberSaveable { mutableStateOf(emptyList()) }
    val isPostButtonEnabled =
        title.isNotEmpty() && content.isNotEmpty() && state != PostEditState.Loading
    var isAnonymous: Boolean by rememberSaveable { mutableStateOf(false) }
    var isSecret: Boolean by rememberSaveable { mutableStateOf(false) }

    var isGalleryShowing: Boolean by rememberSaveable { mutableStateOf(false) }
    var isLeaveDialogShowing: Boolean by rememberSaveable { mutableStateOf(false) }

    fun navigateToCommunityTerm() {
        runCatching {
            val link =
                Uri.parse("https://sites.google.com/view/hobblyofficial/hobby-weekly?authuser=0#h.esvmsklk4668")
            val browserIntent = Intent(Intent.ACTION_VIEW, link)
            ContextCompat.startActivity(context, browserIntent, null)
        }.onFailure { exception ->
            Timber.d(exception)
//            Sentry.withScope {
//                it.level = SentryLevel.INFO
//                Sentry.captureException(exception)
//            }
            Firebase.crashlytics.recordException(exception)
        }
    }

    fun navigateToPost(
        id: Long
    ) {
        val route = makeRoute(
            PostConstant.ROUTE,
            listOf(
                PostConstant.ROUTE_ARGUMENT_BLOCK_ID to data.blockId,
                PostConstant.ROUTE_ARGUMENT_BOARD_ID to data.boardId,
                PostConstant.ROUTE_ARGUMENT_POST_ID to id
            )
        )
        navController.safeNavigate(route) {
            popUpTo(PostEditConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    if (isGalleryShowing) {
        GalleryScreen(
            navController = navController,
            onDismissRequest = { isGalleryShowing = false },
            selectedImageList = newImageList,
            maxSelectCount = 10,
            onResult = {
                newImageList = it
            }
        )
    }
    if (isLeaveDialogShowing) {
        if (data.postId == -1L) {
            DialogScreen(
                title = "게시글 작성",
                message = "게시글 작성을 중단하시겠습니까?\n작성된 내용이 저장되지 않습니다",
                confirmMessage = "종료",
                onDismissRequest = {
                    isLeaveDialogShowing = false
                },
                onCancel = {},
                onConfirm = {
                    navController.safeNavigateUp()
                }
            )
        } else {
            DialogScreen(
                title = "게시글 수정",
                message = "게시글 수정을 중단하시겠습니까?\n변경된 내용이 저장되지 않습니다",
                confirmMessage = "종료",
                onDismissRequest = {
                    isLeaveDialogShowing = false
                },
                onCancel = {},
                onConfirm = {
                    navController.safeNavigateUp()
                }
            )
        }
    }

    BackHandler(
        enabled = true,
        onBack = {
            isLeaveDialogShowing = true
        }
    )

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
                text = data.block.name.takeIfNotEmpty()
                    ?.plus(" ${data.board.name}")
                    ?.plus(" 작성란")
                    ?: "하비위클리",
                modifier = Modifier.align(Alignment.Center),
                style = TitleSemiBoldSmall.merge(Neutral900)
            )
            if (isPostButtonEnabled) {
                RippleBox(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = Space20),
                    onClick = {
                        logEvent("btn_post_success", emptyMap())
                        intent(
                            PostEditIntent.OnPost(
                                title = title,
                                content = content,
                                originalImageList = originalImageList,
                                newImageList = newImageList,
                                isSecret = isSecret,
                                isAnonymous = isAnonymous
                            )
                        )
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(Space24),
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = null,
                        tint = Neutral900
                    )
                }
            } else if (state == PostEditState.Loading) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = Space20)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(Space24)
                            .align(Alignment.Center),
                        color = Neutral400,
                        strokeWidth = 2.dp
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = Space20)
                ) {
                    Icon(
                        modifier = Modifier.size(Space24),
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = null,
                        tint = Neutral400
                    )
                }
            }
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .imePadding()
        ) {
            val (contents, bottomBar, gallery) = createRefs()
            Column(
                modifier = Modifier
                    .constrainAs(contents) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(bottomBar.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(Space12))
                EmptyTextField(
                    text = title,
                    style = TitleMedium.merge(Neutral900),
                    hintText = "제목",
                    hintStyle = TitleMedium.merge(Neutral400),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Space20),
                    maxLines = 1,
                    isEnabled = state != PostEditState.Loading,
                    maxTextLength = Int.MAX_VALUE,
                    onValueChange = {
                        title = it
                    }
                )
                Spacer(modifier = Modifier.height(Space12))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Neutral200
                )
                Spacer(modifier = Modifier.height(Space12))
                EmptyTextField(
                    text = content,
                    style = LabelRegular.merge(Neutral900),
                    hintText = "내용을 입력하세요",
                    hintStyle = LabelRegular.merge(Neutral400),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Space20)
                        .heightIn(min = 200.dp),
                    isEnabled = state != PostEditState.Loading,
                    maxLines = Int.MAX_VALUE,
                    maxTextLength = Int.MAX_VALUE,
                    onValueChange = {
                        content = it
                    }
                )
                Spacer(modifier = Modifier.height(Space12))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = Space20),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(Neutral100)
                    ) {
                        Row(
                            modifier = Modifier.clickable {
                                navigateToCommunityTerm()
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(Space12))
                            Text(
                                text = "커뮤니티 이용규칙",
                                modifier = Modifier.padding(vertical = Space8),
                                style = TitleSemiBoldXSmall.merge(White)
                            )
                            Icon(
                                modifier = Modifier
                                    .size(Space24)
                                    .padding(vertical = Space8),
                                painter = painterResource(id = R.drawable.ic_chevron_right),
                                contentDescription = null,
                                tint = White
                            )
                            Spacer(modifier = Modifier.width(Space12))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Space12))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Space20),
                    text = "하비위클리는 누구나 기분 좋게 참여할 수 있는 커뮤니티를 만들기 위해 커뮤니티 이용규칙을 제정하여 운영하고 있습니다.위반 시 게시물이 삭제되고 서비스 이용이 제한될 수 있습니다.\n" +
                            "아래는 이 게시판에 해당하는 핵심 내용에 대한 요약 사항이며, 게시물 작성전 커뮤니티 이용규칙 전문을 반드시 확인하시길 바랍니다. \n" +
                            "정치 사회 관련 행위금지\n" +
                            "국가기관, 정치 관련 단체, 언론, 시민단체에 대한 언급 혹은 이와 관련한 행위\n" +
                            "성별,종교,인종,출신,지역,작업,이념 등 사회적 이슈에 대한 언급 혹은 이와 관련된 행위\n" +
                            "홍보 및 판매 관련 행위 금지\n" +
                            "영리 여부와 관계 없이 사업체,기관,단체 개인에게 직간접적으로 영향을 줄 수 있는 게시물 작성 행위\n" +
                            "위와 관련된 것으로 의심되거나 예상될 수 있는 바이럴 홍보 마케팅 및 명칭,단어 언급 행위\n" +
                            "불법 촬영물 유통 금지",
                    style = LabelRegular.merge(Neutral200)
                )
                Spacer(modifier = Modifier.height(140.dp))
            }
            LazyRow(
                modifier = Modifier.constrainAs(gallery) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBar.top, margin = Space20)
                    width = Dimension.fillToConstraints
                },
                horizontalArrangement = Arrangement.spacedBy(Space16),
                contentPadding = PaddingValues(start = Space20, end = Space20)
            ) {
                items(originalImageList) { item ->
                    PostEditScreenImageItem(
                        item = item,
                        isLoading = state == PostEditState.Loading,
                        itemToData = { it },
                        onRemove = {
                            originalImageList = originalImageList.filter { it != item }
                        }
                    )
                }
                items(newImageList) { item ->
                    PostEditScreenImageItem(
                        item = item,
                        isLoading = state == PostEditState.Loading,
                        itemToData = { item.filePath },
                        onRemove = {
                            newImageList = newImageList.filter { it != item }
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .constrainAs(bottomBar) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .height(Space56)
                    .background(Neutral050),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(Space20))
                if (state == PostEditState.Loading) {
                    Icon(
                        modifier = Modifier.size(Space24),
                        painter = painterResource(R.drawable.ic_photo),
                        contentDescription = null,
                        tint = Neutral400
                    )
                } else {
                    RippleBox(
                        onClick = {
                            isGalleryShowing = true
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(Space24),
                            painter = painterResource(R.drawable.ic_photo),
                            contentDescription = null,
                            tint = Neutral900
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if (state == PostEditState.Loading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(Space12),
                            painter = if (isSecret) {
                                painterResource(id = R.drawable.ic_check_box_checked)
                            } else {
                                painterResource(id = R.drawable.ic_check_box_unchecked)
                            },
                            contentDescription = null,
                            tint = Neutral400
                        )
                        Spacer(modifier = Modifier.width(Space4))
                        Text(
                            text = "비밀글",
                            modifier = Modifier.padding(vertical = Space8),
                            style = BodyRegular.merge(Neutral400)
                        )
                    }
                } else {
                    RippleBox(
                        onClick = {
                            isSecret = !isSecret
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(Space12),
                                painter = if (isSecret) {
                                    painterResource(id = R.drawable.ic_check_box_checked)
                                } else {
                                    painterResource(id = R.drawable.ic_check_box_unchecked)
                                },
                                contentDescription = null,
                                tint = Red
                            )
                            Spacer(modifier = Modifier.width(Space4))
                            Text(
                                text = "비밀글",
                                modifier = Modifier.padding(vertical = Space8),
                                style = BodyRegular.merge(Neutral900)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(Space10))
                if (state == PostEditState.Loading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(Space12),
                            painter = if (isAnonymous) {
                                painterResource(id = R.drawable.ic_check_box_checked)
                            } else {
                                painterResource(id = R.drawable.ic_check_box_unchecked)
                            },
                            contentDescription = null,
                            tint = Neutral400
                        )
                        Spacer(modifier = Modifier.width(Space4))
                        Text(
                            text = "익명",
                            modifier = Modifier.padding(vertical = Space8),
                            style = BodyRegular.merge(Neutral400)
                        )
                    }
                } else {
                    RippleBox(
                        onClick = {
                            isAnonymous = !isAnonymous
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(Space12),
                                painter = if (isAnonymous) {
                                    painterResource(id = R.drawable.ic_check_box_checked)
                                } else {
                                    painterResource(id = R.drawable.ic_check_box_unchecked)
                                },
                                contentDescription = null,
                                tint = Red
                            )
                            Spacer(modifier = Modifier.width(Space4))
                            Text(
                                text = "익명",
                                modifier = Modifier.padding(vertical = Space8),
                                style = BodyRegular.merge(Neutral900)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(Space20))
            }
        }
    }

    fun load(event: PostEditEvent.Load) {
        when (event) {
            is PostEditEvent.Load.Success -> {
                title = event.post.title
                content = event.post.content
                originalImageList = event.post.imageList
                isSecret = event.post.isSecret
                isAnonymous = event.post.isAnonymous
            }
        }
    }

    fun post(event: PostEditEvent.Post) {
        when (event) {
            is PostEditEvent.Post.Success -> {
                navigateToPost(event.id)
            }
        }
    }

    fun edit(event: PostEditEvent.Edit) {
        when (event) {
            is PostEditEvent.Edit.Success -> {
                navController.safeNavigateUp()
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is PostEditEvent.Load -> {
                    load(event)
                }

                is PostEditEvent.Post -> {
                    post(event)
                }

                is PostEditEvent.Edit -> {
                    edit(event)
                }
            }
        }
    }
}

@Composable
fun <T> PostEditScreenImageItem(
    item: T,
    isLoading: Boolean,
    itemToData: (T) -> Any?,
    onRemove: (T) -> Unit
) {
    Box {
        PostImage(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center),
            data = itemToData(item)
        )
        Box(
            modifier = Modifier
                .padding(Space4)
                .align(Alignment.TopStart)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Neutral400)
                ) {
                    Icon(
                        modifier = Modifier.size(Space20),
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                        tint = White
                    )
                }
            } else {
                RippleBox(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Red),
                    onClick = {
                        onRemove(item)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(Space20),
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                        tint = White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PostEditScreenPreview() {
    PostEditScreen(
        navController = rememberNavController(),
        argument = PostEditArgument(
            state = PostEditState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = PostEditData(
            block = Block(
                id = 5,
                name = "코딩 블록",
                content = "코딩을 취미로 하는 사람들의 모임",
                image = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                thumbnail = "https://i.namu.wiki/i/mQNc8LS1ABA0-jPY-PWldlZPpCB8cgcqgZNvE__Rk1Fw3FmCehm55EaqbsjsK-vTuhEeIj5bFiUdFIRr7RzOdckq2RiVOMM9otmh4yrcmiLKjfNlWJEN976c4ZS-SY8WfhlPSs5DsAvvQZukz3eRWg.webp",
                memberCount = 100
            ),
            board = Board(
                id = 1,
                blockId = 1,
                type = BoardType.Notice,
                name = "공지사항",
                hasNewPost = true
            ),
            blockId = 1L,
            boardId = 1L,
            postId = 1L,
            routineId = 1L
        )
    )
}
