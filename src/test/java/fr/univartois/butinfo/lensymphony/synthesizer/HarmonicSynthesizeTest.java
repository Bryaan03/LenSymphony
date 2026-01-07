package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HarmonicSynthesizeTest {

    @Test
    void testVolumeZeroProducesSilence() {
        try {
            NoteSynthesizer baseSynth = Synthesize.getInstance();
            HarmonicSynthesize harmonicSynth = new HarmonicSynthesize(baseSynth, 5);

            SimpleNoteFactory fac = SimpleNoteFactory.getInstance();
            Note note = fac.createNote(NotePitch.of(PitchClass.A, 4), NoteValue.QUARTER);

            double[] samples = harmonicSynth.synthesize(note, 120, 0.0);

            for (double s : samples) {
                assertEquals(0.0, s, 1e-12, "All samples should be zero when volume is 0");
            }
        } catch (Exception e) {
            fail("Exception during test: " + e.getMessage());
        }
    }
}