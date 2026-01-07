package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * The {@code BassDrumSynthesizer} class is a singleton that implements the
 * {@link NoteSynthesizer} interface to generate the sound of a bass drum.
 * It produces a low-frequency, percussive sound by simulating a decaying sine wave
 * whose frequency gradually decreases from a starting pitch to a lower pitch.
 * 
 * The generated waveform uses an exponential decay to emulate the natural damping
 * of a drum hit.
 * 
 * @author (tibot duquesne)
 * @version 1.0
 */
public class BassDrumSynthesizer implements NoteSynthesizer {

    /**
     * The single instance of this synthesizer (singleton pattern).
     */
    private static final BassDrumSynthesizer INSTANCE = new BassDrumSynthesizer();

    /**
     * The starting frequency (in Hz) of the bass drum sound.
     */
    private static final double FSTART = 60.0;

    /**
     * The ending frequency (in Hz) of the bass drum sound.
     */
    private static final double FEND = 40.0;

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private BassDrumSynthesizer() {}

    /**
     * Returns the single instance of the {@code BassDrumSynthesizer}.
     * 
     * @return The unique instance of this synthesizer.
     */
    public static BassDrumSynthesizer getInstance() {
        return INSTANCE;
    }

    /**
     * Synthesizes the sound of a bass drum for a given musical note.
     * <p>
     * The generated waveform is a decaying sine wave whose frequency decreases
     * linearly over time, producing a short and percussive bass drum sound.
     * </p>
     *
     * @param note The {@link Note} to be synthesized.
     * @param tempo The tempo (in beats per minute) used to calculate note duration.
     * @param volume The amplitude (volume) of the generated sound, typically between 0.0 and 1.0.
     * 
     * @return An array of audio samples representing the synthesized bass drum sound.
     */
    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double durationSeconds = note.getDuration(tempo) / 1000.0;

        if (durationSeconds <= 0) {
            return new double[0];
        }

        int totalSamples = (int) Math.round(SAMPLE_RATE * durationSeconds);

        if (note.getFrequency() == 0.0) {
            return new double[totalSamples];
        }

        double[] samples = new double[totalSamples];

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double) SAMPLE_RATE;

            double f = FSTART + (t * (FEND - FSTART) / durationSeconds);
            samples[i] = volume * Math.exp(-5 * t) * Math.sin(2 * Math.PI * f * t);
        }

        return samples;
    }
}
