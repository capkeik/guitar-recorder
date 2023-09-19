package com.itis.guitarrecorder.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.itis.guitarrecorder.utils.Timer

class RecordViewModel(
    private val startRec: (String) -> Unit,
    private val pauseRec: () -> Unit,
    private val stopRec: () -> Unit
): ViewModel(), Timer.OnTimerClickListener {

    var isRunning by mutableStateOf(false)
        private set

    var isStarted by mutableStateOf(false)
    private set

    var dur by mutableStateOf(0L)
        private set

    override fun onTimerTick(duration: Long) {
        dur = duration
    }

    fun startRecording() {
        isRunning = true
        isStarted = true
        startRec("")
    }
    fun pauseRecording() {
        isRunning = false
        pauseRec()
    }

    fun stopRecording() {
        isRunning = false
        isStarted = false
        stopRec()
    }
    fun cancel() {
        isRunning = false
        isStarted = false
        dur = 0
        stopRec()
    }
}