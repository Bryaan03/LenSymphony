package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

public abstract class AbstractSynthesize implements NoteSynthesizer {

    protected final NoteSynthesizer decoratedSynthesizer;

    /**
     * Creates a new AbstractSynthesize decorator.
     *
     * @param decoratedSynthesizer The NoteSynthesizer to decorate.
     */
    protected AbstractSynthesize(NoteSynthesizer decoratedSynthesizer) {
        this.decoratedSynthesizer = decoratedSynthesizer;
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
    public double[] synthesize(Note note, int tempo, double volume) {
        return decoratedSynthesizer.synthesize(note, tempo, volume);
    }
}