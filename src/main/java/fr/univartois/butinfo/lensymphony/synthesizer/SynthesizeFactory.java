/**
 * Ce fichier fait partie du projet lensymphony.
 *
 * (c) 2025 pasca
 * Tous droits réservés.
 */

package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.MusicPiece;
import fr.univartois.butinfo.lensymphony.notes.MusicStaff;
import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * Le type SynthesizeFactory
 *
 * @author pasca
 *
 * @version 0.1.0
 */
public class SynthesizeFactory {

    /**
     * Create a composite music synthesizer
     */
    public static CompositeMusicSynthesizer createComposite(MusicPiece piece) {
        CompositeMusicSynthesizer composite = new CompositeMusicSynthesizer();

        for (MusicStaff originalStaff : piece.getStaffs()) {
            MusicStaff tempStaff = new MusicStaff(originalStaff.getInstrument(), originalStaff.getVolume());

            for (Note note : originalStaff) {
                if (note != null) {
                    tempStaff.addNote(note);
                }
            }

            piece.addStaff(tempStaff);
            SimpleMusicSynthesizer synth = new SimpleMusicSynthesizer(tempStaff, piece.getTempo(), originalStaff.getVolume());
            composite.add(synth);
        }

        return composite;
    }

}
