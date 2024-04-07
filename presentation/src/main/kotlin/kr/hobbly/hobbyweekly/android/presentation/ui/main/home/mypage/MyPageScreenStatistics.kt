package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleMedium
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.measureTextHeight
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.measureTextWidth

@Composable
fun MyPageScreenStatistics(
    modifier: Modifier = Modifier,
    dataList: List<RoutineStatistics>,
    @FloatRange(from = 0.0, to = 1.0) thickness: Float
) {
    val totalCount = dataList.sumOf { it.totalCount }
    val completedCount = dataList.sumOf { it.completedCount }
    val completedPercent = if (completedCount == 0) {
        0f
    } else {
        completedCount.toFloat() / totalCount
    }
    val text = String.format("%.1f%%", completedPercent * 100)
    val textMeasurer = rememberTextMeasurer()
    val textWidth = measureTextWidth(text = text, style = TitleMedium)
    val textHeight = measureTextHeight(text = text, style = TitleMedium)

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
                .align(Alignment.Center)
        ) {
            val fixedThickness = size.width / 2 * thickness
            drawArc(
                color = Neutral050,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(
                    x = fixedThickness / 2,
                    y = fixedThickness / 2
                ),
                size = Size(
                    size.width - fixedThickness,
                    size.height - fixedThickness
                ),
                style = Stroke(
                    width = fixedThickness
                )
            )
            dataList.forEachIndexed { index, data ->
                val startAngle =
                    -90f + dataList.take(index).sumOf { it.completedCount } * 360f / totalCount
                val angle = data.completedCount * 360f / totalCount
                val seed = data.title.sumOf { it.code }
                val color = Color(0xFF000000 or LongRange(0x000000, 0xFFFFFF).random(Random(seed)))
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = angle,
                    useCenter = false,
                    topLeft = Offset(
                        x = fixedThickness / 2,
                        y = fixedThickness / 2
                    ),
                    size = Size(
                        size.width - fixedThickness,
                        size.height - fixedThickness
                    ),
                    style = Stroke(
                        width = fixedThickness
                    )
                )
            }
            drawText(
                textMeasurer = textMeasurer,
                text = text,
                topLeft = Offset(
                    x = size.width / 2 - textWidth.toPx() / 2,
                    y = size.height / 2 - textHeight.toPx() / 2
                ),
                style = TitleMedium
            )
        }
    }
}

@Preview
@Composable
private fun MyPageScreenStatisticsPreview1() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MyPageScreenStatistics(
            modifier = Modifier
                .fillMaxSize(),
            dataList = listOf(
                RoutineStatistics(
                    title = "해리포터 원문보기",
                    totalCount = 5,
                    completedCount = 5
                ),
                RoutineStatistics(
                    title = "영화자막 번역하기",
                    totalCount = 4,
                    completedCount = 2
                ),
                RoutineStatistics(
                    title = "해외 친구들 만나기",
                    totalCount = 4,
                    completedCount = 1
                )
            ),
            thickness = 0.5f
        )
    }
}

@Preview
@Composable
private fun MyPageScreenStatisticsPreview2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MyPageScreenStatistics(
            modifier = Modifier
                .fillMaxSize(),
            dataList = emptyList(),
            thickness = 0.5f
        )
    }
}
