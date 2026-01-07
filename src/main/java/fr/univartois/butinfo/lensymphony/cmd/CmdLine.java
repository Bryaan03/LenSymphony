package fr.univartois.butinfo.lensymphony.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import fr.univartois.butinfo.lensymphony.notes.Instrument;
import fr.univartois.butinfo.lensymphony.synthesizer.NoteSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.Synthesize;
import fr.univartois.butinfo.lensymphony.synthesizer.TriangularSynthesizer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

@Command(
    name = "LenSymphony",
    description = "Synthesize and play a XML file.",
    mixinStandardHelpOptions = true
)
public class CmdLine implements Callable<Integer> {
    private static final CmdLine INSTANCE = new CmdLine();

    @Spec
    CommandSpec spec;

    @Option(names = {"-i", "--input"}, required = true, description = "Input MusicXML file")
    private String inputFile;

    @Option(names = {"-o", "--output"}, description = "Output audio file (optional)")
    private String outputFile;
    
    @Option(names = {"-synth"}, description = "Which synthe do you want to use")
    private String synthName;

    @Option(names = {"-p", "--play"}, description = "Play the piece in real time")
    private boolean play;

    @Option(names = {"-v", "--voice"}, description = "Link voice to instrument (ex: -v P1:Xylophone)", arity = "1")
    private List<String> voiceMappings = new ArrayList<>();
    
    @Option(
    	    names = {"--volume", "-vol"},
    	    description = "Set volume for an instrument (e.g., --volume Flute:0.8). Can be used multiple times.",
    	    arity = "1"
    	)
    private List<String> volumeMappings = new ArrayList<>();

    private final Map<String, List<String>> voices = new HashMap<>();
    private Map<String, Double> instrumentVolumes = new HashMap<>();

    private CmdLine() {}

    public static CmdLine getInstance() {
        return INSTANCE;
    }

    @Override
    public Integer call() {
        processVoiceMappings();
        processVolumeMappings();
        printSummary();
        return 0;
    }

    public void processVoiceMappings() {
        for (String mapping : getVoiceMappings()) {
            String[] parts = mapping.split(":");
            if (parts.length != 2) {
                throw new ParameterException(spec.commandLine(), "Invalid format, example: -v P1:Xylophone");
            }
            String part = parts[0];
            String instrument = parts[1];

            getVoices().computeIfAbsent(part, k -> new ArrayList<>()).add(instrument);
        }
    }

    
	public void processVolumeMappings() {
	    for (String mapping : getVolumeMappings()) {
	        String[] parts = mapping.split(":");
	        if (parts.length != 2) {
	            System.err.println("Invalid volume format: " + mapping + " (expected Instrument:Value)");
	            continue;
	        }
	        try {
	            getInstrumentVolumes().put(parts[0].toLowerCase(), Double.parseDouble(parts[1]));
	        } catch (NumberFormatException e) {
	            System.err.println("Invalid volume value for " + parts[0] + ": " + parts[1]);
	        }
	    }
	}
	
	public NoteSynthesizer getNoteInstance() {
		if (synthName == null) {
			return Synthesize.getInstance();
		}
		switch (synthName) {
			case "pur":
				return Synthesize.getInstance();
			case "triangular":
				return TriangularSynthesizer.getInstance();
			default:
				return Synthesize.getInstance();
				
		}
	}
   
    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public boolean shouldPlay() {
        return play;
    }

    public Instrument convertVoice(String voice) {
        if (voice == null) {
        	return Instrument.SYNTHESIZER;
        }

        switch (voice.toLowerCase()) {
            case "synthesizer": return Instrument.SYNTHESIZER;
            case "violin": return Instrument.VIOLIN;
            case "piano": return Instrument.PIANO;
            case "guitare": return Instrument.GUITARE;
            case "flute": return Instrument.FLUTE;
            case "cymbale": return Instrument.CYMBALE;
            case "snare": return Instrument.SNARE;
            case "bass": return Instrument.BASS;
            case "triangle": return Instrument.TRIANGLE;
            case "timbale": return Instrument.TIMBALE;
            case "xylophone": return Instrument.XYLOPHONE;
            default: return Instrument.SYNTHESIZER;
        }
    }

    public List<Instrument> getInstruments(String partName) {
        List<String> instrumentNames = getVoices().getOrDefault(partName, List.of("SYNTHESIZER"));
        List<Instrument> instruments = new ArrayList<>();
        for (String name : instrumentNames) {
            instruments.add(Instrument.valueOf(name.toUpperCase()));
        }
        return instruments;
    }


	public double getVolumeForInstrument(Instrument instrument) {
	    return getInstrumentVolumes().getOrDefault(instrument.name().toLowerCase(), 0.5);
	}

    public void printSummary() {
        System.out.println("=== Command Interface ===");
        System.out.println("Input file: " + inputFile);
        System.out.println("Output file: " + (outputFile != null ? outputFile : "none"));
        System.out.println("Real-time play: " + (play ? "yes" : "no"));
        if (getVoices().isEmpty()) {
            System.out.println("No configured voices (using defaults)");
        } else {
            System.out.println("Configured voices: " + getVoices());
        }
        System.out.println("==========================");
    }

    /**
     * Returns the voiceMappings attribute of this CmdLine instance.
     *
     * @return The voiceMappings attribute of this CmdLine instance.
     */
    public List<String> getVoiceMappings() {
        return voiceMappings;
    }

    /**
     * Sets the voiceMappings attribute of this CmdLine instance.
     *
     * @param voiceMappings The new value of the voiceMappings attribute for this CmdLine instance.
     */
    public void setVoiceMappings(List<String> voiceMappings) {
        this.voiceMappings = voiceMappings;
    }

    /**
     * Returns the voices attribute of this CmdLine instance.
     *
     * @return The voices attribute of this CmdLine instance.
     */
    public Map<String, List<String>> getVoices() {
        return voices;
    }

    /**
     * Returns the volumeMappings attribute of this CmdLine instance.
     *
     * @return The volumeMappings attribute of this CmdLine instance.
     */
    public List<String> getVolumeMappings() {
        return volumeMappings;
    }

    /**
     * Sets the volumeMappings attribute of this CmdLine instance.
     *
     * @param volumeMappings The new value of the volumeMappings attribute for this CmdLine instance.
     */
    public void setVolumeMappings(List<String> volumeMappings) {
        this.volumeMappings = volumeMappings;
    }

    /**
     * Returns the instrumentVolumes attribute of this CmdLine instance.
     *
     * @return The instrumentVolumes attribute of this CmdLine instance.
     */
    public Map<String, Double> getInstrumentVolumes() {
        return instrumentVolumes;
    }

    /**
     * Sets the instrumentVolumes attribute of this CmdLine instance.
     *
     * @param instrumentVolumes The new value of the instrumentVolumes attribute for this CmdLine instance.
     */
    public void setInstrumentVolumes(Map<String, Double> instrumentVolumes) {
        this.instrumentVolumes = instrumentVolumes;
    }
}
