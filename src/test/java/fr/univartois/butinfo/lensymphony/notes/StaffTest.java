package fr.univartois.butinfo.lensymphony.notes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class StaffTest {

    @Test
    void testStaffStoresInstrument() {
        MusicStaff staff = new MusicStaff(Instrument.PIANO, 0.8);
        assertEquals(Instrument.PIANO, staff.getInstrument());
    }

    @Test
    void testStaffIteratorIteratesNotesInOrder() {
    	MusicStaff staff = new MusicStaff(Instrument.PIANO, 0.8);

        Note n1 = new NoteMusic(NotePitch.of(PitchClass.C, 4), NoteValue.QUARTER);
        Note n2 = new NoteMusic(NotePitch.of(PitchClass.D, 4), NoteValue.QUARTER);

        staff.addNote(n1);
        staff.addNote(n2);

        Iterator<Note> iterator = staff.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(n1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(n2, iterator.next());
        assertFalse(iterator.hasNext());
    }
}

