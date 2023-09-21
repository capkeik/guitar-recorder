package com.itis.guitarrecorder.utils.elements

import com.itis.guitarrecorder.utils.ChainElement
import java.nio.Buffer


external fun processReverberator(buffer: Buffer, delays: List<Float>, volumes: List<Float>): Buffer
class Reverberator(
    val delays: List<Float>,
    valvolumes: List<Float>
) : ChainElement {
    override fun process(buffer: Buffer): Buffer {
        return processReverberator(buffer, delays, volumes = )
    }

}