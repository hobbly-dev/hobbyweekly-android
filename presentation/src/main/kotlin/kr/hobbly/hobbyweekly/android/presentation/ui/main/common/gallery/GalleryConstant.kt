package kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery

object GalleryConstant {
    const val ROUTE = "gallery"

    const val ROUTE_ARGUMENT_IMAGE_URI_LIST = "image_uri_list"
    const val ROUTE_ARGUMENT_MIN_SELECT_COUNT = "min_select_count"
    const val ROUTE_ARGUMENT_MAX_SELECT_COUNT = "max_select_count"
    const val ROUTE_STRUCTURE = ROUTE +
            "?$ROUTE_ARGUMENT_IMAGE_URI_LIST={$ROUTE_ARGUMENT_IMAGE_URI_LIST}" +
            "&$ROUTE_ARGUMENT_MIN_SELECT_COUNT={$ROUTE_ARGUMENT_MIN_SELECT_COUNT}" +
            "&$ROUTE_ARGUMENT_MAX_SELECT_COUNT={$ROUTE_ARGUMENT_MAX_SELECT_COUNT}"

    const val RESULT_IMAGE_URI_LIST = "result_image_uri_list"
}
