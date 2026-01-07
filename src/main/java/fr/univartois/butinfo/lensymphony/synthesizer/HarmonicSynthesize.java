package fr.univartois.butinfo.lensymphony.synthesizer;


import fr.univartois.butinfo.lensymphony.notes.Note;

public class HarmonicSynthesize extends AbstractSynthesize {

    private final int numberOfHarmonics;

    /**
     * Creates a new HarmonicSynthesize decorator.
     *
     * @param decoratedSynthesizer The NoteSynthesizer to decorate.
     * @param numberOfHarmonics    The number of harmonics to include in the synthesis.
     */
    public HarmonicSynthesize(NoteSynthesizer decoratedSynthesizer, int numberOfHarmonics) {
        super(decoratedSynthesizer);
        this.numberOfHarmonics = numberOfHarmonics;
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
        double[] baseSamples = super.synthesize(note, tempo, volume);
        double[] samples = new double[baseSamples.length];

        double frequency = note.getFrequency();

        if (samples.length == 0 || numberOfHarmonics <= 1) {
            return baseSamples;
        }

        for (int t = 0; t < samples.length; t++) {
            double time = (double) t / SAMPLE_RATE;
            double harmonicComponent = baseSamples[t];

            for (int i = 2; i <= numberOfHarmonics; i++) {
                harmonicComponent += Math.sin(2 * Math.PI * i * frequency * time) / Math.sqrt(i);
            }
            samples[t] = (volume / numberOfHarmonics) * harmonicComponent;
        }

        return samples;
    }
}
