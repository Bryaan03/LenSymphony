package fr.univartois.butinfo.lensymphony.synthesizer;

import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class ComplexHarmonicSynthesizer extends AbstractSynthesize {
    /**
     * The number of harmonics
     */
    private final int numberOfHarmonics;

    /**
     * The function defining the frequency multiplier for each harmonic
     */
    private final IntUnaryOperator harmonicFunction;

    /**
     * The function defining the amplitude for each harmonic
     */
    private final BiFunction<Integer, Double, Double> amplitudeFunc;

    /**
     * Creates a new {@code ComplexHarmonicSynthesizer}.
     *
     * @param decoratedSynthesizer The synthesizer to decorate
     * @param numberOfHarmonics The number of harmonics to add
     * @param harmonicFunction A function mapping each harmonic index to a frequency multiplier
     * @param amplitudeFunc A function giving the amplitude of harmonic at time
     */
    public ComplexHarmonicSynthesizer(NoteSynthesizer decoratedSynthesizer, int numberOfHarmonics, IntUnaryOperator harmonicFunction, BiFunction<Integer, Double, Double> amplitudeFunc) {
        super(decoratedSynthesizer);
        this.numberOfHarmonics = numberOfHarmonics;
        this.harmonicFunction = harmonicFunction;
        this.amplitudeFunc = amplitudeFunc;
    }

    /**
     * Synthesizes a note by adding harmonics
     *
     * @param note The musical note to synthesize
     * @param tempo The current tempo
     * @param volume The volume
     *
     * @return A {@code double[]} representing the audio samples for the synthesized note.
     */
    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double[] baseSamples = super.synthesize(note, tempo, volume);
        double frequency = note.getFrequency();

        for (int t = 0; t < baseSamples.length; t++) {
            double time = t / (double)SAMPLE_RATE;
            double harmonicSum = 0.0;

            for (int i = 2; i <= numberOfHarmonics; i++) {
                int harmonicIndex = harmonicFunction.applyAsInt(i);
                double amp = amplitudeFunc.apply(i, time);
                harmonicSum += amp * Math.sin(2 * Math.PI * harmonicIndex * frequency * time);
            }

            baseSamples[t] += harmonicSum;
            baseSamples[t] /= numberOfHarmonics;
        }

        return baseSamples;
    }
}
