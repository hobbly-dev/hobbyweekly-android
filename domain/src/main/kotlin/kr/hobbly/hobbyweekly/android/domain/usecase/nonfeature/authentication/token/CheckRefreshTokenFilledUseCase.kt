package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.authentication.token

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository

class CheckRefreshTokenFilledUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): Boolean {
        return tokenRepository.refreshToken.isNotEmpty()
    }
}
