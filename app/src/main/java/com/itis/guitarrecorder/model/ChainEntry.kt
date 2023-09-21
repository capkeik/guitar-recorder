package com.itis.guitarrecorder.model

data class ChainEntry(
    val name: String,
    var attrs: List<Attribute> = listOf()
)
