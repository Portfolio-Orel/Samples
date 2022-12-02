package com.orels.samples.ui.multi_fab

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MiniFloatingActionButton(
    miniFloatingAction: MiniFloatingAction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
     FloatingActionButton(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        onClick = {
            miniFloatingAction.action()
            onClick()
        }) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = miniFloatingAction.icon),
            contentDescription = miniFloatingAction.description
        )
    }
}

data class MiniFloatingAction(
    val action: () -> Unit,
    @DrawableRes val icon: Int,
    val description: String = "",
)