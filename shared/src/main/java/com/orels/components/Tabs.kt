package com.orels.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun Tabs(
    items: TabItems,
    height: Dp,
    onClick: (TabItem) -> Unit,
    modifier: Modifier = Modifier,
    default: TabItem? = null,
    width: Dp? = null,
    paddingBetweenItems: Dp = 4.dp,
) {
    val configuration = LocalConfiguration.current
    val tabWidth = width ?: (configuration.screenWidthDp / items.size).dp
    var selected by remember { mutableStateOf(default ?: items.firstOrNull()) }

    Row(modifier = modifier
        .padding(vertical = 8.dp)
        .height(height),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(paddingBetweenItems,
            Alignment.CenterHorizontally)) {
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .width(tabWidth)
                    .fillMaxHeight()
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(4.dp), clip = true)
                    .background(if (selected == item) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                    .clickable {
                        selected = item
                        onClick(item)
                    },
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(item.title),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (selected == item) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,

                    )
            }
        }

    }
}
typealias TabItems = List<TabItem>

data class TabItem(
    @StringRes val title: Int,
    val icon: Int,
    val identifier: String = "",
)