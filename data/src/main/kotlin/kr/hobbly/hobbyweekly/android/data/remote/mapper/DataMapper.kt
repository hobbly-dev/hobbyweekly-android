package kr.hobbly.hobbyweekly.android.data.remote.mapper

interface DataMapper<D> {
    fun toDomain(): D
}
