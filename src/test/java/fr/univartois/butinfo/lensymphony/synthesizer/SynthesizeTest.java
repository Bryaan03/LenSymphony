package fr.univartois.butinfo.lensymphony.synthesizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.SimpleNoteFactory;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;

class SynthesizeTest {

    SimpleNoteFactory fac = SimpleNoteFactory.getInstance();

    @Test
    void testSynthesizeA4OneSecond() {
        Synthesize synth = Synthesize.getInstance();
        Note note = fac.createNote(NotePitch.of(PitchClass.A, 4), NoteValue.HALF);
        double[] samples = synth.synthesize(note, 120, 0.8);
        assertEquals(NoteSynthesizer.SAMPLE_RATE, samples.length, "Expected 1 second of samples");

        assertEquals(0.0, samples[0], 1e-9, "First sample should be ~0 (sin(0))");

        double maxAbs = 0;
        for (double s : samples) {
            assertTrue(Double.isFinite(s), "Sample contains non-finite value");
            maxAbs = Math.max(maxAbs, Math.abs(s));
        }
        assertTrue(Math.abs(maxAbs - 0.8) < 0.05, "Peak amplitude should be approximately volume (0.8)");
    }

    @Test
    void testVolumeZeroProducesSilence() {
        Synthesize synth = Synthesize.getInstance();
        Note note = fac.createNote(NotePitch.of(PitchClass.A, 4), NoteValue.QUARTER); // 500 ms at tempo 120
        double[] samples = synth.synthesize(note, 120, 0.0);
        assertTrue(samples.length > 0);
        for (double s : samples) {
            assertEquals(0.0, s, 1e-12, "All samples should be zero when volume is 0");
        }
    }
}