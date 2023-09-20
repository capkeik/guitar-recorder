package com.itis.guitarrecorder.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.itis.guitarrecorder.ui.theme.Orange

@Composable
fun WaveForm(amps: java.util.ArrayList<Int>) {

    val radius = 4f
    val spikeWidth = 9f
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
//        .padding(start = 0.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)

    ) {
        val canvasHeight = size.height
        val canvasWidth = size.width
        val hCenter = center.y
        if (amps.size * (spikeWidth + 1f) >= canvasWidth) {

        }
        var x = canvasWidth
        val mult = 25F
        for (a in amps) {
            val h = if (a / mult > canvasHeight) canvasHeight else if (a / mult <= 5f) 5f else a / mult
            drawRoundRect(
                color = Orange,
                topLeft = Offset(x, hCenter - h / 2),

                size = Size(spikeWidth, h),
                cornerRadius = CornerRadius(radius, radius)
            )
            x -= spikeWidth + 1f
        }
    }
}