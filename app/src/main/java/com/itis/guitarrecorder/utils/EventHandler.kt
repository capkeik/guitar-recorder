package com.itis.guitarrecorder.utils

interface EventHandler {
    fun startRec(name: String) {}

    fun pauseRec()

    fun StopRec()
}