package kr.hobbly.hobbyweekly.android.domain.repository.nonfeature

import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.file.PreSignedUrl

interface FileRepository {
    suspend fun getPreSignedUrlList(
        count: Int
    ): Result<List<PreSignedUrl>>

    suspend fun upload(
        preSignedUrl: String,
        imageUri: String
    ): Result<Unit>
}
