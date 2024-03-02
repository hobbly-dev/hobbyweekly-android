package kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery

import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryFolder
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage
import androidx.compose.runtime.Immutable
import androidx.paging.compose.LazyPagingItems

@Immutable
data class GalleryData(
    val folderList: List<GalleryFolder>,
    val galleryImageList: LazyPagingItems<GalleryImage>
)
