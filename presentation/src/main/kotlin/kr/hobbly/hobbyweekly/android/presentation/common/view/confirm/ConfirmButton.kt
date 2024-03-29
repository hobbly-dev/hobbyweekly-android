package kr.hobbly.hobbyweekly.android.presentation.common.view.confirm

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.hobbly.hobbyweekly.android.presentation.common.theme.HobbyWeeklyTheme
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral300
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral700
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral800
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Pink
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White

@Composable
fun ConfirmButton(
    modifier: Modifier = Modifier,
    properties: ConfirmButtonProperties,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable RowScope.(TextStyle) -> Unit
) {
    val textStyle = when (properties.size) {
        ConfirmButtonSize.Xlarge -> TitleMedium
        ConfirmButtonSize.Large -> TitleMedium
        ConfirmButtonSize.Medium -> TitleMedium
        ConfirmButtonSize.Small -> TitleMedium
    }

    val backgroundColor = when (properties.type) {
        ConfirmButtonType.Primary -> if (isEnabled) Red else Neutral400
        ConfirmButtonType.Secondary -> Neutral300
        ConfirmButtonType.Tertiary -> Pink
        ConfirmButtonType.Outline -> White
    }

    val textColor = when (properties.type) {
        ConfirmButtonType.Primary -> White
        ConfirmButtonType.Secondary -> Neutral700
        ConfirmButtonType.Tertiary -> Red
        ConfirmButtonType.Outline -> Neutral800
    }

    val border = when (properties.type) {
        ConfirmButtonType.Primary -> null
        ConfirmButtonType.Secondary -> null
        ConfirmButtonType.Tertiary -> null
        ConfirmButtonType.Outline -> BorderStroke(1.dp, Neutral800)
    }

    val height = when (properties.size) {
        ConfirmButtonSize.Xlarge -> 52.dp
        ConfirmButtonSize.Large -> 48.dp
        ConfirmButtonSize.Medium -> 34.dp
        ConfirmButtonSize.Small -> 28.dp
    }

    val paddingHorizontal = when (properties.size) {
        ConfirmButtonSize.Xlarge -> 16.dp
        ConfirmButtonSize.Large -> 16.dp
        ConfirmButtonSize.Medium -> 8.dp
        ConfirmButtonSize.Small -> 8.dp
    }

    Button(
        modifier = modifier.height(height),
        shape = MaterialTheme.shapes.large,
        contentPadding = PaddingValues(
            start = paddingHorizontal,
            top = 0.dp,
            bottom = 0.dp,
            end = paddingHorizontal
        ),
        colors = ButtonDefaults
            .buttonColors(
                containerColor = backgroundColor
            ),
        border = border,
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        onClick = onClick,
        enabled = isEnabled,
    ) {
        content(textStyle.merge(color = textColor))
    }
}

@Preview
@Composable
fun ConfirmButtonPreview1() {
    HobbyWeeklyTheme {
        Column {
            ConfirmButton(
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                modifier = Modifier.fillMaxWidth()
            ) { style ->
                Text(
                    text = "확인",
                    style = style
                )
            }
            ConfirmButton(
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Secondary
                ),
                modifier = Modifier.fillMaxWidth()
            ) { style ->
                Text(
                    text = "확인",
                    style = style
                )
            }
            ConfirmButton(
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Tertiary
                ),
                modifier = Modifier.fillMaxWidth()
            ) { style ->
                Text(
                    text = "확인",
                    style = style
                )
            }
            ConfirmButton(
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Outline
                ),
                modifier = Modifier.fillMaxWidth()
            ) { style ->
                Text(
                    text = "확인",
                    style = style
                )
            }
        }
    }
}

@Preview
@Composable
fun ConfirmButtonPreview2() {
    Column {
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Secondary
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Tertiary
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Outline
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
    }
}

@Preview
@Composable
fun ConfirmButtonPreview3() {
    Column {
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Primary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Secondary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Tertiary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Outline
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
    }
}

@Preview
@Composable
fun ConfirmButtonPreview4() {
    Column {
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Primary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Secondary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Tertiary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Outline
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
    }
}
