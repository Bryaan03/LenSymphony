/**
 * LenSymphony - A simple music synthesizer library developed in Lens, France.
 * Copyright (c) 2025 Romain Wallon - UniversitÃ© d'Artois.
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

package fr.univartois.butinfo.lensymphony;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import fr.univartois.butinfo.lensymphony.cmd.CmdLine;
import fr.univartois.butinfo.lensymphony.musicxml.MusicXMLSaxParser;
import fr.univartois.butinfo.lensymphony.notes.AbstractNoteFactory;
import fr.univartois.butinfo.lensymphony.notes.Instrument;
import fr.univartois.butinfo.lensymphony.notes.MusicPiece;
import fr.univartois.butinfo.lensymphony.notes.MusicStaff;
import fr.univartois.butinfo.lensymphony.notes.Note;
import fr.univartois.butinfo.lensymphony.notes.SimpleNoteFactory;
import fr.univartois.butinfo.lensymphony.synthesizer.MusicSynthesizer;
import fr.univartois.butinfo.lensymphony.synthesizer.SynthesizeFactory;
import picocli.CommandLine;

/**
 * The LenSymphony class provides a simple application to synthesize and play music from a
 * MusicXML file.
 * This file must be provided as a command line argument to the application.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class LenSymphony {

    /**
     * The note factory used to create notes.
     */
    private static AbstractNoteFactory noteFactory = SimpleNoteFactory.getInstance();

    /**
     * Disables instantiation.
     */
    private LenSymphony() {
        throw new AssertionError("No LenSymphony instances for you!");
    }

    private static void createStaff(Instrument instrument, List<Note> notes, MusicPiece piece, double volume) {
        MusicStaff staff = new MusicStaff(instrument, volume);
        for (Note note : notes) {
            staff.addNote(note);
        }
        piece.addStaff(staff);
    }

    /**
     * The main method of the application.
     *
     * @param args The command line arguments, which must contain exactly the path to the
     *        MusicXML file to play.
     *
     * @throws Exception If any error occurs.
     */
    public static void main(String[] args) throws Exception {
        // Creating the SAX parser.
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        CmdLine cli = CmdLine.getInstance();
        new CommandLine(cli).parseArgs(args);
        cli.call();

        if (cli.getInputFile() == null) {
            System.err.println("Missing input file !");
            return;
        }

        String input = cli.getInputFile();
        String output = cli.getOutputFile();
        boolean play = cli.shouldPlay();
        
        // Parsing the MusicXML file.
        MusicXMLSaxParser handler = new MusicXMLSaxParser(noteFactory);
        saxParser.parse(new File(input), handler);

        // Creating a musical score from the parsed data.
        MusicPiece piece = new MusicPiece(handler.getTempo());
        for (Map.Entry<String, List<Note>> entry : handler.getParts().entrySet()) {

            String partName = entry.getKey();
            List<Note> notes = entry.getValue();
            
            List<Instrument> instruments = cli.getInstruments(partName);
            for (Instrument instrument : instruments) {
                double volume = cli.getVolumeForInstrument(instrument);
                createStaff(instrument, notes, piece, volume);
            }
        }

        
        MusicSynthesizer musicSynthesizer = SynthesizeFactory.createComposite(piece);

        // Synthesizing and playing the music.
        musicSynthesizer.synthesize();

        System.out.println("Processing piece : " + input);
        
        if (output != null) {
            System.out.println("Saving in : " + output);
            musicSynthesizer.save(output);
        }

        if (play) {
            System.out.println("Playing...");
            musicSynthesizer.play();
        }
    }

}