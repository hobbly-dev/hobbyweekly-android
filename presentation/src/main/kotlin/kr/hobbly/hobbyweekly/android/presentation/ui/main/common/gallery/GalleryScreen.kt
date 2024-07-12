package kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Black
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Blue
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodyRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space4
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space56
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space8
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleSemiBoldSmall
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Warning
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigateUp
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.dropdown.TextDropdownMenu
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryFolder
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@Composable
fun GalleryScreen(
    navController: NavController,
    argument: GalleryArgument,
    data: GalleryData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler
    val localConfiguration = LocalConfiguration.current

    val perMissionAlbumLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            intent(GalleryIntent.OnGrantPermission)
        } else {
            // TODO : 고도화
            navController.navigateUp()
        }
    }
    val selectedImageUriList: MutableList<String> = remember {
        mutableStateListOf<String>(*data.selectedImageUriList.toTypedArray())
    }
    var isDropDownMenuExpanded: Boolean by remember { mutableStateOf(false) }
    var currentFolder: GalleryFolder by remember { mutableStateOf(GalleryFolder.recent) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        val (topBar, contents) = createRefs()

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(contents) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(top = Space56),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            // TODO : 아무것도 없을 경우 처리
            items(data.galleryImageList.itemCount) { index ->
                data.galleryImageList[index]?.let { gallery ->
                    GalleryScreenItem(
                        galleryImage = gallery,
                        selectedUriList = selectedImageUriList,
                        onSelectImage = { image ->
                            if (selectedImageUriList.size < data.selectRange.last) {
                                selectedImageUriList.add(image.filePath)
                            }
                        },
                        onDeleteImage = { image ->
                            selectedImageUriList.remove(image.filePath)
                        }
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .height(Space56)
                .background(White.copy(alpha = 0.9f))
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(Space20))
            RippleBox(
                onClick = {
                    navController.safeNavigateUp()
                }
            ) {
                Icon(
                    modifier = Modifier.size(Space24),
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    tint = Neutral900
                )
            }
            Spacer(modifier = Modifier.width(Space20))
            RippleBox(
                onClick = {
                    isDropDownMenuExpanded = true
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentFolder.name,
                        style = TitleSemiBoldSmall.merge(Neutral900)
                    )
                    Icon(
                        modifier = Modifier.size(Space16),
                        painter = painterResource(R.drawable.ic_arrow_down),
                        contentDescription = null,
                        tint = Neutral900
                    )
                }
                TextDropdownMenu(
                    items = data.folderList,
                    label = { it.name },
                    isExpanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false },
                    onClick = { folder ->
                        currentFolder = folder
                        intent(GalleryIntent.OnChangeFolder(folder))
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (selectedImageUriList.size in data.selectRange) {
                Text(
                    text = selectedImageUriList.size.toString(),
                    style = TitleSemiBoldSmall.merge(Blue)
                )
                Spacer(modifier = Modifier.width(Space8))
                RippleBox(
                    onClick = {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            GalleryConstant.RESULT_IMAGE_URI_LIST,
                            selectedImageUriList.toTypedArray()
                        )
                        navController.safeNavigateUp()
                    }
                ) {
                    Text(
                        text = "확인",
                        style = TitleSemiBoldSmall.merge(Neutral900)
                    )
                }
            } else {
                Text(
                    text = "확인",
                    style = TitleSemiBoldSmall.merge(Neutral400)
                )
            }
            Spacer(modifier = Modifier.width(Space20))
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            perMissionAlbumLauncher.launch(
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            perMissionAlbumLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
fun GalleryScreenItem(
    galleryImage: GalleryImage,
    selectedUriList: List<String>,
    onSelectImage: (GalleryImage) -> Unit,
    onDeleteImage: (GalleryImage) -> Unit
) {
    val context = LocalContext.current

    val index = selectedUriList.indexOfFirst { it == galleryImage.filePath }

    Box(
        modifier = Modifier
            .background(Neutral200)
            .clickable {
                if (index > -1) {
                    onDeleteImage(galleryImage)
                } else {
                    onSelectImage(galleryImage)
                }
            }
            .run {
                if (index > -1) {
                    border(BorderStroke(2.dp, Blue))
                } else {
                    this
                }
            }
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(galleryImage.filePath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(Space24)
                            .align(Alignment.Center),
                        color = Neutral100,
                        strokeWidth = 2.dp
                    )
                }
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Space24)
                            .align(Alignment.Center),
                        painter = painterResource(R.drawable.ic_error),
                        contentDescription = null,
                        tint = Warning
                    )
                }
            }
        )

        if (index > -1) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .align(Alignment.Center)
                    .background(Black.copy(alpha = 0.4f))
            )
        }

        if (index > -1) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Space8)
            ) {
                Box(
                    modifier = Modifier
                        .size(Space24)
                        .clip(CircleShape)
                        .background(Blue, CircleShape)
                ) {
                    Text(
                        text = (index + 1).toString(),
                        modifier = Modifier.align(Alignment.Center),
                        style = TitleSemiBoldSmall.merge(White)
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Space8)
            ) {
                Box(
                    modifier = Modifier
                        .size(Space24)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(White.copy(alpha = 0.6f), CircleShape)
                        .border(1.dp, Neutral400, CircleShape)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Black.copy(alpha = 0.6f))
        ) {
            Box(
                modifier = Modifier.padding(Space4)
            ) {
                Text(
                    text = galleryImage.name,
                    minLines = 2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = BodyRegular.merge(White)
                )
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun GalleryScreenPreview1() {
    GalleryScreen(
        navController = rememberNavController(),
        argument = GalleryArgument(
            state = GalleryState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = GalleryData(
            folderList = listOf(),
            galleryImageList = MutableStateFlow<PagingData<GalleryImage>>(PagingData.empty()).collectAsLazyPagingItems(),
            selectedImageUriList = listOf(),
            selectRange = 1..1
        )
    )
}

@Preview(apiLevel = 34)
@Composable
private fun GalleryScreenPreview2() {
    GalleryScreen(
        navController = rememberNavController(),
        argument = GalleryArgument(
            state = GalleryState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = GalleryData(
            folderList = listOf(),
            galleryImageList = MutableStateFlow<PagingData<GalleryImage>>(
                PagingData.from(
                    listOf(
                        GalleryImage(
                            id = 1,
                            filePath = "https://via.placeholder.com/150",
                            name = "image1"
                        ),
                        GalleryImage(
                            id = 2,
                            filePath = "https://via.placeholder.com/150",
                            name = "image2"
                        )
                    )
                )
            ).collectAsLazyPagingItems(),
            selectedImageUriList = listOf(
                "https://via.placeholder.com/150"
            ),
            selectRange = 1..1
        )
    )
}
