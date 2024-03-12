package kr.hobbly.hobbyweekly.android.presentation.ui.main.common.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.datetime.LocalTime
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.HeadlineRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space12
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space20
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space36
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButton
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerScreen(
    localTime: LocalTime = LocalTime(0, 0),
    onDismissRequest: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: (LocalTime) -> Unit,
) {
    val timeState = rememberTimePickerState(
        initialHour = localTime.hour,
        initialMinute = localTime.minute,
        is24Hour = false,
    )

    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(Red)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(Space20),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(Space36),
                            painter = painterResource(R.drawable.ic_time),
                            contentDescription = null,
                            tint = White
                        )
                        Spacer(modifier = Modifier.width(Space12))
                        Text(
                            text = "알림 설정",
                            style = HeadlineRegular.merge(White)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Space20))
                TimeInput(
                    state = timeState,
                )
                Spacer(modifier = Modifier.height(Space20))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Space20, end = Space20, bottom = Space12),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ConfirmButton(
                        properties = ConfirmButtonProperties(
                            size = ConfirmButtonSize.Large,
                            type = ConfirmButtonType.Secondary
                        ),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onCancel()
                            onDismissRequest()
                        }
                    ) { style ->
                        Text(
                            text = "삭제",
                            style = style
                        )
                    }
                    Spacer(modifier = Modifier.width(Space20))
                    ConfirmButton(
                        properties = ConfirmButtonProperties(
                            size = ConfirmButtonSize.Large,
                            type = ConfirmButtonType.Primary
                        ),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onConfirm(LocalTime(timeState.hour, timeState.minute))
                            onDismissRequest()
                        }
                    ) { style ->
                        Text(
                            text = "확인",
                            style = style
                        )
                    }
                }
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun TimePickerScreenPreview() {
    TimePickerScreen(
        localTime = LocalTime(0, 0),
        onDismissRequest = {},
        onCancel = {},
        onConfirm = {}
    )
}
