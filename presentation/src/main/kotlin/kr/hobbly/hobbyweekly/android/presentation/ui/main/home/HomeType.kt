package kr.hobbly.hobbyweekly.android.presentation.ui.main.home

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.community.CommunityConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.MyPageConstant
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine.RoutineConstant

@Parcelize
sealed class HomeType(
    val route: String,
    @DrawableRes val iconRes: Int
) : Parcelable {

    @Parcelize
    data object Routine : HomeType(
        route = RoutineConstant.ROUTE,
        iconRes = R.drawable.img_routine
    )

    @Parcelize
    data object Community : HomeType(
        route = CommunityConstant.ROUTE,
        iconRes = R.drawable.img_community
    )

    @Parcelize
    data object MyPage : HomeType(
        route = MyPageConstant.ROUTE,
        iconRes = R.drawable.img_setting
    )

    companion object {
        fun values(): List<HomeType> {
            return listOf(Routine, Community, MyPage)
        }
    }
}
