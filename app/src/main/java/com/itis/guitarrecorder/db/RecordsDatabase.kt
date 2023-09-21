package com.itis.guitarrecorder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itis.guitarrecorder.db.dao.AudioRecordDao
import com.itis.guitarrecorder.db.model.AudioRecord

@Database(entities = [AudioRecord::class], version = 1)
abstract class RecordsDatabase : RoomDatabase() {
    abstract fun audioRecordDao(): AudioRecordDao
}