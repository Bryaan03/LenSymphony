package fr.univartois.butinfo.lensymphony.synthesizer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.SimpleNoteFactory;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;

class VibratoSynthesizerTest {
    @Test
    void testVibratoModifiesSignal() {
        NotePitch pitch = NotePitch.of(PitchClass.A, 4);
        NoteValue value = NoteValue.QUARTER;
        Note note = SimpleNoteFactory.getInstance().createNote(pitch, value);

        VibratoSynthesizer synth = new VibratoSynthesizer(Synthesize.getInstance(), 0.02, 5);
        double[] samples = synth.synthesize(note, 120, 1.0);

        assertNotNull(samples);
        assertTrue(samples.length > 0);

        double sum = 0;
        for (double s : samples) sum += s;
        double mean = sum / samples.length;

        assertFalse(Double.isNaN(mean), "The samples should not be Null");
    }
}
