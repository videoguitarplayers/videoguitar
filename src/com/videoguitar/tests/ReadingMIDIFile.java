package com.videoguitar.tests;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import com.videoguitar.listeners.AccordImageListener;

public class ReadingMIDIFile {

	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
		// TODO Auto-generated method stub
		// 
		
		Sequencer sequencer = MidiSystem.getSequencer();
		sequencer.open();

		// Creating a sequence.
		Sequence sequence = MidiSystem.getSequence(new File("/home/kafurtado/Downloads/THE EAGLES.Hotel California K.mid"));

		sequencer.setSequence(sequence);
		sequencer.start();

		int v = 0;
		long lastTick = -1;

		AccordImageListener accord = new AccordImageListener();

		int controllers[] = new int[128];
		for (int i = 0; i < controllers.length; i++) {
			controllers[i] = i;
		}
		
		sequencer.addControllerEventListener(accord, controllers);
		
		while (true) {
			
//			if (lastTick != sequencer.getTickPosition()) {				
//				lastTick = sequencer.getTickPosition();
//				System.out.print("\n" + lastTick + " - " + System.currentTimeMillis());
//			}
				
			// Exit the program when sequencer has stopped playing.
			if (!sequencer.isRunning()) {
				sequencer.close();
				System.exit(1);
			}

		}

	}
	
	
	
	
	

}
