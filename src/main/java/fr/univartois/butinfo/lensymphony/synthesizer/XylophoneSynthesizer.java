package fr.univartois.butinfo.lensymphony.synthesizer;

import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * Le type XylophoneSynthesizer
 *
 * @author pasca
 *
 * @version 0.1.0
 */
public class XylophoneSynthesizer implements NoteSynthesizer {
    /**
     * The unique instance 
     */
    private static final XylophoneSynthesizer INSTANCE = new XylophoneSynthesizer();
    
	/**
	 * n = harmonics
	 */ 
    private final int n = 5;
   
    /**
     * Private construct (unique instance)
     */ 
    private XylophoneSynthesizer() {}
    
    /**
     * Get the unique instance 
     */
    public static XylophoneSynthesizer getInstance() {
        return INSTANCE;
    }
    
	/**
	 * Synthesizes a note to get a xylophone
	 *
	 * @param note   The note to synthesize
	 * @param tempo  The tempo in beats per minute
	 * @param volume The base volume
	 * @return An array of doubles representing the note's samples of a xylophone
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
        double f = note.getFrequency();

        for (int i = 0; i < totalSamples; i++) {
            double t = i / (double)SAMPLE_RATE;

            double sum = 0.0;

            double currentFreq = f;
            for (int j = 0; j < n; j++) {
                sum += Math.sin(2 * Math.PI * currentFreq * t) * Math.exp(-(2 * j + 1));
                currentFreq *= 2;
            }

            samples[i] = volume * Math.exp(-3 * t) * (sum / n);
        }

        return samples;
    }
}
