package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.file

import javax.inject.Inject
import kotlinx.coroutines.delay
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.file.PreSignedUrl
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.FileRepository

class MockFileRepository @Inject constructor() : FileRepository {
    override suspend fun getPreSignedUrlList(
        count: Int
    ): Result<List<PreSignedUrl>> {
        randomShortDelay()
        return Result.success(
            List(count) {
                PreSignedUrl(
                    preSignedUrl = "https://www.naver.com",
                    uploadFileUrl = "https://www.naver.com"
                )
            }
        )
    }

    override suspend fun upload(
        preSignedUrl: String,
        imageUri: String
    ): Result<Unit> {
        randomLongDelay()
        return Result.success(Unit)
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
