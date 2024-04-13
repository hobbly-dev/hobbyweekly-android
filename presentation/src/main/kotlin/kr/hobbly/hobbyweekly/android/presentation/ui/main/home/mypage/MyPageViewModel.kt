package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.zip
import kr.hobbly.hobbyweekly.android.domain.model.feature.community.Block
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.community.block.GetMyBlockListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.feature.routine.GetRoutineStatisticsListUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication.LogoutUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication.WithdrawUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.EditProfileWithUploadUseCase
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user.GetProfileUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel
import kr.hobbly.hobbyweekly.android.presentation.common.base.ErrorEvent
import kr.hobbly.hobbyweekly.android.presentation.model.gallery.GalleryImage

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProfileUseCase: GetProfileUseCase,
    private val getMyBlockListUseCase: GetMyBlockListUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
    private val editProfileWithUploadUseCase: EditProfileWithUploadUseCase,
    private val getRoutineStatisticsListUseCase: GetRoutineStatisticsListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageState> = MutableStateFlow(MyPageState.Init)
    val state: StateFlow<MyPageState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyPageEvent> = MutableEventFlow()
    val event: EventFlow<MyPageEvent> = _event.asEventFlow()

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(Profile.empty)
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    private val _myBlockList: MutableStateFlow<List<Block>> = MutableStateFlow(emptyList())
    val myBlockList: StateFlow<List<Block>> = _myBlockList.asStateFlow()

    private val _routineStatistics: MutableStateFlow<List<RoutineStatistics>> =
        MutableStateFlow(emptyList())
    val routineStatisticsList: StateFlow<List<RoutineStatistics>> = _routineStatistics.asStateFlow()

    init {
        refresh()
    }

    fun onIntent(intent: MyPageIntent) {
        when (intent) {
            is MyPageIntent.OnProfileImageSet -> {
                setProfileImage(intent.image)
            }

            is MyPageIntent.OnDateChanged -> {
                refreshStatistics(intent.date)
            }

            MyPageIntent.Logout -> logout()
            MyPageIntent.Withdraw -> withdraw()
        }
    }

    private fun refreshStatistics(
        date: LocalDate
    ) {
        launch {
            getRoutineStatisticsListUseCase(
                id = -1,
                date = date
            ).onSuccess { routineStatistics ->
                _routineStatistics.value = routineStatistics
            }.onFailure { exception ->
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

    private fun setProfileImage(
        image: GalleryImage
    ) {
        launch {
            _state.value = MyPageState.Loading
            editProfileWithUploadUseCase(
                nickname = profile.value.nickname,
                imageUri = image.filePath
            ).onSuccess {
                _state.value = MyPageState.Init
                refresh()
            }.onFailure { exception ->
                _state.value = MyPageState.Init
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

    private fun logout() {
        launch {
            _state.value = MyPageState.Loading
            logoutUseCase().onSuccess {
                _state.value = MyPageState.Init
                _event.emit(MyPageEvent.Logout.Success)
            }.onFailure { exception ->
                _state.value = MyPageState.Init
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

    private fun withdraw() {
        launch {
            _state.value = MyPageState.Loading
            withdrawUseCase().onSuccess {
                _state.value = MyPageState.Init
                _event.emit(MyPageEvent.Withdraw.Success)
            }.onFailure { exception ->
                _state.value = MyPageState.Init
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

    private fun refresh() {
        launch {
            _state.value = MyPageState.Loading
            zip(
                { getProfileUseCase() },
                { getMyBlockListUseCase() }
            ).onSuccess { (profile, myBlockList) ->
                _state.value = MyPageState.Init
                _profile.value = profile
                _myBlockList.value = myBlockList
            }.onFailure { exception ->
                _state.value = MyPageState.Init
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
