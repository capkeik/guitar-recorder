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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.itis.guitarrecorder.model.Slider
import com.itis.guitarrecorder.ui.composable.WaveForm
import com.itis.guitarrecorder.ui.theme.Orange
import com.itis.guitarrecorder.viewmodel.RecordViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun RecordScreen(
    viewModel: RecordViewModel
): Unit {
    MainView(viewModel)
}

@Composable
fun MainView(viewModel: RecordViewModel) {
    var showToast by remember { mutableStateOf(false) }
    var isPresetVisible by remember { mutableStateOf(false) }
    var showList by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val dur by viewModel.dur.observeAsState(0L)
    val amps by viewModel.amps.observeAsState(arrayListOf())

    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clickable {
                    isPresetVisible = true
                }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!isPresetVisible) {
            if (!showList) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = formatTime(dur),
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1
                    )
                    WaveForm(amps = amps)
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
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                viewModel.cancel()
                            }
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
                                if (viewModel.isRunning) {
                                    viewModel.pauseRecording()
                                } else {
                                    viewModel.startRecording()
                                }
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

                        if (!viewModel.isRunning) {
                            drawCircle(
                                color = Orange,
                                radius = (radius.toPx() - canvasWidth.toPx() / 2) / 2,
                                center = center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    if (!viewModel.isRunning) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clickable {
                                    viewModel.updateList()
                                    showList = true
                                }
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_save),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clickable {
                                    viewModel.stopRecording()
                                }
                        )
                    }
                }
            }
            else {
                Card(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(onClick = { showList = false }) {

                    }
                    LazyColumn {
                        items(viewModel.recs) {record ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.initTrack("${record.filepath}${record.filename}")
                                    }
                            ){
                                Text(
                                    text = record.filename,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isPresetVisible) {
            PresetWindow {
                isPresetVisible = false
            }
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
//    RecordScreen(RecordViewModel({}, {}, {}, {}, {}, ""))
}

fun formatTime(timeInMillis: Long): String {

    val millis = (timeInMillis % 1000) / 10
    val seconds = (timeInMillis / 1000) % 60
    val minutes = (timeInMillis / (1000 * 60)) % 60
    val hours = (timeInMillis / (1000 * 60 * 60))

    return if (hours > 0)
        "%02d:%02d:%02d.%02d".format(hours, minutes, seconds, millis)
    else
        "%02d:%02d.%02d".format(minutes, seconds, millis)
}


@Composable
fun PresetWindow(onDismiss: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 100.dp, horizontal = 30.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Hardware settings")

                var sliderPosition by remember { mutableStateOf(0f) }

                MinimizableView("Amp", arrayListOf(
                    Slider("Gain", 50f),
                    )
                )
                MinimizableView("EQ", sliders = arrayListOf(
                    Slider(name = "Bass", 50f),
                    Slider(name = "Mid", 50f),
                    Slider(name = "Treble", 50f),
                    )
                )
                MinimizableView(name = "Effect", sliders = arrayListOf(
                    Slider("Reverb", 50f),
                    )
                )
            }
            Button(onClick = onDismiss) {
                Text(text = "Save")
            }
        }

    }
}

@Composable
fun MinimizableView(
    name: String, sliders: ArrayList<Slider>) {
    var isMinimized by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.LightGray)
            .fillMaxWidth()
            .clickable {
                isMinimized = !isMinimized
            }
    ) {
        if (!isMinimized) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.size(8.dp))
                sliders.forEach {
                    NamedSlider(slider = it)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(name)
            }
        }
    }
}

@Composable
fun NamedSlider(slider: Slider) {
    var sliderValue by remember { mutableStateOf(slider.value) }

    Column {
        Text(text = slider.name)
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it
                            slider.value = it},
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
