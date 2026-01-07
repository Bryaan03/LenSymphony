/**
 * This file is part of the lensymphony project.
 *
 * (c) 2025 tibot duquesne
 * All rights reserved.
 */

package fr.univartois.butinfo.lensymphony.notes;

/**
 * The {@code FermataNote} class represents a musical note that is played with a
 * fermata, which prolongs its duration.
 * 
 * This class implements the Notes interface and decorates another note,
 * modifying its duration by a fixed percentage
 * 
 * @author Tibot Duquesne
 * @version 0.1.0
 */
public class FermataNote implements Note {
	private Note note;
	private static final double DURATION_PERCENTAGE = 1.75;

	/**
	 * Constructs a {@code FermataNote} that decorates the given note.
	 * 
	 * @param note the note to be prolonged with a fermata
	 */
	public FermataNote(Note note) {
		this.note = note;
	}

	/**
	 * Returns the frequency of the decorated note.
	 * 
	 * @return the frequency in Hertz
	 */
	public double getFrequency() {
		return note.getFrequency();
	}

	/**
	 * Returns the duration of the note, adjusted by the fermata.
	 * 
	 * @param tempo the tempo in beats per minute
	 * @return the prolonged duration in milliseconds
	 */
	public int getDuration(int tempo) {
		return (int) (note.getDuration(tempo) * DURATION_PERCENTAGE);
	}
}
