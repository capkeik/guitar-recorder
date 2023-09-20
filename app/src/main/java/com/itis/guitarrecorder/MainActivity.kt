package com.itis.guitarrecorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.itis.guitarrecorder.ui.screen.RecordScreen
import com.itis.guitarrecorder.ui.theme.GuitarRecorderTheme
import com.itis.guitarrecorder.utils.Timer
import com.itis.guitarrecorder.viewmodel.RecordViewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

const val REQUEST_CODE = 200

class MainActivity : ComponentActivity() {

    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var permGranted = false

    private lateinit var recorder: MediaRecorder

    private var dirPath = ""

    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        requestPermission()
        super.onCreate(savedInstanceState)

        val recordViewModel = RecordViewModel(
            {
                timer.start()
                startRecording()
            }, {
                timer.pause()
                pauseRecording()
            }, {
                timer.stop()
                stopRecording()
            }, {
                timer.start()
                resumeRecorder()
            }
        )

        timer = Timer(recordViewModel)

        recordViewModel.dur.observeForever {
            try {
                val amp = recorder.maxAmplitude
                recordViewModel.addAmp(amp)
                Log.d("add amp", "")
                Log.d("amps", recordViewModel.amps.value.toString())
            } catch (e: Exception) {}
        }

        setContent {
            GuitarRecorderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecordScreen(recordViewModel)
                }
            }
        }
    }

    fun startRecording() {

        recorder = MediaRecorder()

        dirPath = "${externalCacheDir?.absolutePath}/"

        val formatter = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss")
        val fileName = "recording_${formatter.format(Date())}"
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$dirPath$fileName.mp3")

            try {
                prepare()
            } catch (e: IOException) {
                println(e)
            }

            start()
        }
    }

    fun pauseRecording() {
        recorder.pause()
    }

    fun resumeRecorder() {
        recorder.resume()
    }

    fun stopRecording() {
        try {
            recorder.stop()
        } catch (e: Exception) {
            println(e)
        }
    }

    private fun requestPermission() {
        permGranted = (ActivityCompat.checkSelfPermission(
            this,
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED)

        if (!permGranted) {
            ActivityCompat.requestPermissions(
                this,
                permissions,
                REQUEST_CODE
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GuitarRecorderTheme {
        Greeting("Android")
    }
}