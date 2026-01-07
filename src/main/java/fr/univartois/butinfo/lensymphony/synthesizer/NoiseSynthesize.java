package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;
import java.util.Random;

public class NoiseSynthesize extends AbstractSynthesize {

    private final double noiseLevel;

    private final Random random = new Random();

    /**
     * Creates a new NoiseSynthesize decorator.
     *
     * @param decoratedSynthesizer The NoteSynthesizer to decorate.
     * @param noiseLevel           The maximum amplitude of the noise to add.
     */
    public NoiseSynthesize(NoteSynthesizer decoratedSynthesizer, double noiseLevel) {
        super(decoratedSynthesizer);
        this.noiseLevel = noiseLevel;
    }

    /**
     * Computes the audio samples for a given note.
     *
     * @param note The note to synthesize.
     * @param tempo The tempo in beats per minute (BPM).
     * @param volume The volume level for the note (0.0 to 1.0).
     *
     * @return An array of audio sample representing the synthesized note with noise.
     */
    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double[] samples = super.synthesize(note, tempo, volume);

        for (int i = 0; i < samples.length; i++) {
            samples[i] += random.nextDouble(-noiseLevel, noiseLevel);
        }
        return samples;
    }
}
