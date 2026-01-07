package fr.univartois.butinfo.lensymphony.notes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MusicStaff implements Iterable<Note> {
    private final Instrument instrument;
    private final List<Note> notes = new ArrayList<>();
    private final double volume;

    public MusicStaff(Instrument instrument, double volume) {
    	this.volume = volume;
        this.instrument = instrument;
    }

    // Add a note in the list
    public void addNote(Note note) {
        notes.add(note);
    }

    // Return the instrument
    public Instrument getInstrument() {
        return instrument;
    }
    
    // Return volume
    public double getVolume() {
    	return volume;
    }

    // Get iterator
    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }
}