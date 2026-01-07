package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;
import fr.univartois.butinfo.lensymphony.notes.SimpleNoteFactory;
import fr.univartois.butinfo.lensymphony.synthesizer.TriangleSynthesizer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriangleSynthesizerTest {

    private static final int TEMPO = 120;
    private static final double VOLUME = 0.8;
    NotePitch pitch = NotePitch.of(PitchClass.C, 4);
    NoteValue value = NoteValue.QUARTER;
    Note note = SimpleNoteFactory.getInstance().createNote(pitch, value);

    @Test
    void testTriangleSamples() {
        TriangleSynthesizer triangle = TriangleSynthesizer.getInstance();
        double[] samples = triangle.synthesize(note, TEMPO, VOLUME);

        assertNotNull(samples);
        assertEquals((int)(note.getDuration(TEMPO)/1000.0 * 44100), samples.length);
        boolean allZero = true;
        for (double s : samples) {
            if (Math.abs(s) > 1e-10) allZero = false;
            assertTrue(Math.abs(s) <= VOLUME);
        }
        assertFalse(allZero);
    }
}
