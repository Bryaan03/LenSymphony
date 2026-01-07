package fr.univartois.butinfo.lensymphony.notes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteSilenceTest {
    private NoteSilence noteSilence;
    private NoteValue noteValue;
    @BeforeEach
    void setUp() {
        noteValue = NoteValue.WHOLE;
        noteSilence = new NoteSilence(noteValue);
    }

    @Test
    void testGetFrequencyShouldReturnZero() {
        assertEquals(0.0, noteSilence.getFrequency(),
                "The silent note must be equal to 0.0 Hz.");
    }

    @Test
    void testGetDurationShouldDelegateToNoteValue() {
        int tempo = 120;
        int expectedDuration = noteValue.duration(tempo);

        assertEquals(expectedDuration, noteSilence.getDuration(tempo),
                "The length of silence must be equal to NoteValue.");
    }
    @Test
    void testGetDurationWithDifferentTempos() {
        int tempo1 = 60;
        int tempo2 = 180;

        int duration1 = noteSilence.getDuration(tempo1);
        int duration2 = noteSilence.getDuration(tempo2);

        assertTrue(duration1 != duration2,
                "The duration must change according the tempo.");
    }

}
