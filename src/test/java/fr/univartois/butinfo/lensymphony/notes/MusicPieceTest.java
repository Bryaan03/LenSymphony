package fr.univartois.butinfo.lensymphony.notes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MusicPieceTest {

	@Test
	void testMusicPieceStoresTempo() {
		MusicPiece piece = new MusicPiece(120);
		assertEquals(120, piece.getTempo());
	}

	@Test
	void testMusicPieceStoresStaves() {
		MusicPiece piece = new MusicPiece(120);
		MusicStaff staff = new MusicStaff(Instrument.PIANO, 0.8);

		piece.addStaff(staff);

		assertEquals(1, piece.getStaffs().size());
		assertEquals(staff, piece.getStaffs().get(0));
	}
}
