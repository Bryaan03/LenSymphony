package fr.univartois.butinfo.lensymphony.notes;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TiedNoteTest {
    @Test
    void testTiedNoteDurationSum() { // Test for the sum of the duration of the tied note
        Note n1 = new NoteMusic(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);
        Note n2 = new NoteMusic(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);

        TiedNote tied = new TiedNote(List.of(n1, n2));

        int tempo = 120;
        int expected = n1.getDuration(tempo) + n2.getDuration(tempo);

        assertEquals(expected, tied.getDuration(tempo), "The note must sum up the duration of all his notes.");
    }

    @Test
    void testTiedNoteFrequencySameAsComponents() { // Test of the frequency of the tied note
        Note n1 = new NoteMusic(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);
        Note n2 = new NoteMusic(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);

        TiedNote tied = new TiedNote(List.of(n1, n2));

        assertEquals(n1.getFrequency(), tied.getFrequency(), "The note must conserve the frequency of his notes.");
    }

}
