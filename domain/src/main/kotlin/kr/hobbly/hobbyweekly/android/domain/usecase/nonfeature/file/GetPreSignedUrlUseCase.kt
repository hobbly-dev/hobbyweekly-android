package kr.hobbly.hobbyweekly.android.domain.usecase.nonfeature.file

import javax.inject.Inject
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.file.PreSignedUrl
import kr.hobbly.hobbyweekly.android.domain.repository.nonfeature.FileRepository

class GetPreSignedUrlListUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(
        count: Int
    ): Result<List<PreSignedUrl>> {
        return fileRepository.getPreSignedUrlList(
            count = count
        )
    }
}
