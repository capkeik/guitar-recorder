package com.itis.guitarrecorder.utils.elements

import com.itis.guitarrecorder.utils.ChainElement
import java.nio.Buffer

external fun processCab(buffer: Buffer, bass: Float, mid: Float, treble: Float): Buffer

class Cab(
    val bass: Float,
    val mid: Float,
    val treble: Float
) : ChainElement {
    override fun process(buffer: Buffer): Buffer {
        return processCab(buffer, bass, mid, treble)
    }
}