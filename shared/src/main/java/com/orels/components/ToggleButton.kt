package com.orels.components

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.orels.extension.noRippleClickable


/**
 * @param contentIfCheck is the content to show if the toggle is pressed and is set to true.
 * It receives a function that is called on dismiss.
 */
@Composable
fun ToggleButton(
    onChecked: (Boolean) -> Unit,
    enabled: () -> Boolean,
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    contentIfCheck: @Composable (() -> Unit)? = null,
) {
    var checkedState by remember { mutableStateOf(checked) }
    var disabled by remember { mutableStateOf(!enabled()) }

    OnLifecycleEvent(onResume = {
        disabled = !enabled()
    })

    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .noRippleClickable {
                if (!disabled) {
                    checkedState = !checkedState
                    onChecked(checkedState)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.85f),
            text = stringResource(id = text),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.weight(1f))
        if (!disabled) {
            Switch(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = !checkedState
                    onChecked(checkedState)
                },
                enabled = !disabled
            )
        }
    }
    if (contentIfCheck != null && checked) {
        var visible by remember { mutableStateOf(false) }
        val icon =
            if (visible) painterResource(id = R.drawable.ic_arrow_up) else painterResource(id = R.drawable.ic_arrow_down)
        Column {
            Icon(
                painter = icon,
                contentDescription = stringResource(R.string.show_or_hide)  ,
                modifier = Modifier
                    .padding(8.dp)
                    .noRippleClickable {
                        visible = !visible
                    }
            )
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(initialOffsetY = { -40 })
                        + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically() + fadeOut()
            ) {
                Box(modifier = Modifier.padding(horizontal = 4.dp)) {
                    contentIfCheck()
                }
            }
        }
    }
}