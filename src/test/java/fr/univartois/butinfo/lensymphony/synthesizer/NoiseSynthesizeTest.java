package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NoiseSynthesizeTest {
    @Test
    void testNoiseAffectsSignal() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 4);
        NoteValue value = NoteValue.QUARTER;
        Note note = SimpleNoteFactory.getInstance().createNote(pitch, value);

        NoiseSynthesize synth = new NoiseSynthesize(Synthesize.getInstance(), 0.1);

        double[] samples = synth.synthesize(note, 120, 1.0);

        assertNotNull(samples, "The sample array should not be null");
        assertTrue(samples.length > 0, "The sample array should not be empty");

        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        for (double s : samples) {
            if (s < min) min = s;
            if (s > max) max = s;
        }

        double range = max - min;

        assertTrue(range > 0.001, "Noise should introduce variation in the signal");
    }
}
