package com.example.stockmarketapp.presentation.company_info

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import com.example.stockmarketapp.domain.model.IntradayInfo
import kotlin.math.roundToInt

@Composable
fun StockChart(
    modifier: Modifier = Modifier,
    info: List<IntradayInfo> = emptyList(),
    graphColor: Color = Color.Green
) {
    val spacing = 100F
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5F)
    }
    val upperValue = remember(info) {
        (info.maxOfOrNull { it.close }?.plus(1)?.roundToInt() ?: 0)
    }
    val lowValue = remember(info) {
        (info.minOfOrNull { it.close }?.toInt() ?: 0)
    }
    val density = LocalDensity.current
    val textPaint = remember {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / info.size
    }
}