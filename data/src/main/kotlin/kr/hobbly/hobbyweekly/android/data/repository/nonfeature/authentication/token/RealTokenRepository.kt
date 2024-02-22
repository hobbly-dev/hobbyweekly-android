package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.authentication.token

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.EventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.asEventFlow
import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.TokenApi
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.authentication.JwtToken
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.ServerException
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.TokenRepository

class RealTokenRepository @Inject constructor(
    private val tokenApi: TokenApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : TokenRepository {

    override var refreshToken: String
        set(value) = sharedPreferencesManager.setString(REFRESH_TOKEN, value)
        get() = sharedPreferencesManager.getString(REFRESH_TOKEN, "")
    override var accessToken: String
        set(value) = sharedPreferencesManager.setString(ACCESS_TOKEN, value)
        get() = sharedPreferencesManager.getString(ACCESS_TOKEN, "")

    private val _refreshFailEvent: MutableEventFlow<Unit> = MutableEventFlow()
    override val refreshFailEvent: EventFlow<Unit> = _refreshFailEvent.asEventFlow()

    override suspend fun refreshToken(
        refreshToken: String
    ): Result<JwtToken> {
        return if (refreshToken.isEmpty()) {
            // TODO : 적절한 Exception 이름
            Result.failure(ServerException("Client Error", "refreshToken is empty."))
        } else {
            tokenApi.getAccessToken(
                refreshToken = refreshToken
            ).onSuccess { token ->
                this.refreshToken = token.refreshToken
                this.accessToken = token.accessToken
            }.onFailure { exception ->
                this.refreshToken = ""
                this.accessToken = ""
                _refreshFailEvent.emit(Unit)
            }.map { token ->
                JwtToken(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken
                )
            }
        }
    }

    companion object {
        private const val REFRESH_TOKEN = "refresh_token"
        private const val ACCESS_TOKEN = "access_token"
    }
}
