
#include "lefp"

class Amp {
private:
    int gain;

public:
    Amp(int gain) : gain(gain) {}

    Buffer process(Buffer *inputBuffer) {
        std::vector<float> samples = inputBuffer->getSamples();
        int N = samples.size();

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < 3; ++j) {
                samples[i] = lefp::larsenEquation(samples[i], gain);
            }
        }

        inputBuffer->setSamples(samples);

        return *inputBuffer;
    }

}