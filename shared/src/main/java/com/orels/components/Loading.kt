package com.orels.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Loading(
    color: Color = MaterialTheme.colorScheme.onBackground,
    size: Dp = 24.dp,
    width: Dp = 2.dp
) {
    CircularProgressIndicator(
        modifier = Modifier
            .size(24.dp),
        color = color,
        strokeWidth = 2.dp
    )
}