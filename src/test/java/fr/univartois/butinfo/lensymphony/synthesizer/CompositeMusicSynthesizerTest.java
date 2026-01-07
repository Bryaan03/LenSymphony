/**
 * Ce fichier fait partie du projet lensymphony.
 *
 * (c) 2025 pasca
 * Tous droits réservés.
 */

package fr.univartois.butinfo.lensymphony.synthesizer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.lensymphony.notes.Instrument;
import fr.univartois.butinfo.lensymphony.notes.MusicPiece;
import fr.univartois.butinfo.lensymphony.notes.MusicStaff;
import fr.univartois.butinfo.lensymphony.notes.NoteMusic;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;


/**
 * Le type CompositeMusicSynthesizerTest
 *
 * @author pasca
 *
 * @version 0.1.0
 */
class CompositeMusicSynthesizerTest {
    @Test
    void testCompositeMultiVoices() {
        MusicPiece piece = new MusicPiece(120);

        MusicStaff staff1 = new MusicStaff(Instrument.PIANO, 0.8);
        staff1.addNote(new NoteMusic(NotePitch.of(PitchClass.C,4), NoteValue.QUARTER));
        piece.addStaff(staff1);

        MusicStaff staff2 = new MusicStaff(Instrument.PIANO, 0.8);
        staff2.addNote(new NoteMusic(NotePitch.of(PitchClass.C,4), NoteValue.QUARTER));
        piece.addStaff(staff2);

        CompositeMusicSynthesizer composite = SynthesizeFactory.createComposite(piece);
        composite.synthesize();

        double[] combined = composite.getSamples();
        assertNotNull(combined);
        assertTrue(combined.length > 0);
    }
}
