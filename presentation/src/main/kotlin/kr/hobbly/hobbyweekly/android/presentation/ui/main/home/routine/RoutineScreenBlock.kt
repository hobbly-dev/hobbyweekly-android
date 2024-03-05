package kr.hobbly.hobbyweekly.android.presentation.ui.main.home.routine

import androidx.compose.animation.AnimatedVisibility
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
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Blue
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Green
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Orange
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Pink
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Purple
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Yellow

@Composable
fun RoutineScreenBlock(
    modifier: Modifier,
    isConfirmedDayOfWeek: List<Int>
) {
    val density = LocalDensity.current

    val isMondayVisible: Boolean = isConfirmedDayOfWeek.contains(0)
    val isTuesdayVisible: Boolean = isConfirmedDayOfWeek.contains(1)
    val isWednesdayVisible: Boolean = isConfirmedDayOfWeek.contains(2)
    val isThursdayVisible: Boolean = isConfirmedDayOfWeek.contains(3)
    val isFridayVisible: Boolean = isConfirmedDayOfWeek.contains(4)
    val isSaturdayVisible: Boolean = isConfirmedDayOfWeek.contains(5)
    val isSundayVisible: Boolean = isConfirmedDayOfWeek.contains(6)

    Box(
        modifier = modifier
    ) {
        val blockModifier = Modifier
            .size(35.dp, 45.dp)
            .align(Alignment.Center)
        Icon(
            modifier = blockModifier.offset(),
            painter = painterResource(R.drawable.ic_block),
            contentDescription = null,
            tint = Neutral100
        )
        Icon(
            modifier = blockModifier.offset(x = (-27).dp, y = (-35).dp),
            painter = painterResource(R.drawable.ic_block),
            contentDescription = null,
            tint = Neutral100
        )
        Icon(
            modifier = blockModifier.offset(x = (27).dp, y = (-35).dp),
            painter = painterResource(R.drawable.ic_block),
            contentDescription = null,
            tint = Neutral100
        )
        Icon(
            modifier = blockModifier.offset(x = (-27).dp, y = (0).dp),
            painter = painterResource(R.drawable.ic_block),
            contentDescription = null,
            tint = Neutral100
        )
        Icon(
            modifier = blockModifier.offset(x = (27).dp, y = (0).dp),
            painter = painterResource(R.drawable.ic_block),
            contentDescription = null,
            tint = Neutral100
        )
        Icon(
            modifier = blockModifier.offset(x = (-27).dp, y = (35).dp),
            painter = painterResource(R.drawable.ic_block),
            contentDescription = null,
            tint = Neutral100
        )
        Icon(
            modifier = blockModifier.offset(x = (27).dp, y = (35).dp),
            painter = painterResource(R.drawable.ic_block),
            contentDescription = null,
            tint = Neutral100
        )

        AnimatedVisibility(
            modifier = blockModifier.offset(),
            visible = isThursdayVisible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_block),
                contentDescription = null,
                tint = Green
            )
        }
        AnimatedVisibility(
            modifier = blockModifier.offset(x = (-27).dp, y = (-35).dp),
            visible = isMondayVisible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_block),
                contentDescription = null,
                tint = Red
            )
        }
        AnimatedVisibility(
            modifier = blockModifier.offset(x = (27).dp, y = (-35).dp),
            visible = isFridayVisible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_block),
                contentDescription = null,
                tint = Blue
            )
        }
        AnimatedVisibility(
            modifier = blockModifier.offset(x = (-27).dp, y = (0).dp),
            visible = isTuesdayVisible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_block),
                contentDescription = null,
                tint = Orange
            )
        }
        AnimatedVisibility(
            modifier = blockModifier.offset(x = (27).dp, y = (0).dp),
            visible = isSaturdayVisible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_block),
                contentDescription = null,
                tint = Purple
            )
        }
        AnimatedVisibility(
            modifier = blockModifier.offset(x = (-27).dp, y = (35).dp),
            visible = isWednesdayVisible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_block),
                contentDescription = null,
                tint = Yellow
            )
        }
        AnimatedVisibility(
            modifier = blockModifier.offset(x = (27).dp, y = (35).dp),
            visible = isSundayVisible,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_block),
                contentDescription = null,
                tint = Pink
            )
        }
    }
}

@Preview
@Composable
private fun RoutineScreenBlockPreview() {
    val isConfirmedDayOfWeek = remember { mutableStateListOf<Int>() }
    var counter by remember { mutableIntStateOf(0) }

    RoutineScreenBlock(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable {
                if (isConfirmedDayOfWeek.contains(counter)) {
                    isConfirmedDayOfWeek.remove(counter)
                } else {
                    isConfirmedDayOfWeek.add(counter)
                }
                counter = (counter + 1) % 7
            },
        isConfirmedDayOfWeek = isConfirmedDayOfWeek
    )
}
