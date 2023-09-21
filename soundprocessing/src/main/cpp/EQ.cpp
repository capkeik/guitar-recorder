
#include "EQ.h"
#include <fftw3.h>

Buffer EQ::process(Buffer* inputBuffer) {
    // Assuming the Buffer class has a method `getSamples()` to get the audio samples
    // and `setSamples()` to set the processed samples

    std::vector<float> samples = inputBuffer->getSamples();
    int N = samples.size();

    // Perform Fourier Transform
    fftwf_complex* in = (fftwf_complex*) fftwf_malloc(sizeof(fftwf_complex) * N);
    fftwf_complex* out = (fftwf_complex*) fftwf_malloc(sizeof(fftwf_complex) * N);

    fftwf_plan forwardPlan = fftwf_plan_dft_1d(N, in, out, FFTW_FORWARD, FFTW_ESTIMATE);

    for (int i = 0; i < N; ++i) {
        in[i][0] = samples[i]; // Real part
        in[i][1] = 0;           // Imaginary part (assuming real input)
    }

    fftwf_execute(forwardPlan);

    // Apply equalization in frequency domain (if desired)

    // Perform Inverse Fourier Transform
    fftwf_plan inversePlan = fftwf_plan_dft_1d(N, out, in, FFTW_BACKWARD, FFTW_ESTIMATE);
    fftwf_execute(inversePlan);

    std::vector<float> processedSamples(N);

    for (int i = 0; i < N; ++i) {
        processedSamples[i] = in[i][0] / N; // Normalize by N for inverse transform
    }

    fftwf_destroy_plan(forwardPlan);
    fftwf_destroy_plan(inversePlan);
    fftwf_free(in);
    fftwf_free(out);

    // Set the processed samples back to the buffer
    inputBuffer->setSamples(processedSamples);

    return *inputBuffer; // Return the processed buffer
}

