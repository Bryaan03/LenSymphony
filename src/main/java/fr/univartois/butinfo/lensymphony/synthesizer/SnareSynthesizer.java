package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

import java.util.Random;

public class SnareSynthesizer implements NoteSynthesizer {

    private static final SnareSynthesizer INSTANCE = new SnareSynthesizer();
    private final Random random = new Random();
    private final double a = 0.01;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private SnareSynthesizer() {}

    /**
     * Gets the singleton instance of SnareSynthesizer.
     *
     * @return The singleton instance.
     */
    public static SnareSynthesizer getInstance() {
        return INSTANCE;
    }

    /**
     * ADSR envelope function
     */
    private double aDSR(double t) {
        if (t < a) {
            return t/a;
        }
        return Math.exp(15 * (a - t));
    }

    /**
     * Computes the audio samples for a given note.
     *
     * @param note The note to synthesize.
     * @param tempo The tempo in beats per minute (BPM).
     * @param volume The volume level for the note (0.0 to 1.0).
     *
     * @return An array of audio sample representing the synthesized note.
     */
    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double frequency = note.getFrequency();
        double durationSeconds = note.getDuration(tempo) / 1000.0;
        if (durationSeconds <= 0) {
            return new double[0];
        }
        int totalSamples = (int) Math.round(SAMPLE_RATE * durationSeconds);
        if (frequency == 0.0) {
            return new double[totalSamples];
        }
        double[] samples = new double[totalSamples];

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double)SAMPLE_RATE;
            double e = aDSR(t);
            samples[i] = volume * e * random.nextDouble(-1.0, 1.0);
        }

        return samples;
    }
}