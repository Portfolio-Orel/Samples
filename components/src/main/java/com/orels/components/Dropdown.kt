package com.orels.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun <T : DropdownItem> Dropdown(
    items: List<T>,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    secondaryAction: (T) -> Unit = {},
    secondaryIcon: ImageVector? = null,
    @StringRes defaultTitle: Int = R.string.empty_string,
    color: Color = MaterialTheme.colorScheme.onBackground,
    isError: Boolean = false,
    selectedIndex: Int = 0,
    onClick: () -> Unit = {},
    dropdownDecoratorStyle: DropdownDecoratorStyle = DropdownDecoratorStyle.Default,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember {
        mutableStateOf(
            items.getOrNull(selectedIndex) ?: items.firstOrNull()
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        when (dropdownDecoratorStyle) {
            DropdownDecoratorStyle.Default -> DropdownDecoratorDefault(
                selected = selectedItem,
                onClick = { expanded = expanded != true },
                defaultTitle = defaultTitle
            )
            DropdownDecoratorStyle.Text -> DropdownDecoratorText(
                selected = selectedItem,
                onClick = { expanded = expanded != true },
                defaultTitle = defaultTitle,
                color = color
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .heightIn(max = 400.dp)
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.background
                )
                .clickable { onClick() }
        ) {
            items.forEach {
                DropdownMenuItem(
                    modifier = Modifier
                        .padding(horizontal = 18.dp),
                    onClick = {
                        onSelected(it)
                        selectedItem = it
                        expanded = false
                    },
                ) {
                    Text(
                        text = it.getValue(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (secondaryIcon != null) {
                        Icon(
                            imageVector = secondaryIcon,
                            contentDescription = stringResource(R.string.secondary_action),
                            modifier = Modifier.clickable {
                                secondaryAction(it)
                                expanded = false
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun <T : DropdownItem> DropdownDecoratorText(
    selected: T?,
    onClick: () -> Unit,
    @StringRes defaultTitle: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    isError: Boolean = false,
) {
    Text(
        modifier = modifier.clickable { onClick() },
        text = selected?.getValue() ?: stringResource(id = defaultTitle),
        style = MaterialTheme.typography.labelLarge,
        color = if (isError) MaterialTheme.colorScheme.error
        else color
    )
}

@Composable
private fun <T : DropdownItem> DropdownDecoratorDefault(
    selected: T?,
    onClick: () -> Unit,
    @StringRes defaultTitle: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    expanded: Boolean = false,
    isError: Boolean = false,
) {

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .clickable {
                onClick()
            }
            .border(
                width = 1.dp,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(5.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selected?.getValue() ?: stringResource(id = defaultTitle),
            modifier = Modifier
                .padding(horizontal = 18.dp),
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
        Spacer(Modifier.weight(1f))
        Icon(

            imageVector = icon,
            contentDescription = stringResource(R.string.expansion_button),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

enum class DropdownDecoratorStyle {
    Default,
    Text,
}

interface DropdownItem {
    fun getIdentifier(): String
    fun getValue(): String
}