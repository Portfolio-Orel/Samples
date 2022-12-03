package com.orels.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

enum class InputType {
    Text, Number, Password
}

@Composable
fun Input(
    modifier: Modifier = Modifier,
    inputType: InputType = InputType.Text,
    title: String = "",
    placeholder: String = "",
    defaultValue: String = "",
    minLines: Int = 1,
    maxLines: Int = 1,
    isError: Boolean = false,
    shouldFocus: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTextChange: (String) -> Unit = {},
    maxCharacters: Int? = null,
    isLoading: Boolean = false,
) {
    val value = remember { mutableStateOf(defaultValue) }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val lineHeight = 25
    val focusRequester = FocusRequester()
    val widthPerCharacter = 20

    val minHeight = if (minLines > 1) {
        lineHeight * minLines
    } else {
        lineHeight * 2
    }
    val maxHeight = if (maxLines > 1) {
        lineHeight * maxLines
    } else {
        lineHeight * 2
    }

    val inputModifier = if (shouldFocus) Modifier.focusRequester(focusRequester) else Modifier

    val keyboardType = when (inputType) {
        InputType.Text -> KeyboardType.Text
        InputType.Number -> KeyboardType.Number
        InputType.Password -> KeyboardType.Password
    }

    Column(modifier = (maxCharacters?.let { modifier.width((it * widthPerCharacter).dp) }
        ?: modifier.fillMaxWidth())) {
        Text(title)
        Box(modifier = inputModifier.zIndex(1f)) {
            OutlinedTextField(modifier = Modifier
                .heightIn(min = minHeight.dp, max = maxHeight.dp)
                .fillMaxWidth()
                .focusable(enabled = shouldFocus),
                value = value.value,
                onValueChange = {
                    when (inputType) {
                        InputType.Text -> {
                            if (maxCharacters == null || it.length <= maxCharacters) {
                                value.value = it
                                onTextChange(it)
                            }
                        }
                        InputType.Number -> {
                            if (it.matches(Regex("[0-9]*")) && (maxCharacters == null || it.length <= maxCharacters)) {
                                value.value = it
                                onTextChange(it)
                            }
                        }
                        InputType.Password -> {
                            value.value = it
                            onTextChange(it)
                        }
                    }
                },
                placeholder = {
                    Text(text = placeholder,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                },
                singleLine = maxLines == 1,
                visualTransformation = if (inputType == InputType.Password
                    && !passwordVisible.value
                ) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                trailingIcon = {
                    if (isLoading) Loading(color = MaterialTheme.colorScheme.onBackground,
                        size = 8.dp,
                        width = 1.dp) else {
                        if (trailingIcon != null) {
                            trailingIcon()
                        } else {
//                            PasswordIcon(passwordVisible = passwordVisible.value,
//                                onClick = { passwordVisible.value = !passwordVisible.value })
                        }
                    }
                },
                isError = isError)
        }
    }
}


@Composable
private fun PasswordIcon(
    passwordVisible: Boolean,
    onClick: () -> Unit,
) {
    val image = if (passwordVisible) painterResource(id = R.drawable.ic_visibility_off_24)
    else painterResource(id = R.drawable.ic_visibility_24)
    val description =
        if (passwordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)

    Button(onClick = onClick) {
        Icon(painter = image, description)
    }
}