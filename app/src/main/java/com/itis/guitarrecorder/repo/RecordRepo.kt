package com.itis.guitarrecorder.repo

import com.itis.guitarrecorder.db.dao.AudioRecordDao
import com.itis.guitarrecorder.db.model.AudioRecord

class RecordRepo(private val dao: AudioRecordDao) {
    fun save(audioRecord: AudioRecord) {
        dao.insert(audioRecord)
    }

    suspend fun getAll(): List<AudioRecord> {
        return dao.getAll()
    }



}