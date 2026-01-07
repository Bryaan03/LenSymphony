package fr.univartois.butinfo.lensymphony.notes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DottedNoteTest {

    @Test
    void testDottedNoteDuration() {
        Note base = new NoteMusic(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);

        DottedNote dotted = new DottedNote(base);

        int tempo = 120;
        int baseDuration = base.getDuration(tempo);
        int dottedDuration = dotted.getDuration(tempo);

        assertEquals(baseDuration * 1.5, dottedDuration, 0.0001,
                "The duration of the dotted note should be 1.5 times the base note duration");
    }

    @Test
    void testDottedNoteFrequencyUnchanged() {
        Note base = new NoteMusic(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);
        DottedNote dotted = new DottedNote(base);

        assertEquals(base.getFrequency(), dotted.getFrequency(),
                "The frequency of the dotted note should be the same as the base note");
    }
}