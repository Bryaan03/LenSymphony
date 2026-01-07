/**
 * Ce fichier fait partie du projet lensymphony.
 *
 * (c) 2025 pasca
 * Tous droits réservés.
 */

package fr.univartois.butinfo.lensymphony.notes;


/**
 * Le type NoteMusique
 *
 * @author pasca
 *
 * @version 0.1.0
 */
public class NoteMusic implements Note {
    private final NotePitch pitch;
    private final NoteValue value;

    public NoteMusic(NotePitch pitch, NoteValue value) {
        this.pitch = pitch;
        this.value = value;
    }
    
    // Retourne la fréquence
    public double getFrequency() {
        return pitch.frequency();
    }
    
    // Retourne la durée de la fréquence 
    public int getDuration(int tempo) {
        return value.duration(tempo);
    }
    
}

