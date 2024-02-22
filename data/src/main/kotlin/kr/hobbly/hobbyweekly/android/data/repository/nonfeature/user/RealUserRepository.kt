package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.UserApi
import kr.hobbly.hobbyweekly.android.data.remote.network.util.toDomain
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class RealUserRepository @Inject constructor(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun getProfile(): Result<Profile> {
        return userApi.getProfile().toDomain()
    }
}
