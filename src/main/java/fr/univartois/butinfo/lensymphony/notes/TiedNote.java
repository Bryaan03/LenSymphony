/**
 * Ce fichier fait partie du projet lensymphony2.
 * (c) 2025 kilya
 * Tous droits réservés.
 */

package fr.univartois.butinfo.lensymphony.notes;

import java.util.List;

/**
 * Le type TiedNote
 *
 * @author kilyan
 *
 * @version 0.1.0
 */
public class TiedNote implements Note {
    private final List<Note> notes;

    public TiedNote(List<Note> notes) {
        this.notes = List.copyOf(notes);   
    }

    @Override
    public double getFrequency() {
        return notes.get(0).getFrequency();
    } // returns the frequency of the tied note

    @Override
    public int getDuration(int tempo) { // returns the sum of the duration of the tied note
        int duration = 0;
        for (Note note: notes) {
            duration += note.getDuration(tempo);
        }
        return duration;
    }
}


