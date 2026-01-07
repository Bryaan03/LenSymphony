package fr.univartois.butinfo.lensymphony.notes;

public class DottedNote implements Note {

    private final Note mainNote;

    /**
     * Constructs a DottedNote based on the given main note.
     *
     * @param mainNote The base note to be dotted.
     */
    public DottedNote(Note mainNote) {
        this.mainNote = mainNote;
    }

    /**
     * Gives the frequency of this note, as a frequency in Hertz (Hz).
     *
     * @return The frequency of this note.
     */
    public double getFrequency() {
        return mainNote.getFrequency();
    }

    /**
     * Gives the duration of this note in milliseconds, given a tempo in beats per
     * minute (BPM).
     *
     * @param tempo The tempo in beats per minute (BPM).
     *
     * @return The duration of this note, in milliseconds.
     */
    public int getDuration(int tempo) {
        int baseDuration = mainNote.getDuration(tempo);
        return (int)(baseDuration*1.5);
    }
}
