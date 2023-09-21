package com.itis.guitarrecorder.db.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "audio_records")
data class AudioRecord (
    var filename: String,
    var filepath: String,
    var timestamp: Long,
    var duration: Long,
    var ampsPath: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @Ignore
    var isChecked = false
}
