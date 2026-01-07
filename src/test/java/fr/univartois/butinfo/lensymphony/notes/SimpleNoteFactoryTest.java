package fr.univartois.butinfo.lensymphony.notes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link SimpleNoteFactory} class (Sprint 1 only).
 * <p>
 * These tests check the correct behavior of the methods implemented
 * during Sprint 1: Singleton instance creation, and the creation
 * of basic notes and rests.
 * </p>
 */
class SimpleNoteFactoryTest {

    @Test
    void testSingleton() {
        SimpleNoteFactory factory1 = SimpleNoteFactory.getInstance();
        SimpleNoteFactory factory2 = SimpleNoteFactory.getInstance();
        assertSame(factory1, factory2, "The factory should be a singleton");
    }

    @Test
    void testCreateNote() {
        SimpleNoteFactory factory = SimpleNoteFactory.getInstance();
        NotePitch pitch = NotePitch.of(PitchClass.C, 4);
        NoteValue value = NoteValue.QUARTER;

        Note note = factory.createNote(pitch, value);
        assertNotNull(note, "The note should not be null");
        assertTrue(note instanceof NoteMusic, "The note should be an instance of NoteMusic");
        assertEquals(pitch.frequency(), note.getFrequency(), 0.0001, "The frequency should match the pitch");
        assertEquals(value.duration(120), note.getDuration(120), "The duration should match the value");
    }

    @Test
    void testCreateRest() {
        SimpleNoteFactory factory = SimpleNoteFactory.getInstance();
        NoteValue value = NoteValue.HALF;

        Note rest = factory.createRest(value);
        assertNotNull(rest, "The rest should not be null");
        assertTrue(rest instanceof NoteSilence, "The rest should be an instance of NoteSilence");
        assertEquals(0.0, rest.getFrequency(), "The frequency of a rest should be 0.0");
        assertEquals(value.duration(120), rest.getDuration(120), "The duration should match the value");
    }
    @Test
    void testCreateDottedNote() {
        SimpleNoteFactory factory = SimpleNoteFactory.getInstance();
        Note baseNote = factory.createNote(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);

        Note dotted = factory.createDottedNote(baseNote);
        assertNotNull(dotted, "The dotted note should not be null");
        assertTrue(dotted instanceof DottedNote, "The note should be an instance of DottedNote");
    }

    @Test
    void testCreateTiedNotes() {
        SimpleNoteFactory factory = SimpleNoteFactory.getInstance();

        List<Note> notes = List.of(
                factory.createNote(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER),
                factory.createNote(NotePitch.of(PitchClass.C, 4), NoteValue.HALF)
        );

        Note tied = factory.createTiedNotes(notes);
        assertNotNull(tied, "The tied note should not be null");
        assertTrue(tied instanceof TiedNote, "The note should be an instance of TiedNote");
    }
    @Test
    void testCreateFermataOn() {
        SimpleNoteFactory factory = SimpleNoteFactory.getInstance();
        Note baseNote = factory.createNote(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);
        Note fermataNote = factory.createFermataOn(baseNote);
        assertNotNull(fermataNote, "The fermata note should not be null");
        assertTrue(fermataNote.getDuration(120) >= baseNote.getDuration(120), "The duration should be at least the duration of the base note");
    }
}