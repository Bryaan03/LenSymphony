package fr.univartois.butinfo.lensymphony.notes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FermataNoteTest {

    @Test
    void testGetFrequency() {
        Note originalNote = new Note() {
            @Override
            public double getFrequency() {
                return 440.0; 
            }

            @Override
            public int getDuration(int tempo) {
                return 1000; 
            }
        };

        FermataNote fermata = new FermataNote(originalNote);
        assertEquals(440.0, fermata.getFrequency(), 0.001, "Frequency should match the original note");
    }

    @Test
    void testGetDuration() {
        Note originalNote = new Note() {
            @Override
            public double getFrequency() {
                return 440.0;
            }

            @Override
            public int getDuration(int tempo) {
                return 1000;
            }
        };

        FermataNote fermata = new FermataNote(originalNote);
        int expected = (int)(1000 * 1.75);
        assertEquals(expected, fermata.getDuration(120), "Duration should be 1.75 times the original note");
    }

    @Test
    void testGetDurationDifferentTempo() {
        Note originalNote = new Note() {
            @Override
            public double getFrequency() {
                return 440.0;
            }

            @Override
            public int getDuration(int tempo) {
                return 600 * 60 / tempo; 
            }
        };

        FermataNote fermata = new FermataNote(originalNote);

        int expectedAt60 = (int)((600 * 60 / 60) * 1.75);
        int expectedAt120 = (int)((600 * 60 / 120) * 1.75);

        assertEquals(expectedAt60, fermata.getDuration(60), "Duration at tempo 60 should be scaled correctly");
        assertEquals(expectedAt120, fermata.getDuration(120), "Duration at tempo 120 should be scaled correctly");
    }
}
