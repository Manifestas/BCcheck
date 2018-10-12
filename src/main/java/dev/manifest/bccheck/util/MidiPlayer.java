package dev.manifest.bccheck.util;

import javax.sound.midi.*;


public class MidiPlayer {

    public static void playNewProduct() {

        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            MidiEvent changeInstrument = makeEvent(ShortMessage.PROGRAM_CHANGE, 1, 40, 0,1);
            track.add(changeInstrument);

            MidiEvent noteOn = makeEvent(ShortMessage.NOTE_ON, 1, 70, 100, 1);
            track.add(noteOn);

            MidiEvent noteOff = makeEvent(ShortMessage.NOTE_OFF, 1, 70, 100, 6);
            track.add(noteOff);

            player.setSequence(seq);
            player.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void playAlarmSound() {

        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            // change instrument
            MidiEvent changeInstrument = makeEvent(ShortMessage.PROGRAM_CHANGE, 1, 70, 0, 1);
            track.add(changeInstrument);

            MidiEvent firstNoteOn = makeEvent(ShortMessage.NOTE_ON, 1, 40, 100, 1);
            track.add(firstNoteOn);

            MidiEvent firstNoteOff = makeEvent(ShortMessage.NOTE_OFF, 1, 40, 100, 4);
            track.add(firstNoteOff);

            MidiEvent secondNoteOn = makeEvent(ShortMessage.NOTE_ON, 1, 40, 127, 5);
            track.add(secondNoteOn);

            MidiEvent secondNoteOff = makeEvent(ShortMessage.NOTE_OFF, 1, 40, 127, 12);
            track.add(secondNoteOff);

            player.setSequence(seq);
            player.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static MidiEvent makeEvent(int command, int channel, int one, int two, int tick) throws InvalidMidiDataException {
        MidiEvent event = null;
        ShortMessage a = new ShortMessage();
        a.setMessage(command, channel, one, two);
        event = new MidiEvent(a, tick);
        return event;
    }

}
