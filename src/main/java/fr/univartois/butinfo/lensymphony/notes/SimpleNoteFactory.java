package fr.univartois.butinfo.lensymphony.notes;

import java.util.List;

/**
 * A concrete implementation of the {@link AbstractNoteFactory} interface.
 * 
 * This factory supports creating simple musical notes, rests (silences), dotted
 * notes, fermata notes, and tied notes.
 * 
 * More complex behaviors may be added in future sprints, but this version
 * already provides concrete implementations for the basic note types.
 * 
 * This class uses the Singleton pattern: only one instance of the factory
 * exists.
 * s
 * 
 * @author Tibot
 * @version 1.0.0
 */
public class SimpleNoteFactory implements AbstractNoteFactory {

	private static final SimpleNoteFactory instance = new SimpleNoteFactory();

	/**
	 * Private constructor to prevent direct instantiation.
	 */
	private SimpleNoteFactory() {
		// No initialization
	}

	/**
	 * Returns the unique instance of this factory (Singleton pattern).
	 *
	 * @return The single instance of {@code SimpleNoteFactory}.
	 */
	public static SimpleNoteFactory getInstance() {
		return instance;
	}

	/**
	 * Creates a rest (silence) with the given note value.
	 *
	 * @param value The rhythmic value of the rest.
	 * @return A new NoteSilence object.
	 */
	@Override
	public Note createRest(NoteValue value) {
		return new NoteSilence(value);
	}

	/**
	 * Creates a simple musical note with the given pitch and value.
	 *
	 * @param pitch The pitch of the note.
	 * @param value The rhythmic value of the note.
	 * @return A new NoteMusic object.
	 */
	@Override
	public Note createNote(NotePitch pitch, NoteValue value) {
		return new NoteMusic(pitch, value);
	}

	/**
	 * Creates a dotted note from the given base note.
	 * 
	 * A dotted note has a duration 1.5 times that of the base note.
	 *
	 * @param note The base Note to be dotted.
	 * @return A new DottedNote object wrapping the base note.
	 */
	@Override
	public Note createDottedNote(Note note) {
		return new DottedNote(note);
	}

	/**
	 * Creates a note with a fermata from the given base note.
	 * 
	 * A fermata note may have extended duration while preserving the pitch.
	 *
	 * @param note The base Note to apply the fermata to.
	 * @return A new FermataNote object wrapping the base note.
	 */
	@Override
	public Note createFermataOn(Note note) {
		return new FermataNote(note);
	}

	/**
	 * Creates a tied note by combining multiple notes together.
	 *
	 * The resulting note preserves the sequence and duration of all base notes.
	 * 
	 *
	 * @param notes The list of Note objects to tie together.
	 * @return A new TiedNote object combining the given notes.
	 */
	@Override
	public Note createTiedNotes(List<Note> notes) {
		return new TiedNote(notes);
	}
}
