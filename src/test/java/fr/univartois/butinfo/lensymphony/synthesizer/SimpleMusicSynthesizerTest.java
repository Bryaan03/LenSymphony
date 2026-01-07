package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleMusicSynthesizerTest {

    @Test
    void testSynthesizerCombinesNotesFromStaff() {
        MusicPiece piece = new MusicPiece(120);
        MusicStaff staff = new MusicStaff(Instrument.PIANO, 0.8);

        staff.addNote(new NoteMusic(NotePitch.of(PitchClass.C,4), NoteValue.QUARTER));
        staff.addNote(new NoteMusic(NotePitch.of(PitchClass.D,4), NoteValue.QUARTER));

        piece.addStaff(staff);

        SimpleMusicSynthesizer synth = new SimpleMusicSynthesizer(staff, piece.getTempo(), staff.getVolume());
        synth.synthesize();

        double[] samples = synth.getSamples();

        assertNotNull(samples, "Le tableau de samples ne doit pas être null");
        assertTrue(samples.length > 0, "Le tableau doit contenir les samples des notes");
    }
}
