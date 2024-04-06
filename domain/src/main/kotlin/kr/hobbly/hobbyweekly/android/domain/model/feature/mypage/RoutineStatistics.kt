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

// TODO
//data class RoutineStatistics(
//    val title: String = "",
//    val totalCount: Int,
//    val completedCount: Int
//) {
//    companion object {
//        val empty = RoutineStatistics(
//            title = "",
//            totalCount = 0,
//            completedCount = 0
//        )
//    }
//}
