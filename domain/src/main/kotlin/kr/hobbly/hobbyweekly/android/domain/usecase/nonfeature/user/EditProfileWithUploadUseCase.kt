package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.user

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.file.GetUrlAndUploadImageUseCase

class EditProfileWithUploadUseCase @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val getUrlAndUploadImageUseCase: GetUrlAndUploadImageUseCase
) {
    suspend operator fun invoke(
        nickname: String,
        imageUri: String
    ): Result<Unit> {
        return if (imageUri.isEmpty()) {
            editProfileUseCase(
                nickname = nickname,
                image = ""
            )
        } else {
            getUrlAndUploadImageUseCase(
                imageUriList = listOf(imageUri)
            ).mapCatching { imageList ->
                editProfileUseCase(
                    nickname = nickname,
                    image = imageList.firstOrNull().orEmpty()
                ).getOrThrow()
            }
        }
    }
}
