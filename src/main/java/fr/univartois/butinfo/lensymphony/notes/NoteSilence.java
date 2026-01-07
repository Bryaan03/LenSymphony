package fr.univartois.butinfo.lensymphony.notes;

/*
* The NoteSilence class allows to create the silent note
* @author Romain Wallon
* @version 0.1.0
 */
public class NoteSilence implements Note {
    private final NoteValue value; // returns value of the note (black, white...)

    public NoteSilence(NoteValue value) {
        this.value = value;
    }

    @Override
    public double getFrequency() {
        return 0.0; // return frequency equals to null (silent one)
    }

    @Override
    public int getDuration(int tempo) { // returns the duration of the value
        return value.duration(tempo);
    }
}

