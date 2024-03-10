package kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space6
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@Composable
fun GalleryItemContent(
    galleryImage: GalleryImage,
    selected: Boolean,
    onSelectImage: (GalleryImage) -> Unit,
    onDeleteImage: (GalleryImage) -> Unit
) {
    Box {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(galleryImage.filePath)
                .crossfade(true)
                .build(),
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = White,
                        modifier = Modifier.fillMaxSize(0.5f)
                    )

                }
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .animateContentSize()
                .clickable {
                    if (selected) {
                        onDeleteImage(galleryImage)
                    } else {
                        onSelectImage(galleryImage)
                    }
                },
            alpha = if (selected) 1f else 0.4f
        )
        if (selected) {
            Box(
                modifier = Modifier.padding(Space6)
            ) {
                Icon(
                    modifier = Modifier.size(Space24),
                    painter = painterResource(R.drawable.ic_check_box_checked),
                    contentDescription = null,
                    tint = Red
                )
            }
        }
    }
}
