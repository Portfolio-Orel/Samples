package com.orels.samples.login.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orels.extension.noRippleClickable
import com.orels.samples.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )

        Input(
            modifier = Modifier
                .drawBehind {
                    drawRect(
                        color = Color.Transparent,
                        size = size,
                    )
                }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = stringResource(R.string.username),
            paddingLeadingIconEnd = 10.dp,
            paddingTrailingIconStart = 10.dp,
            onValueChange = {}
        )
        Input(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        color = Color.Transparent,
                        size = size,
                    )
                }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = stringResource(R.string.password),
            paddingLeadingIconEnd = 10.dp,
            paddingTrailingIconStart = 10.dp,
            onValueChange = {}
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .noRippleClickable { },
            text = stringResource(R.string.forgot_password),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.dont_have_an_account),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Light,
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .noRippleClickable { },
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp),
            onClick = { },
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background,
            ),
        ) {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Input(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    defaultValue: String = "",
    placeholder: String = "",
    maxLines: Int = 1,
    minLines: Int = 1,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    onDone: KeyboardActionScope.() -> Unit = {},
) {
    var text by remember { mutableStateOf(defaultValue) }
    Row(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
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