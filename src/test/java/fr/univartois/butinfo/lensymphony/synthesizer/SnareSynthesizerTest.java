package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnareSynthesizerTest {

    private static final int TEMPO = 120;
    private static final double VOLUME = 0.7;
    NotePitch pitch = NotePitch.of(PitchClass.C, 4);
    NoteValue value = NoteValue.QUARTER;
    Note note = SimpleNoteFactory.getInstance().createNote(pitch, value);

    @Test
    void testSnareSamples() {
        SnareSynthesizer snare = SnareSynthesizer.getInstance();
        double[] samples = snare.synthesize(note, TEMPO, VOLUME);

        assertNotNull(samples);
        assertEquals((int)(note.getDuration(TEMPO)/1000.0 * 44100), samples.length);
        boolean allZero = true;
        for (double s : samples) {
            if (Math.abs(s) > 1e-10) {
                allZero = false;
            }
            assertTrue(Math.abs(s) <= VOLUME);
        }
        assertFalse(allZero);
    }
}