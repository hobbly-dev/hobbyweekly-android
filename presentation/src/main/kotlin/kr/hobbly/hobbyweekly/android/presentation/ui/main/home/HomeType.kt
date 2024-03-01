package kr.hobbly.hobbyweekly.android.presentation.ui.main.home

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.MyPageConstant

@Parcelize
sealed class HomeType(
    val route: String,
    @DrawableRes val iconRes: Int
) : Parcelable {

    @Parcelize
    data object MyPage : HomeType(
        route = MyPageConstant.ROUTE,
        iconRes = R.drawable.img_setting
    )

    companion object {
        fun values(): List<HomeType> {
            return listOf(MyPage)
        }
    }
}
