package com.itis.guitarrecorder.utils.elements

import com.itis.guitarrecorder.utils.ChainElement
import java.nio.Buffer


external fun processAmp(buffer: Buffer, gain: Int): Buffer
class Amp(
    val gain: Int
): ChainElement {
    override fun process(buffer: Buffer): Buffer {
        return processAmp(buffer, gain)
    }

}