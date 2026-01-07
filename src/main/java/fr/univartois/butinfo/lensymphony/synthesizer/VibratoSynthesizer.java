package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class VibratoSynthesizer extends AbstractSynthesize {
    /**
     * The depth of the vibrato
     */
    private final double depth;

    /**
     * The speed of the vibrato
     */
    private final double speed;

    /**
     * Creates a new {@code VibratoSynthesizer}.
     *
     * @param decoratedSynthesizer The synthesizer to decorate 
     * @param depth The amplitude of the vibrato
     * @param speed The frequency of the vibrato
     */

    public VibratoSynthesizer(NoteSynthesizer decoratedSynthesizer, double depth, double speed) {
        super(decoratedSynthesizer);
        this.depth = depth;
        this.speed = speed;
    }

    /**
     * Synthesizes a note and applies a vibrato
     *
     * @param note The note to synthesize
     * @param tempo The tempo
     * @param volume The volume.
     *
     * @return A {@code double[]} containing the modulated samples.
     */
    @Override
    public double[] synthesize(Note note, int tempo, double volume) {
        double[] baseSamples = super.synthesize(note, tempo, volume);

        int totalSamples = baseSamples.length;

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double)SAMPLE_RATE;

            double vibrato = depth * Math.sin(2 * Math.PI * speed * t);

            baseSamples[i] += vibrato;
        }

        return baseSamples;
    }
}
