package kr.hobbly.hobbyweekly.android.domain.model.feature.mypage

data class RoutineStatistics(
    val achievementRageList: List<Float>
) {
    companion object {
        val empty = RoutineStatistics(
            achievementRageList = emptyList()
        )
    }
}
