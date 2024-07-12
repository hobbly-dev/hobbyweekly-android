package kr.hobbly.hobbyweekly.android.presentation.ui.main.common.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.max
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryFolder
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val galleryCursor: GalleryCursor
) : BaseViewModel() {

    private val _state: MutableStateFlow<GalleryState> = MutableStateFlow(GalleryState.Init)
    val state: StateFlow<GalleryState> = _state.asStateFlow()

    private val _event: MutableEventFlow<GalleryEvent> = MutableEventFlow()
    val event: EventFlow<GalleryEvent> = _event.asEventFlow()

    private val _galleryImageList = MutableStateFlow<PagingData<GalleryImage>>(PagingData.empty())
    val galleryImageList: StateFlow<PagingData<GalleryImage>> = _galleryImageList.asStateFlow()

    private val _folderList: MutableStateFlow<List<GalleryFolder>> =
        MutableStateFlow(listOf(GalleryFolder.recent))
    val folderList: StateFlow<List<GalleryFolder>> = _folderList.asStateFlow()

    val selectedImageUriList: List<String> by lazy {
        savedStateHandle.get<Array<String>>(GalleryConstant.ROUTE_ARGUMENT_IMAGE_URI_LIST)
            ?.toList()
            .orEmpty()
    }

    val selectRange: IntRange by lazy {
        val min = max(
            savedStateHandle.get<Int>(GalleryConstant.ROUTE_ARGUMENT_MIN_SELECT_COUNT) ?: 1,
            1
        )
        val max = max(
            savedStateHandle.get<Int>(GalleryConstant.ROUTE_ARGUMENT_MAX_SELECT_COUNT) ?: 1,
            min
        )
        min..max
    }

    fun onIntent(intent: GalleryIntent) {
        when (intent) {
            is GalleryIntent.OnGrantPermission -> {
                _folderList.value = galleryCursor.getFolderList()
                getGalleryPagingImages(GalleryFolder.recent)
            }

            is GalleryIntent.OnChangeFolder -> {
                getGalleryPagingImages(intent.folder)
            }
        }
    }

    private fun getGalleryPagingImages(
        folder: GalleryFolder
    ) {
        launch {
            Pager(
                config = PagingConfig(
                    pageSize = GalleryPagingSource.PAGING_SIZE,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = {
                    GalleryPagingSource(
                        galleryCursor = galleryCursor,
                        currentLocation = folder.location,
                    )
                },
            ).flow
                .cachedIn(viewModelScope)
                .collect {
                    _galleryImageList.value = it
                }
        }
    }
}
