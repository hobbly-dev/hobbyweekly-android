package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.todayIn
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Board
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardType
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Comment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Member
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Post
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodyRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodyRegularSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral030
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral600
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space4
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space6
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldXSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Transparent
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.makeRoute
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.util.toDurationString
import kr.hobbly.hobbyweekly.android.presentation.common.view.DialogScreen
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.dropdown.TextDropdownMenu
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.ProfileImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.textfield.EmptyTextField
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit.PostEditConstant

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostScreen(
    navController: NavController,
    argument: PostArgument,
    data: PostData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    val isMyPost = data.post.member.id == data.profile.id
    val formattedDate = data.post.createdAt.toDurationString()
    val menu: List<String> = mutableListOf<String>().apply {
        if (isMyPost) {
            add("수정하기")
            add("삭제하기")
        }
    }

    var isAnonymous: Boolean by remember { mutableStateOf(false) }
    var commentText: String by remember { mutableStateOf("") }
    var selectedComment: Comment? by remember { mutableStateOf(null) }

    var isMenuShowing by remember { mutableStateOf(false) }
    var isPostRemoveSuccessDialogShowing by remember { mutableStateOf(false) }
    var isPostReportSuccessDialogShowing by remember { mutableStateOf(false) }
    var isCommentWriteSuccessDialogShowing by remember { mutableStateOf(false) }
    var isCommentRemoveSuccessDialogShowing by remember { mutableStateOf(false) }
    var isCommentReportSuccessDialogShowing by remember { mutableStateOf(false) }

    fun navigateToPostEdit(
        post: Post
    ) {
        val route = makeRoute(
            PostEditConstant.ROUTE,
            listOf(
                PostEditConstant.ROUTE_ARGUMENT_BLOCK_ID to post.blockId,
                PostEditConstant.ROUTE_ARGUMENT_BOARD_ID to post.board.id,
                PostEditConstant.ROUTE_ARGUMENT_POST_ID to post.id
            )
        )
        navController.safeNavigate(route)
    }

    if (isPostRemoveSuccessDialogShowing) {
        DialogScreen(
            isCancelable = false,
            title = "게시글 알람",
            message = "게시글을 삭제하였습니다.",
            onConfirm = {
                navController.safeNavigateUp()
            },
            onDismissRequest = {
                isPostRemoveSuccessDialogShowing = false
            }
        )
    }
    if (isPostReportSuccessDialogShowing) {
        DialogScreen(
            isCancelable = false,
            title = "게시글 알람",
            message = "게시글을 신고하였습니다.",
            onConfirm = {
                navController.safeNavigateUp()
            },
            onDismissRequest = {
                isPostReportSuccessDialogShowing = false
            }
        )
    }
    if (isCommentWriteSuccessDialogShowing) {
        DialogScreen(
            isCancelable = false,
            title = "댓글 알람",
            message = "댓글을 작성하였습니다.",
            onConfirm = {
                intent(PostIntent.Comment.Refresh)
            },
            onDismissRequest = {
                isCommentWriteSuccessDialogShowing = false
            }
        )
    }
    if (isCommentRemoveSuccessDialogShowing) {
        DialogScreen(
            isCancelable = false,
            title = "댓글 알람",
            message = "댓글을 삭제하였습니다.",
            onConfirm = {
                intent(PostIntent.Comment.Refresh)
            },
            onDismissRequest = {
                isCommentRemoveSuccessDialogShowing = false
            }
        )
    }
    if (isCommentReportSuccessDialogShowing) {
        DialogScreen(
            isCancelable = false,
            title = "댓글 알람",
            message = "댓글을 신고하였습니다.",
            onConfirm = {
                intent(PostIntent.Comment.Refresh)
            },
            onDismissRequest = {
                isCommentReportSuccessDialogShowing = false
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
            if (menu.isNotEmpty()) {
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
                            if (text == "수정하기") {
                                navigateToPostEdit(data.post)
                            }
                            if (text == "삭제하기") {
                                intent(PostIntent.Post.OnRemove)
                            }
                        }
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Neutral030)
        ) {
            item {
                Column {
                    Spacer(modifier = Modifier.height(Space20))
                    Row(
                        modifier = Modifier.padding(horizontal = Space20),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileImage(
                            data = data.post.member.image,
                            modifier = Modifier.size(Space24)
                        )
                        Spacer(modifier = Modifier.width(Space12))
                        Column {
                            Text(
                                text = data.post.member.nickname,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = LabelRegular.merge(Neutral900)
                            )
                            Spacer(modifier = Modifier.height(Space4))
                            Text(
                                text = formattedDate,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = BodyRegular.merge(Neutral400)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(Space20))
                    Text(
                        text = data.post.title,
                        modifier = Modifier.padding(horizontal = Space24),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TitleSemiBoldXSmall.merge(Neutral900)
                    )
                    Spacer(modifier = Modifier.height(Space12))
                    Text(
                        text = data.post.content,
                        modifier = Modifier.padding(horizontal = Space24),
                        style = LabelRegular.merge(Neutral600)
                    )
                    if (data.post.imageList.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(Space20))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(Space16),
                            contentPadding = PaddingValues(start = Space20, end = Space20)
                        ) {
                            items(
                                items = data.post.imageList
                            ) { image ->
                                PostImage(
                                    data = image,
                                    modifier = Modifier.height(200.dp),
                                    contentScale = ContentScale.FillHeight,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(Space20))
                    Row(
                        modifier = Modifier.padding(horizontal = Space20),
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
                            text = if (data.post.commentCount > 99) "99+" else data.post.commentCount.toString(),
                            style = BodyRegular.merge(Neutral900)
                        )
                        Spacer(modifier = Modifier.width(Space4))
                        Icon(
                            modifier = Modifier.size(Space12),
                            painter = painterResource(R.drawable.ic_like),
                            contentDescription = null,
                            tint = Neutral900
                        )
                        Spacer(modifier = Modifier.width(Space4))
                        Text(
                            text = if (data.post.likeCount > 99) "99+" else data.post.likeCount.toString(),
                            style = BodyRegular.merge(Neutral900)
                        )
                        Spacer(modifier = Modifier.width(Space4))
                    }
                    Spacer(modifier = Modifier.height(Space12))
                    Row(
                        modifier = Modifier.padding(horizontal = Space20),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .background(Neutral100)
                        ) {
                            Box(
                                modifier = Modifier.clickable {
                                    intent(PostIntent.Post.OnLike)
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = Space12,
                                        vertical = Space4
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(Space12),
                                        painter = painterResource(R.drawable.ic_like),
                                        contentDescription = null,
                                        tint = Neutral900
                                    )
                                    Spacer(modifier = Modifier.width(Space6))
                                    Text(
                                        text = "공감",
                                        style = BodyRegularSmall.merge(Neutral900)
                                    )
                                }
                            }
                        }
//                        Spacer(modifier = Modifier.width(Space4))
//                        Box(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(100.dp))
//                                .background(Neutral100)
//                        ) {
//                            Box(
//                                modifier = Modifier.clickable {
//
//                                }
//                            ) {
//                                Row(
//                                    modifier = Modifier.padding(
//                                        horizontal = Space12,
//                                        vertical = Space4
//                                    ),
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Icon(
//                                        modifier = Modifier.size(Space12),
//                                        painter = painterResource(R.drawable.ic_bookmark),
//                                        contentDescription = null,
//                                        tint = Neutral900
//                                    )
//                                    Spacer(modifier = Modifier.width(Space6))
//                                    Text(
//                                        text = "스크랩",
//                                        style = BodyRegularSmall.merge(Neutral900)
//                                    )
//                                }
//                            }
//                        }
                    }
                    Spacer(modifier = Modifier.height(Space20))
                }
            }
            items(
                count = data.commentList.itemCount,
                key = { index ->
                    data.commentList[index]?.let { comment ->
                        "${comment.blockId}/${comment.boardId}/${comment.postId}/${comment.id}"
                    }.orEmpty()
                }
            ) { index ->
                data.commentList[index]?.let { comment ->
                    PostScreenCommentItem(
                        comment = comment,
                        profile = data.profile,
                        isChild = false,
                        selectedComment = selectedComment,
                        onComment = { comment, bringIntoViewRequester ->
                            selectedComment = comment
                            scope.launch {
                                focusRequester.requestFocus()
                                keyboard?.show()
                                bringIntoViewRequester.bringIntoView()
                            }
                        },
                        onLike = {
                            intent(PostIntent.Comment.OnLike(it.id))
                        },
                        onReport = { comment, reason ->
                            intent(
                                PostIntent.Comment.OnReport(
                                    commentId = comment.id,
                                    reason = reason
                                )
                            )
                        },
                        onDelete = {
                            intent(PostIntent.Comment.OnRemove(it.id))
                        }
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .background(White)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(Space8)
                    .clip(RoundedCornerShape(100.dp))
                    .background(Neutral100)
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = Space12,
                            vertical = Space4
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                    Spacer(modifier = Modifier.width(Space6))
                    EmptyTextField(
                        text = commentText,
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester),
                        style = BodyRegular.merge(Neutral900),
                        hintText = "댓글을 입력하세요",
                        hintStyle = BodyRegular.merge(Neutral400),
                        maxLines = 1,
                        maxTextLength = Int.MAX_VALUE,
                        onValueChange = {
                            commentText = it
                        }
                    )
                    Spacer(modifier = Modifier.width(Space6))
                    if (commentText.isEmpty()) {
                        Icon(
                            modifier = Modifier.size(Space24),
                            painter = painterResource(id = R.drawable.ic_send),
                            contentDescription = null,
                            tint = Neutral400
                        )
                    } else {
                        RippleBox(
                            onClick = {
                                intent(
                                    PostIntent.Comment.OnComment(
                                        parentId = selectedComment?.id ?: -1,
                                        commentText = commentText,
                                        isAnonymous = isAnonymous
                                    )
                                )
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(Space24),
                                painter = painterResource(id = R.drawable.ic_send),
                                contentDescription = null,
                                tint = Neutral900
                            )
                        }
                    }
                }
            }
        }
    }

    fun post(event: PostEvent.Post) {
        when (event) {
            PostEvent.Post.Remove.Success -> {
                isPostRemoveSuccessDialogShowing = true
            }

            PostEvent.Post.Report.Success -> {
                isPostReportSuccessDialogShowing = true
            }
        }
    }

    fun comment(event: PostEvent.Comment) {
        when (event) {
            is PostEvent.Comment.Write.Success -> {
                isCommentWriteSuccessDialogShowing = true
            }

            PostEvent.Comment.Remove.Success -> {
                isCommentRemoveSuccessDialogShowing = true
            }

            PostEvent.Comment.Report.Success -> {
                isCommentReportSuccessDialogShowing = true
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is PostEvent.Post -> {
                    post(event)
                }

                is PostEvent.Comment -> {
                    comment(event)
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(Unit, handler) {
        intent(PostIntent.Post.Refresh)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostScreenCommentItem(
    comment: Comment,
    profile: Profile,
    isChild: Boolean,
    selectedComment: Comment?,
    onComment: (Comment, BringIntoViewRequester) -> Unit,
    onLike: (Comment) -> Unit,
    onReport: (Comment, String) -> Unit,
    onDelete: (Comment) -> Unit,
) {
    val isMyComment = comment.member.id == comment.id
    val formattedDate = comment.createdAt.toDurationString()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val menu: List<String> = mutableListOf<String>().apply {
        if (isMyComment) {
            add("삭제하기")
        } else {
            add("신고하기")
        }
    }

    // TODO
    val reportReasonList: List<String> = listOf(
        "욕설/비방",
        "음란성",
        "광고/홍보",
        "개인정보 유출",
        "기타"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (comment == selectedComment) {
            Color(
                Red.red,
                Red.green,
                Red.blue,
                0.3f
            )
        } else {
            Transparent
        },
        label = comment.id.toString()
    )

    var isMenuShowing by remember { mutableStateOf(false) }
    var isReportReasonShowing by remember { mutableStateOf(false) }

    Column {
        Column(
            modifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequester)
                .background(backgroundColor)
        ) {
            Spacer(modifier = Modifier.height(Space16))
            Row(
                modifier = Modifier.padding(horizontal = Space20)
            ) {
                if (isChild) {
                    Icon(
                        modifier = Modifier.size(Space12),
                        painter = painterResource(R.drawable.ic_reply),
                        contentDescription = null,
                        tint = Neutral900
                    )
                    Spacer(modifier = Modifier.width(Space12))
                }
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileImage(
                            data = comment.member.image,
                            modifier = Modifier.size(Space24)
                        )
                        Spacer(modifier = Modifier.width(Space8))
                        Text(
                            text = comment.member.nickname,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = LabelRegular.merge(Neutral900)
                        )
                        Spacer(modifier = Modifier.width(Space8))
                        Text(
                            text = formattedDate,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = BodyRegular.merge(Neutral400)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .background(Neutral050)
                        ) {
                            Box(
                                modifier = Modifier.clickable {
                                    onComment(comment, bringIntoViewRequester)
                                }
                            ) {
                                Box(
                                    modifier = Modifier.padding(
                                        horizontal = Space12,
                                        vertical = Space4
                                    )
                                ) {
                                    Icon(
                                        modifier = Modifier.size(Space12),
                                        painter = painterResource(R.drawable.ic_talk),
                                        contentDescription = null,
                                        tint = Neutral900
                                    )
                                }
                            }
                            VerticalDivider(
                                thickness = 1.dp,
                                color = Neutral100
                            )
                            Box(
                                modifier = Modifier.clickable {
                                    onLike(comment)
                                }
                            ) {
                                Box(
                                    modifier = Modifier.padding(
                                        horizontal = Space12,
                                        vertical = Space4
                                    )
                                ) {
                                    Icon(
                                        modifier = Modifier.size(Space12),
                                        painter = painterResource(R.drawable.ic_like),
                                        contentDescription = null,
                                        tint = Neutral900
                                    )
                                }
                            }
                            VerticalDivider(
                                thickness = 1.dp,
                                color = Neutral100
                            )
                            Box(
                                modifier = Modifier.clickable {
                                    isMenuShowing = true
                                }
                            ) {
                                Box(
                                    modifier = Modifier.padding(
                                        horizontal = Space12,
                                        vertical = Space4
                                    )
                                ) {
                                    Icon(
                                        modifier = Modifier.size(Space12),
                                        painter = painterResource(R.drawable.ic_more_vertical),
                                        contentDescription = null,
                                        tint = Neutral900
                                    )
                                    TextDropdownMenu(
                                        items = menu,
                                        isExpanded = isMenuShowing,
                                        onDismissRequest = { isMenuShowing = false },
                                        onClick = {
                                            if (it == "삭제하기") {
                                                onDelete(comment)
                                            }
                                            if (it == "신고하기") {
                                                isReportReasonShowing = true
                                            }
                                        }
                                    )
                                    TextDropdownMenu(
                                        items = reportReasonList,
                                        isExpanded = isReportReasonShowing,
                                        onDismissRequest = { isReportReasonShowing = false },
                                        onClick = { reason ->
                                            onReport(comment, reason)
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(Space12))
                    Text(
                        text = comment.content,
                        style = BodyRegular.merge(Neutral900)
                    )
                }
            }
            Spacer(modifier = Modifier.height(Space16))
        }
        comment.child.forEach { child ->
            PostScreenCommentItem(
                comment = child,
                profile = profile,
                isChild = true,
                selectedComment = selectedComment,
                onComment = onComment,
                onLike = onLike,
                onReport = onReport,
                onDelete = onDelete
            )
        }
    }
}

@Preview
@Composable
private fun PostScreenPreview() {
    PostScreen(
        navController = rememberNavController(),
        argument = PostArgument(
            state = PostState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = PostData(
            post = Post(
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
            profile = Profile(
                id = 1,
                nickname = "장성혁",
                image = "https://avatars.githubusercontent.com/u/48707913?v=4",
                isHobbyChecked = true
            ),
            commentList = MutableStateFlow<PagingData<Comment>>(PagingData.empty()).collectAsLazyPagingItems(),
        )
    )
}
