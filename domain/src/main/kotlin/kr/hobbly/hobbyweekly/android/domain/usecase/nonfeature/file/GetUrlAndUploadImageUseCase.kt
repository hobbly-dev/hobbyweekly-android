package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.file

import javax.inject.Inject

class GetUrlAndUploadImageUseCase @Inject constructor(
    private val getPreSignedUrlListUseCase: GetPreSignedUrlListUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) {
    suspend operator fun invoke(
        imageUri: List<String>
    ): Result<List<String>> {
        return getPreSignedUrlListUseCase(
            count = imageUri.size
        ).map { preSignedUrlList ->
            preSignedUrlList.mapIndexed { index, preSignedUrl ->
                uploadImageUseCase(
                    preSignedUrl = preSignedUrl.preSignedUrl,
                    imageUri = imageUri.getOrNull(index).orEmpty()
                ).getOrThrow()

                preSignedUrl.uploadFileUrl
            }
        }
    }
}
