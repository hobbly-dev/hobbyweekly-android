package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kr.hobbly.hobbyweekly.android.domain.model.feature.mypage.RoutineStatistics
import kr.hobbly.hobbyweekly.android.presentation.common.theme.BodySemiBold
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.measureTextHeight
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.measureTextWidth

@Composable
fun MyPageScreenStatistics(
    modifier: Modifier,
    routineStatistics: RoutineStatistics,
    thickness: Float = 0.5f
) {
    // TODO : Block 모양으로 변경

    val dataList = routineStatistics.achievementRageList

    val bottomMeasuredList: List<Pair<Dp, Dp>> = List(dataList.size) { index ->
        val bottomTextWidth = measureTextWidth(text = "${index + 1}주", style = BodySemiBold)
        val bottomTextHeight = measureTextHeight(text = "${index + 1}주", style = BodySemiBold)
        Pair(bottomTextWidth, bottomTextHeight)
    }

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
    ) {
        if (dataList.isNotEmpty()) {
            val fixedThickness = size.width / dataList.size * thickness

            val topHeight = 0
            val bottomHeight = bottomMeasuredList.maxOf { it.second }.toPx() + 24.dp.toPx()

            dataList.forEachIndexed { index, data ->
                val backgroundHeight = (size.height - topHeight - bottomHeight)
                val height = backgroundHeight * data
                val centerX = size.width / dataList.size * (index + 0.5f)
                val formattedText = "${index + 1}주"

                drawRoundRect(
                    brush = SolidColor(Neutral100),
                    topLeft = Offset(
                        x = centerX - fixedThickness / 2,
                        y = size.height - bottomHeight - backgroundHeight
                    ),
                    size = Size(
                        width = fixedThickness,
                        height = backgroundHeight
                    ),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
                drawRoundRect(
                    brush = SolidColor(Neutral100),
                    topLeft = Offset(
                        x = centerX - fixedThickness / 2,
                        y = size.height - bottomHeight - backgroundHeight / 2
                    ),
                    size = Size(
                        width = fixedThickness,
                        height = backgroundHeight / 2
                    )
                )
                drawRoundRect(
                    brush = SolidColor(Red),
                    topLeft = Offset(
                        x = centerX - fixedThickness / 2,
                        y = size.height - bottomHeight - height
                    ),
                    size = Size(
                        width = fixedThickness,
                        height = height
                    ),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
                drawRoundRect(
                    brush = SolidColor(Red),
                    topLeft = Offset(
                        x = centerX - fixedThickness / 2,
                        y = size.height - bottomHeight - height / 2
                    ),
                    size = Size(
                        width = fixedThickness,
                        height = height / 2
                    )
                )
                drawText(
                    textMeasurer = textMeasurer,
                    text = formattedText,
                    topLeft = Offset(
                        x = centerX - bottomMeasuredList[index].first.toPx() / 2,
                        y = size.height - 10.dp.toPx() - bottomMeasuredList[index].second.toPx()
                    ),
                    style = BodySemiBold.merge(Neutral400)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MyPageScreenStatisticsPreview() {
    val isConfirmedDayOfWeek = remember { mutableStateListOf<Int>() }

    MyPageScreenStatistics(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        routineStatistics = RoutineStatistics(
            achievementRageList = List(4) {
                IntRange(0, 10).random() / 10f
            }
        )
    )
}
