package com.itis.guitarrecorder.viewmodel

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.guitarrecorder.db.model.AudioRecord
import com.itis.guitarrecorder.repo.RecordRepo
import com.itis.guitarrecorder.utils.ChainElement
import com.itis.guitarrecorder.utils.Timer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.coroutines.coroutineContext

class RecordViewModel(
    private val repo: RecordRepo,
    private val startRec: () -> Unit,
    private val pauseRec: () -> Unit,
    private val stopRec: () -> Unit,
    private val resumeRec: () -> Unit,
    private val cancelRec: () -> Unit,
    var dirPath: String
): ViewModel(), Timer.OnTimerTickListener {

    val amps = MutableLiveData<ArrayList<Int>>()
    var isRunning by mutableStateOf(false)
        private set
    var isStarted by mutableStateOf(false)
        private set
    val dur = MutableLiveData<Long>(0L)

    var ampsToSave = arrayListOf<Int>()

    var recs by mutableStateOf(listOf<AudioRecord>())

    private var mp = MediaPlayer()

    private var chain = mutableStateOf(arrayListOf<ChainElement>())

    init {
        dur.value = 0L
    }

    fun updateDur(newValue: Long) {
        dur.value = newValue
    }

    private val formatter = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss")

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
        updateDur(0L)
        ampsToSave = amps.value?: arrayListOf()
        amps.value = arrayListOf()
    }

    fun cancel() {
        isRunning = false
        isStarted = false
        updateDur(0L)
        amps.value = arrayListOf()
        cancelRec()
    }

    fun resumeRecording() {
        isRunning = true
        resumeRec()
    }


    override fun onTimerTick(duration: Long) {
        updateDur(duration)
    }

    fun addAmp(a: Int) {
        Log.d("amp", "$a")
        val cur = amps.value?: arrayListOf()
        cur.add(0, a)
        amps.value = cur
    }

    fun updateList() {
        viewModelScope.launch(Dispatchers.IO) {
            recs = repo.getAll()
        }
    }

    fun initTrack(path: String) {
        try {
            mp.setDataSource(path)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mp.setOnPreparedListener {
            mp.start()
        }

        mp.setOnErrorListener { mp, what, extra ->
            // Handle error
            false
        }

        mp.setOnCompletionListener {
            mp.release()
        }

        mp.prepareAsync()
    }
}