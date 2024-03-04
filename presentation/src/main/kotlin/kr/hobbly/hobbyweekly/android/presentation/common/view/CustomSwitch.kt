package kr.hobbly.hobbyweekly.android.presentation.common.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral100
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral200
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space16
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space36
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    isEnabled: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit),
    color: Color
) {
    val weight by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "weight"
    )

    val thumbColor by animateColorAsState(
        targetValue = if (!isEnabled) Neutral400 else if (isChecked) color else Neutral200,
        label = "color"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (!isEnabled) Neutral100 else White,
        label = "color"
    )

    Row(
        modifier = modifier
            // TODO : default size 로 변경
            .size(width = Space36, height = Space16)
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor),
    ) {
        if (weight > 0) {
            Spacer(modifier = Modifier.weight(weight))
        }
        if (isEnabled) {
            RippleBox(
                modifier = Modifier.padding(2.dp),
                onClick = {
                    onCheckedChange(!isChecked)
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(thumbColor)
                )
            }
        } else {
            Box(
                modifier = Modifier.padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(thumbColor)
                )
            }
        }
        if (1f - weight > 0) {
            Spacer(modifier = Modifier.weight(1f - weight))
        }
    }
}

@Preview
@Composable
private fun CustomSwitchPreview1() {
    var isChecked by remember { mutableStateOf(true) }

    CustomSwitch(
        isChecked = isChecked,
        onCheckedChange = { isChecked = it },
        color = Color.Red
    )
}

@Preview
@Composable
private fun CustomSwitchPreview2() {
    var isChecked by remember { mutableStateOf(true) }

    CustomSwitch(
        modifier = Modifier,
        isChecked = isChecked,
        isEnabled = false,
        onCheckedChange = { isChecked = it },
        color = Color.Red
    )
}
