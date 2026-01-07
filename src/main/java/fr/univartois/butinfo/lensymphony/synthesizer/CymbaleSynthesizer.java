package fr.univartois.butinfo.lensymphony.synthesizer;

import java.util.Random;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class CymbaleSynthesizer implements NoteSynthesizer {

    private static final CymbaleSynthesizer INSTANCE = new CymbaleSynthesizer();
    private final Random random = new Random();
    private static final double a = 0.01;
    private static final double d = 0.2;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private CymbaleSynthesizer() {}

    /**
     * Gets the singleton instance of CymbaleSynthesizer.
     *
     * @return The singleton instance.
     */
    public static CymbaleSynthesizer getInstance() {
        return INSTANCE;
    }

    /**
     * ADSR envelope function
     */
    private double aDSR(double t) {
        if (t < a) {
            return t/a;
        }
        return Math.exp((a - t) / d);
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
            samples[i] = volume * e * random.nextDouble(-1, 1) * Math.sin(4000 * Math.PI * t);
        }

        return samples;
    }
}