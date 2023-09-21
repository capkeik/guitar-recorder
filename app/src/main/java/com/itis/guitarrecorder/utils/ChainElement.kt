package com.itis.guitarrecorder.utils

import java.nio.Buffer

interface ChainElement {
    fun process(buffer: Buffer) : Buffer
}