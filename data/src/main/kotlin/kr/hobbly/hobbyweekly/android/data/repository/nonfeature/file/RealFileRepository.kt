package kr.hobbly.hobbyweekly.android.data.repository.nonfeature.file

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager
import kr.hobbly.hobbyweekly.android.data.remote.network.api.nonfeature.FileApi
import kr.hobbly.hobbyweekly.android.data.remote.network.util.toDomain
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.file.PreSignedUrl
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.FileRepository

class RealFileRepository @Inject constructor(
    private val fileApi: FileApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : FileRepository {
    override suspend fun getPreSignedUrlList(
        count: Int
    ): Result<List<PreSignedUrl>> {
        return fileApi.getPreSignedUrlList(
            count = count
        ).toDomain()
    }

    override suspend fun upload(
        preSignedUrl: String,
        imageUri: String
    ): Result<Unit> {
        return fileApi.upload(
            preSignedUrl = preSignedUrl,
            imageUri = imageUri
        )
    }
}
