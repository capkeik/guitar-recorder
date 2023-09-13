package com.itis.guitarrecorder.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itis.guitarrecorder.R
import com.itis.guitarrecorder.ui.theme.Orange
import java.util.concurrent.TimeUnit

@Composable
fun RecordScreen(x: String = "Hello"): Unit {
    CenteredTextViewWithIcons()
}

@Composable
fun CenteredTextViewWithIcons() {
    val interactionSource = remember { MutableInteractionSource() }
    var showToast by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = formatTime(0),
                fontSize = 64.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null, // Provide a meaningful description
                tint = Color.Black, // Adjust the tint color if needed
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Canvas(
                modifier = Modifier
                    .height(96.dp)
                    .width(96.dp)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
                        showToast = true
                    }
            ) {
                val canvasWidth = 96.dp
                val canvasHeight = 96.dp
                val radius = 80.dp
                val center = Offset(canvasWidth.toPx() / 2, canvasHeight.toPx() / 2)

                // Draw the ring
                drawCircle(
                    color = Orange,
                    radius = radius.toPx() - canvasWidth.toPx() / 2,
                    center = center,
                    style = Stroke(width = 4.dp.toPx())
                )

                drawCircle(
                    color = Orange,
                    radius = (radius.toPx() - canvasWidth.toPx() / 2) / 2,
                    center = center
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(60.dp)
            )
        }
    }
    val context = LocalContext.current
    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(
                context,
                "Canvas Clicked!",
                Toast.LENGTH_SHORT
            ).show()
            showToast = false
        }
    }
}

@Preview
@Composable
fun ScreenPrev() {
    RecordScreen()
}

fun formatTime(timeInMillis: Long): String {
    var millis = timeInMillis
    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    millis -= TimeUnit.HOURS.toMillis(hours)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    millis -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
    return "${if (hours < 10) "0" else ""}$hours:" +
            "${if (minutes < 10) "0" else ""}$minutes:" +
            "${if (seconds < 10) "0" else ""}$seconds"
}