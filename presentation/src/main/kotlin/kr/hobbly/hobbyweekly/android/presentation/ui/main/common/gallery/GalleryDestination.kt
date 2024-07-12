package kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.ErrorObserver

fun NavGraphBuilder.galleryDestination(
    navController: NavController
) {
    composable(
        route = GalleryConstant.ROUTE_STRUCTURE,
        arguments = listOf(
            navArgument(GalleryConstant.ROUTE_ARGUMENT_IMAGE_URI_LIST) {
                type = NavType.StringArrayType
                defaultValue = emptyArray<String>()
            },
            navArgument(GalleryConstant.ROUTE_ARGUMENT_MIN_SELECT_COUNT) {
                type = NavType.IntType
                defaultValue = 1
            },
            navArgument(GalleryConstant.ROUTE_ARGUMENT_MAX_SELECT_COUNT) {
                type = NavType.IntType
                defaultValue = 1
            }
        )
    ) {
        val viewModel: GalleryViewModel = hiltViewModel()

        val argument: GalleryArgument = Unit.let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            GalleryArgument(
                state = state,
                event = viewModel.event,
                intent = viewModel::onIntent,
                logEvent = viewModel::logEvent,
                handler = viewModel.handler
            )
        }

        val data: GalleryData = Unit.let {
            val folderList by viewModel.folderList.collectAsStateWithLifecycle()
            val galleryImageList = viewModel.galleryImageList.collectAsLazyPagingItems()
            val selectedImageUriList = viewModel.selectedImageUriList
            val selectRange = viewModel.selectRange

            GalleryData(
                folderList = folderList,
                galleryImageList = galleryImageList,
                selectedImageUriList = selectedImageUriList,
                selectRange = selectRange
            )
        }

        ErrorObserver(viewModel)
        GalleryScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}
