package com.orels.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
    Text, Number
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
    isPassword: Boolean = false,
    shouldFocus: Boolean = false,
    leadingIcon: @Composable (() -> Unit) = { },
    trailingIcon: @Composable (() -> Unit)? = null,
    onTextChange: (String) -> Unit = {},
    maxCharacters: Int? = null,
) {
    val value = remember { mutableStateOf(defaultValue) }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val lineHeight = 40
    val focusRequester = FocusRequester()
    val widthPerCharacter = 20

    val inputModifier = if (shouldFocus) Modifier.focusRequester(focusRequester) else Modifier

    Column(modifier = (maxCharacters?.let { modifier.width((it * widthPerCharacter).dp) }
        ?: modifier.fillMaxWidth())) {
        Text(title)
        Box(modifier = inputModifier.zIndex(1f)) {
            OutlinedTextField(modifier = Modifier
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

                    }
                },
                placeholder = {
                    Text(text = placeholder,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                },
                singleLine = maxLines == 1,
                visualTransformation = if (isPassword && !passwordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions(
                    keyboardType = KeyboardType.Text),
                trailingIcon = trailingIcon,
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

    IconButton(onClick = onClick) {
        Icon(painter = image, description)
    }
}