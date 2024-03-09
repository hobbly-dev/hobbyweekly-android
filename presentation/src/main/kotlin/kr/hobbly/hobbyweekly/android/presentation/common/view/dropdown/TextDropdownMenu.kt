package kr.hobbly.hobbyweekly.android.presentation.common.view.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White

@Composable
fun <T> TextDropdownMenu(
    items: List<T>,
    label: (T) -> String = { it.toString() },
    isExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (T) -> Unit,
) {
    val localConfiguration = LocalConfiguration.current

    DropdownMenu(
        modifier = Modifier
            .requiredSizeIn(maxHeight = localConfiguration.screenHeightDp.dp - Space80)
            .width(localConfiguration.screenWidthDp.dp * 2 / 5)
            .background(White),
        expanded = isExpanded,
        onDismissRequest = onDismissRequest,
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = label(item),
                        style = TitleMedium.merge(Neutral900)
                    )
                },
                onClick = {
                    onClick(item)
                    onDismissRequest()
                },
                contentPadding = PaddingValues(0.dp)
            )
        }
    }
}
