package fr.univartois.butinfo.lensymphony.notes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a musical piece composed of one or more music staffs. Each
 * {@code MusicPiece} has a fixed tempo and contains a list of MusicStaff
 * objects. This class provides methods to add staffs and retrieve the tempo or
 * the list of staffs.
 * 
 * This class is immutable regarding the tempo, but the list of staffs can grow
 * as new staffs are added.
 * 
 * @author Duquesne Tibot
 * @version 1.0.0
 */
public class MusicPiece implements Iterable<MusicStaff> {

	/**
	 * The tempo of the musical piece in beats per minute (BPM).
	 */
	private final int tempo;

	/**
	 * The list of music staffs contained in this piece.
	 */
	private final List<MusicStaff> staffs = new ArrayList<>();

	/**
	 * Creates a new {@code MusicPiece} with the given tempo.
	 *
	 * @param tempo The tempo of the piece in beats per minute (BPM).
	 */
	public MusicPiece(int tempo) {
		this.tempo = tempo;
	}

	/**
	 * Adds a new staff to this musical piece.
	 *
	 * @param staff The MusicStaff to add.
	 */
	public void addStaff(MusicStaff staff) {
		staffs.add(staff);
	}

	/**
	 * Returns the tempo of this musical piece.
	 *
	 * @return The tempo in beats per minute (BPM).
	 */
	public int getTempo() {
		return tempo;
	}

	/**
	 * Returns an unmodifiable copy of the list of staffs in this piece.
	 *
	 * @return A List containing all MusicStaff objects.
	 */
	public List<MusicStaff> getStaffs() {
		return List.copyOf(staffs);
	}

	@Override
	public Iterator<MusicStaff> iterator() {
		return staffs.iterator();
	}
}
