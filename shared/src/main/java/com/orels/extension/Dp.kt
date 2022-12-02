package com.orels.extension

import androidx.compose.ui.unit.Dp

fun Dp.multiplyByGoldenRatio(): Dp = this * 1.618f
fun Dp.divideByGoldenRatio(): Dp = this / 1.618f