package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.mypage.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.RoutineStatistics
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral050
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space40

@Composable
fun MyPageStatisticsScreenStatistics(
    modifier: Modifier = Modifier,
    dataList: List<RoutineStatistics>
) {
    val totalCount = dataList.sumOf { it.totalCount }
    val completedCount = dataList.sumOf { it.completedCount }
    val completedPercent = if (completedCount == 0) {
        1f
    } else {
        completedCount.toFloat() / totalCount
    }

    Canvas(
        modifier = modifier
    ) {
        drawRoundRect(
            brush = SolidColor(Neutral050),
            topLeft = Offset(
                x = 0f,
                y = 0f
            ),
            size = size,
            cornerRadius = CornerRadius(10.dp.toPx())
        )

        dataList.reversed().forEachIndexed { index, data ->
            val width = size.width * dataList.take(dataList.size - index)
                .sumOf { it.completedCount } / totalCount
            val seed = data.title.sumOf { it.code }
            val color = Color(0xFF000000 or LongRange(0x000000, 0xFFFFFF).random(Random(seed)))
            drawRoundRect(
                brush = SolidColor(color),
                topLeft = Offset(
                    x = 0f,
                    y = 0f
                ),
                size = Size(
                    width = width,
                    height = size.height
                ),
                cornerRadius = CornerRadius(10.dp.toPx())
            )
        }
    }
}

@Preview
@Composable
private fun MyPageStatisticsScreenStatisticsPreview1() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Space40)
            .background(Color.White)
    ) {
        MyPageStatisticsScreenStatistics(
            modifier = Modifier.fillMaxSize(),
            dataList = listOf(
                RoutineStatistics(
                    id = 1L,
                    blockName = "독서 블록",
                    thumbnail = "https://via.placeholder.com/150",
                    title = "해리포터 원문보기",
                    totalCount = 5,
                    completedCount = 5
                ),
                RoutineStatistics(
                    id = 2L,
                    blockName = "영어 블록",
                    thumbnail = "https://via.placeholder.com/150",
                    title = "영화자막 번역하기",
                    totalCount = 4,
                    completedCount = 2
                ),
                RoutineStatistics(
                    id = 3L,
                    blockName = "공부 블록",
                    thumbnail = "https://via.placeholder.com/150",
                    title = "해외 친구들 만나기",
                    totalCount = 4,
                    completedCount = 1
                )
            )
        )
    }
}

@Preview
@Composable
private fun MyPageStatisticsScreenStatisticsPreview2() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Space40)
            .background(Color.White)
    ) {
        MyPageStatisticsScreenStatistics(
            modifier = Modifier.fillMaxSize(),
            dataList = emptyList()
        )
    }
}
