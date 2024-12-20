package com.example.stockmarketapp.presentation.company_info

import android.graphics.Paint
import android.graphics.Path
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

        val strokePath = Path().apply {
            val height = size.height
            for (i in info.indices) {
                val mInfo = info[i]
                val nextInfo = info.getOrNull(i + 1) ?: info.last()
                val leftRatio = (mInfo.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (i + 1) * spacePerHour
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if (i == 0) {
                    moveTo(x1, y1)
                }
                // TODO : Search alternatives for quadraticBezierTo
                cubicTo(x1, x2, (x1 + x2) / 2f, (y1 + y2) / 2F, 0F, 0F)
            }
        }
    }
}