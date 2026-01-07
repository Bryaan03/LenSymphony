package fr.univartois.butinfo.lensymphony.notes;

import fr.univartois.butinfo.lensymphony.cmd.CmdLine;
import fr.univartois.butinfo.lensymphony.synthesizer.ADSRSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.BassDrumSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.ComplexHarmonicSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.CymbaleSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.HarmonicSynthesize;
import fr.univartois.butinfo.lensymphony.synthesizer.NoiseSynthesize;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.SnareSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.Synthesize;
import fr.univartois.butinfo.lensymphony.synthesizer.TimbaleSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.TriangleSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.VibratoSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.XylophoneSynthesizer;

public enum Instrument {
    PUR(Synthesize.getInstance()),
    SYNTHESIZER(new HarmonicSynthesize(CmdLine.getInstance().getNoteInstance(), 18)), // synthesizer using the class HarmonicSynthesize using the instance and the number of harmonics
    VIOLIN(new VibratoSynthesizer(new ADSRSynthesizer(new HarmonicSynthesize(Synthesize.getInstance(), 10), 0.1, 0.2, 0.7, 0.3), 0.01, 5)),
    PIANO(
            new ADSRSynthesizer(
                    new ComplexHarmonicSynthesizer(
                            CmdLine.getInstance().getNoteInstance(),
                            10,
                            i -> i,
                            (i, t) -> Math.exp(-2 * i * t) / i
                    ),
                    0.01, 0.3, 0.2, 0.5
            )
    ),

    GUITARE(
            new VibratoSynthesizer(
                    new ADSRSynthesizer(
                            new ComplexHarmonicSynthesizer(
                                    CmdLine.getInstance().getNoteInstance(),
                                    8,
                                    i -> i,
                                    (i, t) -> 1.5 / i
                            ),
                            0.008, 0.05, 0.2, 2.5
                    ),
                    0.02, 3
            )
    ),

    FLUTE(
            new VibratoSynthesizer(
                new ADSRSynthesizer(
                    new ComplexHarmonicSynthesizer(
                        new NoiseSynthesize(CmdLine.getInstance().getNoteInstance(), 0.003),
                        5,
                        i -> 2 * i - 1,
                        (i, t) -> 1.0 / Math.pow(3, i - 1)
                    ),
                    0.09, 0.0, 1.0, 0.3
                ),
                0.01, 5
            )
        ),
    CYMBALE(CymbaleSynthesizer.getInstance()),
    SNARE(SnareSynthesizer.getInstance()),
    BASS(BassDrumSynthesizer.getInstance()),
    TRIANGLE(TriangleSynthesizer.getInstance()),
    TIMBALE(TimbaleSynthesizer.getInstance()),
    XYLOPHONE(XylophoneSynthesizer.getInstance());

    private final NoteSynthesizer synthesizer;

    Instrument(NoteSynthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

    // Return the synthesize
    public NoteSynthesizer getSynthesizer() {
        return synthesizer;
    }
}