package com.itis.guitarrecorder.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itis.guitarrecorder.utils.Timer

class RecordViewModel(
    private val startRec: () -> Unit,
    private val pauseRec: () -> Unit,
    private val stopRec: () -> Unit,
    private val resumeRec: () -> Unit
): ViewModel(), Timer.OnTimerTickListener {

    val amps = MutableLiveData<ArrayList<Int>>()
    var isRunning by mutableStateOf(false)
        private set
    var isStarted by mutableStateOf(false)
        private set
    val dur = MutableLiveData<Long>(0L)

    init {
        dur.value = 0L
    }

    fun updateDur(newValue: Long) {
        dur.value = newValue
    }
    fun startRecording() {
        isRunning = true
        isStarted = true
        startRec()
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
        updateDur(0L)
        amps.value = arrayListOf()
        stopRec()
    }

//    fun getLiveDataList(): LiveData<List<Int>> {
//        return _ampsList
//    }
//
//    fun setAmpList(newList: List<Int>) {
//        _ampsList.value = newList
//    }

    override fun onTimerTick(duration: Long) {
        updateDur(duration)
    }

    fun addAmp(a: Int) {
        Log.d("amp", "$a")
        val cur = amps.value?: arrayListOf()
        cur.add(0, a)
        amps.value = cur
    }
}