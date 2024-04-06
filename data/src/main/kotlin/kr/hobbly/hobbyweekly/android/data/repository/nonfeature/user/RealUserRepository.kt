package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.TermApi
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.UserApi
import kr.hobbly.hobbyweekly.android.data.remote.network.util.toDomain
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class RealUserRepository @Inject constructor(
    private val userApi: UserApi,
    private val termApi: TermApi
) : UserRepository {
    override suspend fun getTermList(): Result<List<Term>> {
        return termApi.getTermList().toDomain()
    }

    override suspend fun agreeTermList(
        termList: List<Long>
    ): Result<Unit> {
        return termApi.agreeTermList(
            termList = termList
        )
    }

    override suspend fun getAgreedTermList(): Result<List<Long>> {
        return termApi.getTermListAgreeState().toDomain()
    }

    override suspend fun setHobbyList(
        hobbyList: List<String>
    ): Result<Unit> {
        return userApi.setHobbyList(
            hobbyList = hobbyList
        )
    }

    override suspend fun editProfile(
        nickname: String,
        image: String
    ): Result<Unit> {
        return userApi.editProfile(
            nickname = nickname,
            image = image
        )
    }

    override suspend fun getProfile(): Result<Profile> {
        return userApi.getProfile().toDomain()
    }
}
