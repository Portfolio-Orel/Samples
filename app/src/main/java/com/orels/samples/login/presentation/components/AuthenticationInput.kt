package com.orels.samples.login.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationInput(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    maxLines: Int = 1,
    minLines: Int = 1,
    value: String = "",
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    onDone: KeyboardActionScope.() -> Unit = {},
) {
    var text by remember { mutableStateOf(value) }
    Row(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        color = Color.Transparent,
                        size = size,
                    )
                },
            value = text,
            onValueChange = {
                val value = it.stripSpacesTabsAndNewLines()
                text = value
                onValueChange(it)
            },
            keyboardActions = KeyboardActions(onDone = onDone),
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            },

            maxLines = maxLines,
            minLines = minLines,
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                cursorColor = MaterialTheme.colorScheme.onBackground,
                focusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            ),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    onValueChange: (String) -> Unit,
    placeholder: String,
    defaultValue: String,
) {
    var password by remember { mutableStateOf(defaultValue) }

    TextField(
        value = password,
        onValueChange = {
            val value = it.stripSpacesTabsAndNewLines()
            password = value
            onValueChange(value)
        },
        label = { Text(placeholder) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

private fun String.stripSpacesTabsAndNewLines(): String =
    this.replace("\\s".toRegex(), "")