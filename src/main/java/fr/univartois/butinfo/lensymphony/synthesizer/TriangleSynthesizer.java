package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

public class TriangleSynthesizer implements NoteSynthesizer {
	/**
	 * The unique instance 
	 */
    private static final TriangleSynthesizer INSTANCE = new TriangleSynthesizer();
    
	/**
	 * n = frequency
	 */ 
    private final int n = 5;

	/**
	 * Private construct (unique instance)
	 */ 
    private TriangleSynthesizer() {}

	/**
	 * Get the unique instance 
	 */
    public static TriangleSynthesizer getInstance() {
        return INSTANCE;
    }
    
    
	/**
	 * Synthesizes a note to get a triangle
	 *
	 * @param note   The note to synthesize
	 * @param tempo  The tempo in beats per minute
	 * @param volume The base volume
	 * @return An array of doubles representing the note's samples of a triangle
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

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double) SAMPLE_RATE;
            double s = 0;

            for (int j = 1; j <= n; j++) {
                s += Math.exp(-5 * (0.5 + 0.3 * j)) * Math.sin(4 * Math.PI * (2000 + 800 * j) * t);
            }

            samples[i] = volume * s;
        }

        return samples;
    }
}
