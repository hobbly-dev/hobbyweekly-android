package kr.hobbly.hobbyweekly.android.presentation.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication.token.GetTokenRefreshFailEventFlowUseCase
import kr.hobbly.hobbyweekly.android.presentation.common.base.BaseViewModel

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTokenRefreshFailEventFlowUseCase: GetTokenRefreshFailEventFlowUseCase
) : BaseViewModel() {

    val refreshFailEvent: EventFlow<Unit> = getTokenRefreshFailEventFlowUseCase()

}
