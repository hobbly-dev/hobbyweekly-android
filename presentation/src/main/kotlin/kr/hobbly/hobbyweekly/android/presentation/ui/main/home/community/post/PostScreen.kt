package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post

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
import androidx.compose.ui.layout.ContentScale
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
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardComment
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.BoardPost
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Member
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
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.makeRoute
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.util.toDurationString
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.dropdown.TextDropdownMenu
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.PostImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.image.ProfileImage
import kr.hobbly.hobbyweekly.android.presentation.common.view.textfield.EmptyTextField
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.post.edit.PostEditConstant

@Composable
fun PostScreen(
    navController: NavController,
    argument: PostArgument,
    data: PostData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    val isMyPost = data.post.member.id == data.profile.id
    val formattedDate = data.post.createdAt.toDurationString()

    var isAnonymous: Boolean by remember { mutableStateOf(false) }
    var commentText: String by remember { mutableStateOf("") }

    var isMenuShowing by remember { mutableStateOf(false) }
    val menu: List<String> = mutableListOf<String>().apply {
        if (isMyPost) {
            add("수정하기")
            add("삭제하기")
        }
    }

    fun navigateToPostEdit(
        post: BoardPost
    ) {
        val route = makeRoute(
            PostEditConstant.ROUTE,
            listOf(
                PostEditConstant.ROUTE_ARGUMENT_BLOCK_ID to post.blockId,
                PostEditConstant.ROUTE_ARGUMENT_BOARD_ID to post.boardId,
                PostEditConstant.ROUTE_ARGUMENT_POST_ID to post.id
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
                },
                contentType = { index ->
                    // TODO : 아직 Comment 구조 미정의
                }
            ) { index ->
                data.commentList[index]?.let { comment ->
                    // TODO : 아직 Comment 구조 미정의
                    PostScreenCommentItem(
                        comment = comment,
                        profile = data.profile,
                        onComment = {
                            // TODO : 아직 UI 미정의
                        },
                        onLike = {
                            intent(PostIntent.Comment.OnLike(it.id))
                        },
                        onReport = {
                            // TODO : 아직 UI 미정의
                        },
                        onEdit = {
                            // TODO : 아직 UI 미정의
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
                        style = BodyRegular.merge(Neutral900),
                        hintText = "댓글을 입력하세요",
                        hintStyle = BodyRegular.merge(Neutral400),
                        modifier = Modifier.weight(1f),
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
                                        parentId = -1, // TODO
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
                // TODO
            }

            PostEvent.Post.Report.Success -> {
                // TODO
            }
        }
    }

    fun comment(event: PostEvent.Comment) {
        when (event) {
            is PostEvent.Comment.Write.Success -> {
                // TODO
            }

            PostEvent.Comment.Remove.Success -> {
                // TODO
            }

            PostEvent.Comment.Report.Success -> {
                // TODO
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
}

// TODO : Paging State Loading
@Composable
fun PostScreenCommentItem(
    comment: BoardComment,
    profile: Profile,
    onComment: (BoardComment) -> Unit,
    onLike: (BoardComment) -> Unit,
    onReport: (BoardComment) -> Unit,
    onEdit: (BoardComment) -> Unit,
    onDelete: (BoardComment) -> Unit,
) {
    val isMyComment = comment.member.id == comment.id
    val formattedDate = comment.createdAt.toDurationString()

    val menu: List<String> = mutableListOf<String>().apply {
        if (isMyComment) {
            add("수정하기")
            add("삭제하기")
        } else {
            add("신고하기")
        }
    }

    var isMenuShowing by remember { mutableStateOf(false) }

    Column {
        Spacer(modifier = Modifier.height(Space16))
        Row(
            modifier = Modifier.padding(horizontal = Space20),
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
                        onComment(comment)
                    }
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = Space12, vertical = Space4)
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
                        onComment(comment)
                    }
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = Space12, vertical = Space4)
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
                        onComment(comment)
                    }
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = Space12, vertical = Space4)
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
                                if (it == "수정하기") {
                                    onEdit(comment)
                                }
                                if (it == "삭제하기") {
                                    onDelete(comment)
                                }
                                if (it == "신고하기") {
                                    onReport(comment)
                                }
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(Space12))
        Text(
            text = comment.content,
            modifier = Modifier.padding(horizontal = Space20),
            style = BodyRegular.merge(Neutral900)
        )
        Spacer(modifier = Modifier.height(Space16))
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
            post = BoardPost(
                id = 1,
                blockId = 1,
                boardId = 1,
                member = Member(
                    id = 1,
                    nickname = "장성혁",
                    image = "https://avatars.githubusercontent.com/u/48707913?v=4"
                ),
                title = "휴식 인증합니다",
                content = "휴식 했습니다.",
                imageList = listOf(),
                createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                    .minus(1, DateTimeUnit.WEEK)
                    .atTime(
                        0, 0, 0
                    ),
                updatedAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                    .minus(1, DateTimeUnit.WEEK)
                    .atTime(
                        0, 0, 0
                    ),
                commentCount = 0,
                likeCount = 0,
                isAnonymous = false,
                isSecret = false
            ),
            profile = Profile(
                id = 1,
                nickname = "장성혁",
                image = "https://avatars.githubusercontent.com/u/48707913?v=4",
                isHobbyChecked = true
            ),
            commentList = MutableStateFlow<PagingData<BoardComment>>(PagingData.empty()).collectAsLazyPagingItems(),
        )
    )
}
