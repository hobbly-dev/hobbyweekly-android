package kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error

class BadRequestServerException(
    override val id: String,
    override val message: String
) : ServerException(id, message)
