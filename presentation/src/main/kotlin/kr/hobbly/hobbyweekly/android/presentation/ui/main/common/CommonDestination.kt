package kr.hobbly.hobbyweekly.android.presentation.ui.main.common

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery.galleryDestination

fun NavGraphBuilder.commonDestination(
    navController: NavController
) {
    galleryDestination(navController)
}