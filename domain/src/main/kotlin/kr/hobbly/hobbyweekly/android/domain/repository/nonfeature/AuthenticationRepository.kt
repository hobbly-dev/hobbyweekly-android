package kr.hobbly.hobbyweekly.android.domain.repository.nonfeature

interface AuthenticationRepository {

    suspend fun logout(): Result<Unit>

    suspend fun withdraw(): Result<Unit>
}
