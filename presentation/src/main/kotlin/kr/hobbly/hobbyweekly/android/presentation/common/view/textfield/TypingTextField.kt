package kr.hobbly.hobbyweekly.android.presentation.common.view.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.LabelMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Shapes
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Transparent
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Warning
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypingTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "",
    isError: Boolean = false,
    isEnabled: Boolean = true,
    minLines: Int = 1,
    maxTextLength: Int = 100,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIconContent: (@Composable () -> Unit) = {},
    trailingIconContent: (@Composable () -> Unit) = {},
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

    Row(
        modifier = modifier
            .background(
                color = White,
                shape = Shapes.medium
            )
            .border(
                width = 1.dp,
                shape = Shapes.medium,
                color = currentColorState.value
            )
            .wrapContentHeight()
            .onFocusChanged {
                isTextFieldFocused = it.isFocused
                onTextFieldFocusChange(it.isFocused)
            }
            .padding(horizontal = 15.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIconContent()
        BasicTextField(
            value = text,
            onValueChange = {
                if (maxTextLength >= it.length) {
                    onValueChange(it)
                }
            },
            enabled = isEnabled,
            modifier = Modifier.weight(1f),
            textStyle = LabelMedium.merge(Neutral900),
            singleLine = minLines == 1,
            minLines = minLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(value = currentColorState.value),
            interactionSource = interactionSource,
        ) { textField ->
            TextFieldDefaults.DecorationBox(
                value = text,
                innerTextField = textField,
                enabled = isEnabled,
                singleLine = minLines == 1,
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
        trailingIconContent()
    }
}

@Preview
@Composable
private fun TypingTextField1Preview() {
    TypingTextField(
        text = "잘못 된 이름",
        hintText = "이름을 입력하세요.",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        isError = true,
    )
}

@Preview
@Composable
private fun TypingTextField2Preview() {
    TypingTextField(
        text = "이름",
        hintText = "이름을 입력하세요.",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        isError = false,
    )
}

@Preview
@Composable
private fun TypingTextField3Preview() {
    TypingTextField(
        text = "",
        hintText = "이름을 입력하세요.",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        isError = false,
        trailingIconContent = {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "bottom icon",
                modifier = Modifier.size(20.dp)
            )
        },
        leadingIconContent = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "bottom icon",
                modifier = Modifier.size(20.dp)
            )
        }
    )
}

@Preview
@Composable
private fun TypingTextField4Preview() {
    TypingTextField(
        text = "엄청나게 긴 텍스트입니다. 엄청나게 긴 텍스트입니다. 엄청나게 긴 텍스트입니다. 엄청나게 긴 텍스트입니다. 엄청나게 긴 텍스트입니다.",
        hintText = "",
        onValueChange = {}
    )
}