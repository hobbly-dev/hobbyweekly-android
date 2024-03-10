package kr.hobbly.hobbyweekly.android.presentation.common.view.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Transparent
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Warning
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "",
    isError: Boolean = false,
    isEnabled: Boolean = true,
    maxLines: Int = 1,
    maxTextLength: Int = 100,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextFieldFocusChange: (Boolean) -> Unit = {}
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var isTextFieldFocused by remember { mutableStateOf(false) }

    val currentColor = when {
        isError -> Warning
        isTextFieldFocused -> Neutral900
        else -> Neutral400
    }
    val currentColorState = animateColorAsState(
        targetValue = currentColor,
        label = "color state"
    )

    BasicTextField(
        value = text,
        modifier = modifier.onFocusChanged {
            isTextFieldFocused = it.isFocused
            onTextFieldFocusChange(it.isFocused)
        },
        onValueChange = {
            if (maxTextLength >= it.length) {
                onValueChange(it)
            }
        },
        enabled = isEnabled,
        textStyle = LabelMedium.merge(Neutral900),
        singleLine = maxLines == 1,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(value = currentColorState.value),
        interactionSource = interactionSource,
    ) { textField ->
        TextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = textField,
            enabled = isEnabled,
            singleLine = maxLines == 1,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            placeholder = {
                Text(
                    text = hintText,
                    style = LabelMedium.merge(Neutral400)
                )
            },
            contentPadding = PaddingValues(0.dp),
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
            )
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun EmptyTextField1Preview() {
    EmptyTextField(
        text = "잘못 된 이름",
        hintText = "이름을 입력하세요.",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        isError = true,
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun EmptyTextField2Preview() {
    EmptyTextField(
        text = "이름",
        hintText = "이름을 입력하세요.",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        isError = false,
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun EmptyTextField3Preview() {
    EmptyTextField(
        text = "",
        hintText = "이름을 입력하세요.",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        isError = false
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun EmptyTextField4Preview() {
    EmptyTextField(
        text = "엄청나게 긴 텍스트입니다. 엄청나게 긴 텍스트입니다. 엄청나게 긴 텍스트입니다. 엄청나게 긴 텍스트입니다. 엄청나게 긴 텍스트입니다.",
        modifier = Modifier.heightIn(min = 100.dp),
        hintText = "",
        maxLines = Int.MAX_VALUE,
        onValueChange = {}
    )
}
