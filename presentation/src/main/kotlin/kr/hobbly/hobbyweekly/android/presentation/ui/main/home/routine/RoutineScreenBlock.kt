package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.hobbly.hobbyweekly.android.common.util.orZero
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Blue
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Green
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Orange
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Pink
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Purple
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Yellow
import kr.hobbly.hobbyweekly.android.presentation.model.routine.RoutineStatisticsItem
import kr.hobbly.hobbyweekly.android.presentation.model.routine.RoutineStatisticsModel

@Composable
fun RoutineScreenBlock(
    modifier: Modifier,
    routineStatisticsList: List<RoutineStatisticsItem>
) {
    val density = LocalDensity.current

    val modelList: List<RoutineStatisticsModel> = (0..6).map { dayOfWeek ->
        val color = when (dayOfWeek) {
            0 -> Red
            1 -> Orange
            2 -> Yellow
            3 -> Green
            4 -> Blue
            5 -> Purple
            6 -> Pink
            else -> Neutral100
        }.copy(alpha = getAlpha(routineStatisticsList, dayOfWeek))

        val animatedColor by animateColorAsState(
            targetValue = color,
            label = "color state $dayOfWeek"
        )

        RoutineStatisticsModel(
            dayOfWeek = dayOfWeek,
            color = animatedColor,
            x = when (dayOfWeek) {
                0, 1, 2 -> (-27).dp
                3 -> 0.dp
                4, 5, 6 -> 27.dp
                else -> 0.dp
            },
            y = when (dayOfWeek) {
                0, 4 -> (-35).dp
                1, 3, 5 -> 0.dp
                2, 6 -> 35.dp
                else -> 0.dp
            }
        )
    }.sortedWith(
        compareBy(
            { it.y },
            { it.x }
        )
    )

    Box(
        modifier = modifier
    ) {
        val blockModifier = Modifier
            .size(35.dp, 45.dp)
            .align(Alignment.Center)

        modelList.forEach { model ->
            Icon(
                modifier = blockModifier.offset(x = model.x, y = model.y),
                painter = painterResource(R.drawable.ic_block),
                contentDescription = null,
                tint = Neutral100
            )
        }

        modelList.forEach { model ->
            val isVisible = getAlpha(routineStatisticsList, model.dayOfWeek) > 0f
            AnimatedVisibility(
                modifier = blockModifier.offset(x = model.x, y = model.y),
                visible = isVisible,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_block),
                    contentDescription = null,
                    tint = model.color
                )
            }
        }
    }
}

@FloatRange(from = 0.0, to = 1.0)
private fun getAlpha(
    itemList: List<RoutineStatisticsItem>,
    dayOfWeek: Int
): Float {
    return itemList.find { it.dayOfWeek == dayOfWeek }?.let {
        val confirmedRoutineCount = it.confirmedRoutineCount
        val routineCount = it.routineCount

        if (routineCount == 0) {
            0f
        } else {
            (confirmedRoutineCount.toFloat() / routineCount.toFloat())
        }
    }.orZero()
}

@Preview
@Composable
private fun RoutineScreenBlockPreview() {
    val routineStatisticsList = remember { mutableStateListOf<RoutineStatisticsItem>() }
    var counter by remember { mutableIntStateOf(0) }

    RoutineScreenBlock(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable {
                val item = routineStatisticsList.find { it.dayOfWeek == counter }
                if (item != null) {
                    routineStatisticsList.remove(item)
                } else {
                    routineStatisticsList.add(
                        RoutineStatisticsItem(
                            dayOfWeek = counter,
                            routineCount = 1,
                            confirmedRoutineCount = 1
                        )
                    )
                }
                counter = (counter + 1) % 7
            },
        routineStatisticsList = routineStatisticsList
    )
}
