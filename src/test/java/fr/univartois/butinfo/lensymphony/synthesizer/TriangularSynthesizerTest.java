package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TriangularSynthesizer class.
 */
class TriangularSynthesizerTest {

    @Test
    void testSingletonInstanceIsUnique() {
        TriangularSynthesizer s1 = TriangularSynthesizer.getInstance();
        TriangularSynthesizer s2 = TriangularSynthesizer.getInstance();

        assertNotNull(s1, "The instance should not be null");
        assertSame(s1, s2, "The singleton should always return the same instance");
    }

    @Test
    void testSynthesizeGeneratesSamples() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 3);
        NoteValue value = NoteValue.QUARTER;
        Note note = SimpleNoteFactory.getInstance().createNote(pitch, value);
        TriangularSynthesizer synth = new TriangularSynthesizer();

        double[] samples = synth.synthesize(note, 120, 0.8);
        assertNotNull(samples);
        assertTrue(samples.length > 0, "The synthesizer should generate samples");
    }

    @Test
    void testSynthesizeSamplesAreWithinVolumeRange() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 3);
        NoteValue value = NoteValue.QUARTER;
        Note note = SimpleNoteFactory.getInstance().createNote(pitch, value);
        TriangularSynthesizer synth = new TriangularSynthesizer();

        double volume = 0.5;
        double[] samples = synth.synthesize(note, 120, volume);

        for (double sample : samples) {
            assertTrue(sample >= -volume - 0.001 && sample <= volume + 0.001,
                    "Each sample should be within the range [-volume, +volume]");
        }
    }

    @Test
    void testSynthesizeProducesDifferentValuesOverTime() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 3);
        NoteValue value = NoteValue.QUARTER;
        Note note = SimpleNoteFactory.getInstance().createNote(pitch, value);
        TriangularSynthesizer synth = new TriangularSynthesizer();

        double[] samples = synth.synthesize(note, 120, 1.0);
        boolean hasVariation = false;
        for (int i = 1; i < samples.length; i++) {
            if (samples[i] != samples[i - 1]) {
                hasVariation = true;
                break;
            }
        }
        assertTrue(hasVariation, "Sample values should vary over time (otherwise the sound would be flat)");
    }
}
