package kr.hobbly.hobbyweekly.android.presentation.model.gallery

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GalleryImage(
    val id: Long,
    val filePath: String,
    val name: String
) : Parcelable
