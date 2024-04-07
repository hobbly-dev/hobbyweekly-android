package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.edit

import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.RoutineConstant

object RoutineEditConstant {
    const val ROUTE: String = "${RoutineConstant.ROUTE}/edit"

    const val ROUTE_ARGUMENT_ROUTINE_ID = "routine_id"
    const val ROUTE_ARGUMENT_BLOCK_ID = "block_id"
    const val ROUTE_STRUCTURE = ROUTE +
            "?${ROUTE_ARGUMENT_ROUTINE_ID}={${ROUTE_ARGUMENT_ROUTINE_ID}}" +
            "&${ROUTE_ARGUMENT_BLOCK_ID}={${ROUTE_ARGUMENT_BLOCK_ID}}"
}
