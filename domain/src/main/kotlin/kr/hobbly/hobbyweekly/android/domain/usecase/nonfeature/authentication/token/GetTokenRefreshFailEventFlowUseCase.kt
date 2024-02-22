package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication.token

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository

class GetTokenRefreshFailEventFlowUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): EventFlow<Unit> {
        return tokenRepository.refreshFailEvent
    }
}
