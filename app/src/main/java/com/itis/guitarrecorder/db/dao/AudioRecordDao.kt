package com.itis.guitarrecorder.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.itis.guitarrecorder.db.model.AudioRecord

@Dao
interface AudioRecordDao {
    @Query("SELECT * FROM audio_records")
    fun getAll(): List<AudioRecord>

    @Insert
    fun insert(audioRecord: AudioRecord)

    @Delete
    fun delete(audioRecord: AudioRecord)

    @Delete
    fun delete(audioRecords: ArrayList<AudioRecord>)

    @Update
    fun update(audioRecord: AudioRecord)
}