package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * The {@code TimbaleSynthesizer} class is a singleton implementation of the
 * {@link NoteSynthesizer} interface that generates the sound of a timpani
 * (kettle drum).
 * 
 * This synthesizer produces a resonant, percussive sound by generating a
 * decaying sine wave whose frequency slightly decreases over time. The sound
 * begins with a high amplitude that fades exponentially, emulating the natural
 * damping of a timpani after being struck.
 * 
 * The generated frequency sweeps from the initial pitch of the note to
 * approximately 60% of that frequency over the note’s duration, giving the
 * sound a deep and realistic tonal decay.
 * 
 * @author (tibot duquesne)
 * @version 1.0
 */
public class TimbaleSynthesizer implements NoteSynthesizer {

	/**
	 * The single instance of this synthesizer (singleton pattern).
	 */
	private static final TimbaleSynthesizer INSTANCE = new TimbaleSynthesizer();

	/**
	 * Private constructor to enforce the singleton pattern.
	 */
	private TimbaleSynthesizer() {
	}

	/**
	 * Returns the single instance of the {@code TimbaleSynthesizer}.
	 *
	 * @return The unique instance of this synthesizer.
	 */
	public static TimbaleSynthesizer getInstance() {
		return INSTANCE;
	}

	/**
	 * Synthesizes the sound of a timpani for a given musical note.
	 * <p>
	 * The generated waveform is a decaying sine wave whose frequency decreases
	 * slightly over time, producing a resonant and realistic drum-like tone.
	 * </p>
	 *
	 * @param note   The {@link Note} to be synthesized.
	 * @param tempo  The tempo (in beats per minute) used to determine the note
	 *               duration.
	 * @param volume The amplitude (volume) of the generated sound, typically
	 *               between 0.0 and 1.0.
	 *
	 * @return An array of audio samples representing the synthesized timpani sound.
	 */
	@Override
	public double[] synthesize(Note note, int tempo, double volume) {
		double durationSeconds = note.getDuration(tempo) / 1000.0;

		if (durationSeconds <= 0) {
			return new double[0];
		}

		int totalSamples = (int) Math.round(SAMPLE_RATE * durationSeconds);

		if (note.getFrequency() == 0.0) {
			return new double[totalSamples];
		}

		double[] samples = new double[totalSamples];

		double fstart = note.getFrequency();
		double fend = 0.6 * fstart;

		for (int i = 0; i < totalSamples; i++) {
			double t = i / (double) SAMPLE_RATE;

			double f = fstart + (t * (fend - fstart) / durationSeconds);
			samples[i] = volume * Math.exp(-5 * t) * Math.sin(2 * Math.PI * f * t);
		}

		return samples;
	}
}
