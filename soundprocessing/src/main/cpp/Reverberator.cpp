
using namespace std;

class Reverberator { const vector <Real> &signal = _signal.get();Real &strongDecay = _strongDecay.get();

vector <Real> absSignal;
Real centroid;

_abs->input("array").
set(signal);
_abs->output("array").
set(absSignal);
_abs->

compute();

_centroid->configure("range", (signal.

size()

-1) / parameter("sampleRate").

toReal()

);
_centroid->input("array").
set(absSignal);
_centroid->output("centroid").
set(centroid);
_centroid->

compute();

if (centroid <= 0.0) {
// Zero signals (silence) has no strong decay
strongDecay = 0.0;
return;
}

Real signalEnergy = energy(signal);
strongDecay = sqrt(signalEnergy / centroid);
}

} // namespace standard
} // namespace essentia



namespace essentia {
    namespace streaming {

        const char *StrongDecay::name = standard::StrongDecay::name;
        const char *StrongDecay::category = standard::StrongDecay::category;
        const char *StrongDecay::description = standard::StrongDecay::description;


        void StrongDecay::reset() {
            AccumulatorAlgorithm::reset();
            _centroid = 0.0;
            _energy = 0.0;
            _weights = 0.0;
            _idx = 0;
        }

        void StrongDecay::consume() {
            const vector <Real> &signal = *((const vector <Real> *) _signal.getTokens());

            for (int i = 0; i < (int) signal.size(); i++) {
                Real absSignal = fabs(signal[i]);
                _centroid += (_idx++) * absSignal;
                _weights += absSignal;
            }
            _energy += energy(signal);
        }


        void StrongDecay::finalProduce() {
            if (_idx < 2) {
                throw EssentiaException(
                        "StrongDecay: cannot compute centroid of an array of size < 2");
            }

            if (_weights == 0) {
                _centroid = 0.0;
            } else {
                _centroid /= _weights;
                _centroid /= parameter("sampleRate").toReal();
            }

            if (_centroid <= 0.0) {
                // Zero signals (silence) has no strong decay
                _strongDecay.push((Real) 0.0);
                return;
            }

            _strongDecay.push((Real) sqrt(_energy / _centroid));
        }
    }