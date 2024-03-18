package kr.hobbly.hobbyweekly.android.domain.repository.nonfeature

import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term

interface UserRepository {
    suspend fun getTermList(): Result<List<Term>>

    suspend fun agreeTermList(
        termList: List<Long>
    ): Result<Unit>

    suspend fun getAgreedTermList(): Result<List<Long>>

    suspend fun setHobbyList(
        hobbyList: List<String>
    ): Result<Unit>

    suspend fun editProfile(
        nickname: String,
        image: String
    ): Result<Unit>

    suspend fun getProfile(): Result<Profile>
}
