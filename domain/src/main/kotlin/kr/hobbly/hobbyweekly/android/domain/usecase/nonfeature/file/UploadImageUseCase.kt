package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.file

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.FileRepository

class UploadImageUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(
        preSignedUrl: String,
        imageUri: String
    ): Result<Unit> {
        return fileRepository.upload(
            preSignedUrl = preSignedUrl,
            imageUri = imageUri
        )
    }
}
