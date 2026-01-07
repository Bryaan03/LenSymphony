package fr.univartois.butinfo.lensymphony.synthesizer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.NotePitch;
import fr.univartois.butinfo.lensymphony.notes.NoteValue;
import fr.univartois.butinfo.lensymphony.notes.SimpleNoteFactory;
import fr.univartois.butinfo.lensymphony.notes.PitchClass;

class ADSRSynthesizerTest {

    @Test
    void testAdsrEnvelopeAffectsAmplitude() {
        // Create a note (E4, half note)
        NotePitch pitch = NotePitch.of(PitchClass.E, 4);
        NoteValue value = NoteValue.HALF;
        Note note = SimpleNoteFactory.getInstance().createNote(pitch, value);

        // ADSR parameters: attack 10%, decay 20%, sustain 70%, release 30%
        ADSRSynthesizer synth = new ADSRSynthesizer(
            Synthesize.getInstance(), 0.1, 0.2, 0.7, 0.3
        );

        double[] samples = synth.synthesize(note, 120, 1.0);

        assertNotNull(samples);
        assertTrue(samples.length > 0);

        // --- Attack phase ---
        int attackIndex = (int)(samples.length * 0.05); // 5% of note duration
        int attackIndex2 = (int)(samples.length * 0.09); // near end of attack
        double attackStart = Math.abs(samples[0]);
        double attackMid = Math.abs(samples[attackIndex]);
        double attackEnd = Math.abs(samples[attackIndex2]);
        assertTrue(attackMid > attackStart, "Amplitude should increase during attack");
        assertTrue(attackEnd > attackMid, "Amplitude should continue to rise until attack ends");

        // --- Sustain phase ---
        int sustainStart = (int)(samples.length * 0.35); // after attack+decay
        int sustainMid = (int)(samples.length * 0.5);
        int sustainEnd = (int)(samples.length * 0.65); // just before release
        double sustain1 = Math.abs(samples[sustainStart]);
        double sustain2 = Math.abs(samples[sustainMid]);
        double sustain3 = Math.abs(samples[sustainEnd]);
        assertTrue(Math.abs(sustain1 - sustain2) < 0.01, "Amplitude should remain roughly constant in sustain");
        assertTrue(Math.abs(sustain2 - sustain3) < 0.01, "Amplitude should remain roughly constant in sustain");

        // --- Release phase ---
        int releaseIndex = (int)(samples.length * 0.85); // inside release phase
        int releaseEnd = samples.length - 1; // last sample
        double releaseStart = Math.abs(samples[releaseIndex]);
        double releaseFinal = Math.abs(samples[releaseEnd]);
        assertTrue(releaseFinal < releaseStart, "Amplitude should decrease during release");
    }
}
