package fr.univartois.butinfo.lensymphony.synthesizer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.SimpleNoteFactory;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;

class ComplexHarmonicSynthesizerTest {
    @Test
    void testSynthesizeAddsHarmonics() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 4);
        NoteValue value = NoteValue.QUARTER;
        Note note = SimpleNoteFactory.getInstance().createNote(pitch, value);

        ComplexHarmonicSynthesizer synth = new ComplexHarmonicSynthesizer(
                Synthesize.getInstance(),
                5,
                i -> i,
                (i, t) -> 1.0 / i
        );

        double[] samples = synth.synthesize(note, 120, 1.0);

        assertNotNull(samples, "The sample array should not be null");
        assertTrue(samples.length > 0, "The sample array should contain values");

        boolean nonZero = false;
        for (double v : samples) {
            if (Math.abs(v) > 0.0001) {
                nonZero = true;
                break;
            }
        }
        assertTrue(nonZero, "The samples should contain non zero values");

    }
}
