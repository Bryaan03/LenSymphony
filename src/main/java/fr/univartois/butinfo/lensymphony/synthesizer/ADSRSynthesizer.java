package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * A synthesizer that applies an ADSR (Attack, Decay, Sustain, Release) envelope
 * to the output of another synthesizer. This class is a decorator for
 * {@link NoteSynthesizer}, modifying the amplitude of each sample according to
 * the ADSR parameters.
 * 
 * @author Tibot
 * @version 1.0.0
 */
public class ADSRSynthesizer extends AbstractSynthesize {

	/**
	 * Attack time (fraction of note duration).
	 */
	private final double a;

	/**
	 * Decay time (fraction of note duration).
	 */
	private final double d;

	/**
	 * Sustain level (0.0 to 1.0).
	 */
	private final double s;

	/**
	 * Release time (fraction of note duration).
	 */
	private final double r;

	/**
	 * Creates a new ADSRSynthesizer that decorates another synthesizer.
	 *
	 * @param decorated The synthesizer to decorate
	 * @param a         Attack time as a fraction of note duration
	 * @param d         Decay time as a fraction of note duration
	 * @param s         Sustain level (0.0 to 1.0)
	 * @param r         Release time as a fraction of note duration
	 */
	public ADSRSynthesizer(NoteSynthesizer decorated, double a, double d, double s, double r) {
		super(decorated);
		this.a = a;
		this.d = d;
		this.s = s;
		this.r = r;
	}

	/**
	 * Computes the ADSR amplitude multiplier for a given time.
	 *
	 * @param t The elapsed time since the start of the note (in seconds)
	 * @param duration The total duration of the note (in seconds)
	 * @return The amplitude multiplier (between 0.0 and 1.0)
	 */
	private double aDSR(double t, double duration) {
		if (duration > 0) {
			try {
				if (t < a * duration)
					return t / (a * duration); // Attack phase
				if (t < (a + d) * duration)
					return 1 - ((t - a * duration) / (d * duration) * (1 - s)); // Decay phase
				if (t < duration - r * duration)
					return s; // Sustain phase
				return s * (1 - ((t - (duration - r * duration)) / (r * duration))); // Release phase
			} catch (ArithmeticException e) {
				System.out.println("Error: division by zero (a, d, s, r should be > 0)");
				return 0.0;
			}
		} else {
			return 0.0;
		}
	}

	/**
	 * Synthesizes a note and applies the ADSR envelope to its amplitude.
	 *
	 * @param note   The note to synthesize
	 * @param tempo  The tempo in beats per minute
	 * @param volume The base volume
	 * @return An array of doubles representing the note's samples with ADSR applied
	 */
	@Override
	public double[] synthesize(Note note, int tempo, double volume) {
		double[] base = super.synthesize(note, tempo, volume);
		double duration = note.getDuration(tempo);
		int totalSamples = base.length;

		for (int i = 0; i < totalSamples; i++) {
			double t = i / (double) SAMPLE_RATE;
			double factor = aDSR(t, duration);
			base[i] *= factor;
		}

		return base;
	}
}
