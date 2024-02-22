package kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error

class UndefinedKeyException(
    override val message: String
) : Exception(message)
