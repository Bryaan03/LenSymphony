package fr.univartois.butinfo.lensymphony.synthesizer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.SimpleNoteFactory;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;

/**
 * Unit tests for {@link BassDrumSynthesizer}.
 *
 * These tests ensure that:
 *  - The synthesizer generates valid samples (non-null, correct size)
 *  - The amplitude decays over time
 * - Edge cases (zero frequency, zero duration) are handled safely
 *  @author tibot duquesne
 * 
 */
public class BassDrumSynthesizerTest {

    /**
     * Helper to create a basic test note.
     */
    private Note createTestNote() {
        NotePitch pitch = NotePitch.of(PitchClass.C, 3);
        NoteValue value = NoteValue.QUARTER;
        return SimpleNoteFactory.getInstance().createNote(pitch, value);
    }

    /**
     * Test that the singleton pattern works correctly.
     */
    @Test
    void testSingletonInstance() {
        BassDrumSynthesizer s1 = BassDrumSynthesizer.getInstance();
        BassDrumSynthesizer s2 = BassDrumSynthesizer.getInstance();
        assertSame(s1, s2, "BassDrumSynthesizer should be a singleton");
    }

    /**
     * Test that the synthesizer returns a valid waveform for a regular note.
     */
    @Test
    void testSynthesizesValidSamples() {
        Note note = createTestNote();
        BassDrumSynthesizer synth = BassDrumSynthesizer.getInstance();

        double[] samples = synth.synthesize(note, 120, 1.0);

        assertNotNull(samples, "Samples array should not be null");
        assertTrue(samples.length > 0, "Samples array should not be empty");

        boolean hasNonZero = false;
        for (double s : samples) {
            if (Math.abs(s) > 1e-9) {
                hasNonZero = true;
                break;
            }
        }
        assertTrue(hasNonZero, "Samples should contain non-zero values");
    }

    /**
     * Test that amplitude decays over time due to exponential damping.
     */
    @Test
    void testAmplitudeDecay() {
        Note note = createTestNote();
        BassDrumSynthesizer synth = BassDrumSynthesizer.getInstance();
        double[] samples = synth.synthesize(note, 120, 1.0);

        int total = samples.length;
        int window = total / 20; // 5% of the note length for averaging

        // Average absolute amplitude at start, middle, and end
        double startAvg = 0, midAvg = 0, endAvg = 0;

        for (int i = 0; i < window; i++) {
            startAvg += Math.abs(samples[i]);
            midAvg += Math.abs(samples[total / 2 + i]);
            endAvg += Math.abs(samples[total - window + i]);
        }

        startAvg /= window;
        midAvg /= window;
        endAvg /= window;

        // Check the global decay trend
        assertTrue(midAvg < startAvg, "Average amplitude should decay from start to middle");
        assertTrue(endAvg < midAvg, "Average amplitude should decay from middle to end");
    }


    /**
     * Test that a note with zero frequency returns a silent waveform.
     */
    @Test
    void testZeroFrequencyNote() {
        Note zeroFreqNote = new Note() {
            @Override
            public double getFrequency() {
                return 0.0;
            }

            @Override
            public int getDuration(int tempo) {
                return 500; // 0.5 second
            }
        };

        BassDrumSynthesizer synth = BassDrumSynthesizer.getInstance();
        double[] samples = synth.synthesize(zeroFreqNote, 120, 1.0);

        for (double s : samples) {
            assertEquals(0.0, s, 1e-9, "Samples should all be zero for zero-frequency note");
        }
    }

    /**
     * Test that an invalid duration (<= 0) returns an empty array.
     */
    @Test
    void testZeroOrNegativeDuration() {
        Note invalidNote = new Note() {
            @Override
            public double getFrequency() {
                return 440.0;
            }

            @Override
            public int getDuration(int tempo) {
                return 0; // simulate invalid duration
            }
        };

        BassDrumSynthesizer synth = BassDrumSynthesizer.getInstance();
        double[] samples = synth.synthesize(invalidNote, 120, 1.0);

        assertEquals(0, samples.length, "Duration <= 0 should return empty sample array");
    }

    /**
     * Test that increasing volume increases the amplitude of the samples.
     */
    @Test
    void testVolumeScaling() {
        Note note = createTestNote();
        BassDrumSynthesizer synth = BassDrumSynthesizer.getInstance();

        double[] quiet = synth.synthesize(note, 120, 0.5);
        double[] loud = synth.synthesize(note, 120, 1.0);

        assertEquals(quiet.length, loud.length);

        double maxQuiet = 0;
        double maxLoud = 0;
        for (int i = 0; i < quiet.length; i++) {
            maxQuiet = Math.max(maxQuiet, Math.abs(quiet[i]));
            maxLoud = Math.max(maxLoud, Math.abs(loud[i]));
        }

        assertTrue(maxLoud > maxQuiet, "Louder volume should produce larger amplitude samples");
    }
}
