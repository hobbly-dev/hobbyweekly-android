package kr.hobbly.hobbyweekly.android.domain.repository.nonfeature

import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile

interface UserRepository {

    suspend fun getProfile(): Result<Profile>
}
