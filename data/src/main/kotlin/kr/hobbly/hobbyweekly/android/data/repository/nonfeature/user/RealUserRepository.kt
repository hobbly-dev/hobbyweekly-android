package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.hobbly.hobbyweekly.android.data.common.DEFAULT_PAGING_SIZE
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.NotificationApi
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.TermApi
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.UserApi
import kr.hobbly.hobbyweekly.android.data.remote.network.util.toDomain
import kr.hobbly.hobbyweekly.android.data.repository.nonfeature.user.paging.GetNotificationPagingSource
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.notification.Notification
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Profile
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class RealUserRepository @Inject constructor(
    private val userApi: UserApi,
    private val termApi: TermApi,
    private val notificationApi: NotificationApi
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

    override suspend fun getNotificationPaging(): Flow<PagingData<Notification>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                GetNotificationPagingSource(
                    notificationApi = notificationApi
                )
            },
        ).flow
    }

    override suspend fun checkNotification(
        id: Long
    ): Result<Unit> {
        return notificationApi.checkNotification(
            id = id
        )
    }
}
