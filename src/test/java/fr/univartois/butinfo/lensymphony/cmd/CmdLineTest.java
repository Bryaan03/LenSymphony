package fr.univartois.butinfo.lensymphony.cmd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.lensymphony.notes.Instrument;

public class CmdLineTest {

    private CmdLine cmd;

    @BeforeEach
    void setUp() {
        cmd = CmdLine.getInstance();
    }

    @Test
    void testProcessVoiceMappings_multipleSamePart() {
        cmd.getVoiceMappings().clear();
        cmd.getVoiceMappings().add("P1:Xylophone");
        cmd.getVoiceMappings().add("P1:Piano");
        cmd.processVoiceMappings();

        List<String> instruments = cmd.getVoices().get("P1");
        assertNotNull(instruments);
        assertEquals(2, instruments.size());
        assertTrue(instruments.contains("Xylophone"));
        assertTrue(instruments.contains("Piano"));
    }

    @Test
    void testConvertVoice() {
        assertEquals(Instrument.PIANO, cmd.convertVoice("piano"));
        assertEquals(Instrument.XYLOPHONE, cmd.convertVoice("Xylophone"));
        assertEquals(Instrument.SYNTHESIZER, cmd.convertVoice("unknown"));
        assertEquals(Instrument.SYNTHESIZER, cmd.convertVoice(null));
    }

    @Test
    void testGetInstruments_single() {
        cmd.getVoiceMappings().clear();
        cmd.getVoiceMappings().add("P2:Flute");
        cmd.processVoiceMappings();

        List<Instrument> instruments = cmd.getInstruments("P2");
        assertEquals(1, instruments.size());
        assertEquals(Instrument.FLUTE, instruments.get(0));
    }

    @Test
    void testGetInstruments_multiple() {
        cmd.getVoiceMappings().clear();
        cmd.getVoiceMappings().add("P3:Xylophone");
        cmd.getVoiceMappings().add("P3:Piano");
        cmd.processVoiceMappings();

        List<Instrument> instruments = cmd.getInstruments("P3");
        assertEquals(2, instruments.size());
        assertTrue(instruments.contains(Instrument.XYLOPHONE));
        assertTrue(instruments.contains(Instrument.PIANO));
    }

    @Test
    void testGetInstruments_default() {
        List<Instrument> instruments = cmd.getInstruments("PUnknown");
        assertEquals(1, instruments.size());
        assertEquals(Instrument.SYNTHESIZER, instruments.get(0));
    }

    @Test
    void testProcessVolumeMappings_valid() {
        cmd.getVolumeMappings().clear();
        cmd.getVolumeMappings().add("Flute:0.8");
        cmd.getVolumeMappings().add("Piano:0.5");
        cmd.processVolumeMappings();

        assertEquals(0.8, cmd.getInstrumentVolumes().get("flute"));
        assertEquals(0.5, cmd.getInstrumentVolumes().get("piano"));
    }
}
