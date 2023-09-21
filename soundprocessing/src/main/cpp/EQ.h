//
// Created by capkeik on 9/21/23.
//

#ifndef GUITARRECORDER_EQ_H
#define GUITARRECORDER_EQ_H




class EQ {
private:
    float bass, mid, treble;

public:
    EQ(float bass, float mid, float treble) : bass(bass), mid(mid), treble(treble) {}
    Buffer process(Buffer* inputBuffer)
};

#endif //GUITARRECORDER_EQ_H
