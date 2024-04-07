package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineExceptionHandler
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@Immutable
data class MyPageArgument(
    val state: MyPageState,
    val event: EventFlow<MyPageEvent>,
    val intent: (MyPageIntent) -> Unit,
    val logEvent: (eventName: String, params: Map<String, Any>) -> Unit,
    val handler: CoroutineExceptionHandler
)

sealed interface MyPageState {
    data object Init : MyPageState
    data object Loading : MyPageState
}


sealed interface MyPageEvent {
    sealed interface Logout : MyPageEvent {
        data object Success : Logout
    }

    sealed interface Withdraw : MyPageEvent {
        data object Success : Withdraw
    }
}

sealed interface MyPageIntent {
    data class OnProfileImageSet(val image: GalleryImage) : MyPageIntent
    data object Logout : MyPageIntent
    data object Withdraw : MyPageIntent
}
