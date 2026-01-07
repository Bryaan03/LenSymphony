/**
 * LenSymphony - A simple music synthesizer library developed in Lens, France.
 * Copyright (c) 2025 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package fr.univartois.butinfo.lensymphony.synthesizer;

import java.util.Arrays;

import fr.univartois.butinfo.lensymphony.notes.MusicStaff;
import fr.univartois.butinfo.lensymphony.notes.Note;

/**
 * The SimpleMusicSynthesizer allows to synthesize a sequence of notes into an audio
 * stream.
 * The audio stream can be played or saved to a WAV file.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class SimpleMusicSynthesizer implements MusicSynthesizer {

    /**
     * The default volume level for the notes.
     */
    //private static final double DEFAULT_VOLUME = 0.5;
	private final double volume;

    /**
     * The music staff
     */
    private MusicStaff staff;

    /**
     * The synthesized audio samples as a double array.
     */
    private double[] samples;
    
    /**
     * The tempo
     */
    int tempo;

    /**
     * Creates a new MusicSynthesizer.
     *
     * @param staff The music staff to synthesize.
     * @param tempo The tempo in beats per minute (BPM).
     */
    public SimpleMusicSynthesizer(MusicStaff staff, int tempo, double volume) {
        this.staff = staff;
        this.tempo = tempo;
        this.volume = volume;
        this.samples = new double[0];
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.lensymphony.synthesizer.IMusicSynthesizer#synthesize()
     */
    @Override
    public void synthesize() {
        NoteSynthesizer synthesizer = staff.getInstrument().getSynthesizer();

        for (Note note : staff) {
            // Synthesizing the sound samples for this note.
            double[] noteSamples = synthesizer.synthesize(note, tempo, volume);

            // Appending the samples to the overall audio stream.
            int previousLength = samples.length;
            samples = Arrays.copyOf(samples, samples.length + noteSamples.length);
            System.arraycopy(noteSamples, 0, samples, previousLength, noteSamples.length);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.lensymphony.synthesizer.IMusicSynthesizer#getSamples()
     */
    @Override
    public double[] getSamples() {
        return samples;
    }

}
