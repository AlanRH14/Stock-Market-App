package com.example.stockmarketapp.presentation.company_info

import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import com.example.stockmarketapp.domain.model.IntradayInfo
import java.lang.Math.round
import java.util.Calendar

import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
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
    val lowerValue = remember(info) {
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
        val calendar = Calendar.getInstance()
        (0 until info.size - 1 step 2).forEach { i ->
            val intradayInfo = info[i]
            calendar.time = intradayInfo.date
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + i * spacePerHour,
                    size.height - 5,
                    textPaint
                )
            }
        }

        val priceStep = (upperValue - lowerValue) / 5
        (0..5).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round((lowerValue + priceStep * i).toDouble()).toString(),
                    30F,
                    size.height - spacing - i * size.height / 5F,
                    textPaint
                )
            }
        }
    }
}