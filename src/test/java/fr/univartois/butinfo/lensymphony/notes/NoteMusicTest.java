package fr.univartois.butinfo.lensymphony.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class NoteMusicTest {
    SimpleNoteFactory fac = SimpleNoteFactory.getInstance();
    @Test
    void testGetFrequency() {
        // A4 est accordé à 440 Hz
        NotePitch pitch = NotePitch.of(PitchClass.A, 4);
        Note note = fac.createNote(pitch, NoteValue.QUARTER);

        assertEquals(440.0, note.getFrequency(), 0.0001);
    }

    @Test
    void testGetDurationQuarterNote() {
        // Tempo = 120 BPM -> une noire dure 500 ms
        NotePitch pitch = NotePitch.of(PitchClass.A, 4);
        Note note = fac.createNote(pitch, NoteValue.QUARTER);

        int duration = note.getDuration(120);

        assertEquals(500, duration);
    }

    @Test
    void testGetDurationWholeNote() {
        // Tempo = 60 BPM -> une ronde dure 4000 ms
        NotePitch pitch = NotePitch.of(PitchClass.C, 4);
        Note note = fac.createNote(pitch, NoteValue.WHOLE);

        int duration = note.getDuration(60);

        assertEquals(4000, duration);
    }

    @Test
    void testDifferentPitchDifferentFrequency() {
        NotePitch a4 = NotePitch.of(PitchClass.A, 4);  // 440 Hz
        NotePitch c4 = NotePitch.of(PitchClass.C, 4);

        Note noteA = fac.createNote(a4, NoteValue.QUARTER);
        Note noteC = fac.createNote(c4, NoteValue.QUARTER);

        assertNotEquals(noteA.getFrequency(), noteC.getFrequency());
    }
}
