/**
 * Ce fichier fait partie du projet lensymphony.
 *
 * (c) 2025 pasca
 * Tous droits réservés.
 */

package fr.univartois.butinfo.lensymphony.synthesizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Le type CompositeMusicSynthesizer
 *
 * @author pasca
 *
 * @version 0.1.0
 */
public class CompositeMusicSynthesizer implements MusicSynthesizer {

    private final List<MusicSynthesizer> synthesizers = new ArrayList<>();

    private double[] samples = new double[0];

    // Add a synthesizer to the list
    public void add(MusicSynthesizer synthesizer) {
        synthesizers.add(synthesizer);
    }

    // synthesize override
    @Override
    public void synthesize() {
        for (MusicSynthesizer synth : synthesizers) {
            synth.synthesize();
        }

        if (synthesizers.isEmpty()) {
            samples = new double[0];
            return;
        }

        int length = 0;
        for (MusicSynthesizer synth : synthesizers) {
            int synthLength = synth.getSamples().length;
            if (synthLength > length) {
                length = synthLength;
            }
        }
        
        double[] mix = new double[length];

        for (MusicSynthesizer synth : synthesizers) {
            double[] voiceSamples = synth.getSamples();
            int synthLength = voiceSamples.length;
            for (int i = 0; i < synthLength; i++) {
                mix[i] += voiceSamples[i];
            }
        }

        int count = synthesizers.size();
        for (int i = 0; i < length; i++) {
            mix[i] /= count;
        }

        this.samples = mix;
    }

    // return a list of samples 
    @Override
    public double[] getSamples() {
        return samples;
    }

}


