package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.notification.Notification
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.UserRepository

class GetNotificationPagingUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Notification>> {
        return userRepository.getNotificationPaging()
    }
}
