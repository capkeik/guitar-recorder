package com.itis.guitarrecorder.utils.elements

import com.itis.guitarrecorder.utils.ChainElement
import java.nio.Buffer

external fun processEQ(buffer: Buffer, bass: Float, mid: Float, treble: Float): Buffer

class EQ(
    val bass: Float,
    val mid: Float,
    val treble: Float
) : ChainElement{
    override fun process(buffer: Buffer): Buffer {
        return processEQ(buffer, bass, mid, treble)
    }
}