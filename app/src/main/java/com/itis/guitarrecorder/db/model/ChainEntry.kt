package com.itis.guitarrecorder.db.model

data class ChainEntry(
    val name: String,
    var attrs: List<Attribute> = listOf()
)
