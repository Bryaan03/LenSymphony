package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class Synthesize extends AbstractSynthesize {

    private static final Synthesize synthesize = new Synthesize();

    public Synthesize() {
        super(null);
    }

    public Synthesize(NoteSynthesizer decoratedSynthesizer) {
        super(decoratedSynthesizer);
    }

    /**
     * Returns the unique instance of this factory Singleton pattern.
     *
     * @return The single instance of Synthesize.
     */
    public static Synthesize getInstance() {
        return synthesize;
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
        double[] samples = new double[totalSamples];

        for (int i = 0; i < totalSamples; i++) {
            samples[i] = volume * Math.sin(2 * Math.PI * frequency * i / SAMPLE_RATE);
        }

        return samples;
    }
}
