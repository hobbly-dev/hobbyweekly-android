package kr.hobbly.hobbyweekly.android.presentation.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kr.hobbly.hobbyweekly.android.presentation.R

val Typography = Typography()

val DisplayRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 30.sp
)

val HeadlineRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 28.sp
)

val TitleRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp
)

val TitleMedium = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)

val TitleSemiBoldSmall = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
)

val TitleSemiBold = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp
)

val LabelRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)

val LabelMedium = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp
)

val BodyRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.pretendard)),
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp
)
