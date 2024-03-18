package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.profile

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.EditProfileUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.EditProfileWithUploadUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@HiltViewModel
class RegisterProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val editProfileWithUploadUseCase: EditProfileWithUploadUseCase,
    private val editProfileUseCase: EditProfileUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegisterProfileState> =
        MutableStateFlow(RegisterProfileState.Init)
    val state: StateFlow<RegisterProfileState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegisterProfileEvent> = MutableEventFlow()
    val event: EventFlow<RegisterProfileEvent> = _event.asEventFlow()

    fun onIntent(intent: RegisterProfileIntent) {
        when (intent) {
            is RegisterProfileIntent.OnConfirm -> {
                setProfile(
                    nickname = intent.nickname,
                    image = intent.image
                )
            }
        }
    }

    private fun setProfile(
        nickname: String,
        image: GalleryImage?
    ) {
        launch {
            _state.value = RegisterProfileState.Loading
            if (image == null) {
                editProfileUseCase(
                    nickname = nickname,
                    image = ""
                ).onSuccess {
                    _state.value = RegisterProfileState.Init
                    _event.emit(RegisterProfileEvent.SetProfile.Success)
                }.onFailure { exception ->
                    _state.value = RegisterProfileState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
            } else {
                editProfileWithUploadUseCase(
                    nickname = nickname,
                    imageUri = image.filePath
                ).onSuccess {
                    _state.value = RegisterProfileState.Init
                    _event.emit(RegisterProfileEvent.SetProfile.Success)
                }.onFailure { exception ->
                    _state.value = RegisterProfileState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
            }
        }
    }
}
