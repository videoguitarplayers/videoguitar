package com.videoguitar.tests;

// Java program showing the implementation of a simple record 
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import com.videoguitar.common.Nota;

public class FirstSteps {
	
	public static final int SEMIBREVE = 16;
	
	public static final int MINIMA = 8;
	
	public static final int SEMINIMA = 4;
	
	public static final int COLCHEIA = 2;
	
	public static final int SEMICOLCHEIA = 1;

	public static final int SI2 = 59; 
	
	public static final int DO3 = 60; 
			
	public static final int RE3 = 62; 
	
	public static final int MI3 = 64;
	
	public static final int FA3 = 65;
	
	public static final int SOL3 = 67; 
	
	public static final int LA3 = 69;
	
	public static final int SI3 = 71;
	
	public static final int DO4 = 72;
	
	public static final int SUSTENIDO = 1;
	
	public static final int BEMOL = -1;
	
	public static void main(String[] args) {

		System.out.println("Wait, the song will start");

		FirstSteps player = new FirstSteps();
		player.setUpPlayer(10);
	}

	public void setUpPlayer(int numOfNotes) {

		try {

			// A static method of MidiSystem that returns
			// a sequencer instance.
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();

			// Creating a sequence.
			Sequence sequence = new Sequence(Sequence.PPQ, 4);

			// PPQ(Pulse per ticks) is used to specify timing
			// type and 4 is the timing resolution.

			// Creating a track on our sequence upon which
			// MIDI events would be placed
			Track track = sequence.createTrack();
			
//           # #  # # #  # #  # # #  # #  # # #			
//			C D EF G A BC D EF G A BC D EF G A BC

//			int notas[] = { 40, 45, 50, 55, 59, 64 }; // cordas violão
//			Nota notas[] = { new Nota( 0, 16 ), new Nota( 60, 2 ), new Nota( 60, 2 ), new Nota( 62, 4 ), new Nota( 60, 4 ), new Nota( 65, 4 ), new Nota( 64, 8 ) }; // parabéns
//			int notas[] = { 48, 52, 55 }; // c - e - g (acorde C)
//			int notas[] = { 57, 60, 64 }; // a - c - e (acorde Am)
//			Nota notas[] = { new Nota(46, 1), new Nota(49, 1), new Nota(50, 1), new Nota(51, 1), null, new Nota(46, 1),
//					new Nota(49, 1), new Nota(50, 1), new Nota(51, 1), new Nota(52, 1), new Nota(51, 1),
//					new Nota(51, 1), new Nota(51, 1), new Nota(50, 1) }; // nem um dia
//			Nota notas[] = { new Nota(0, 2), new Nota(67, 3), new Nota(67, 2), new Nota(69, 2), new Nota(67, 2), new Nota(72, 3), new Nota(71, 4) }; // parabéns
//			int d = SEMICOLCHEIA;
//			Nota notas[] = { new Nota(0, d), new Nota(96, d), new Nota(0, d), new Nota(60, d), new Nota(62, d), new Nota(64, d), new Nota(65, d), new Nota(67, d), new Nota(69, d), new Nota(71, d), new Nota(72, d) }; // Escala DO
			
			// Hotel California - Eagles
			Nota notas[] = { new Nota(0, SEMIBREVE), new Nota(FA3 + SUSTENIDO, COLCHEIA), new Nota(SI2, SEMICOLCHEIA), new Nota(RE3, SEMICOLCHEIA), new Nota(SI2, SEMICOLCHEIA), new Nota(FA3 + SUSTENIDO, SEMICOLCHEIA), // 1
					new Nota(RE3, SEMICOLCHEIA), new Nota(SI2, SEMICOLCHEIA), new Nota(SI3, SEMINIMA), new Nota(RE3, COLCHEIA), new Nota(MI3, COLCHEIA), // 1
					new Nota(LA3 + SUSTENIDO, COLCHEIA), new Nota(MI3, SEMICOLCHEIA), new Nota(FA3 + SUSTENIDO, SEMICOLCHEIA), new Nota(MI3, SEMICOLCHEIA), new Nota(DO4 + SUSTENIDO, SEMICOLCHEIA), // 2
					new Nota(FA3 + SUSTENIDO, SEMICOLCHEIA), new Nota(MI3, SEMICOLCHEIA), new Nota(LA3 + SUSTENIDO, MINIMA) // 2		
			};
			
//			Nota notas[] = new Nota[60];
			
//			for ( int i = 0; i < notas.length; i++) {
//				notas[i] = new Nota(60, 1);
//			}
			
			int tick = 0;
			int ajusteTom = 12 * 0;
			
//			for (int a = 1; a <= 8; a++) {
			for (int i = 0; i < notas.length; i++) {
				if (notas[i].numeroNota > 0) {
					track.add(makeEvent(144, 1, notas[i].numeroNota + ajusteTom, 100, tick));
					track.add(makeEvent(128, 1, notas[i].numeroNota + ajusteTom, 100, tick + notas[i].duracao));
				}
				tick += (notas[i].duracao);
			}
//				ajusteTom += 2;
//			}

			// Setting our sequence so that the sequencer can
			// run it on synthesizer
			sequencer.setSequence(sequence);

			// Specifies the beat rate in beats per minute.
			sequencer.setTempoInBPM(75);

			// Sequencer starts to play notes
			sequencer.start();

			while (true) {

				// Exit the program when sequencer has stopped playing.
				if (!sequencer.isRunning()) {
					sequencer.close();
					System.exit(1);
				}
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	public MidiEvent makeEvent(int command, int channel, int note, int velocity, long tick) {

		MidiEvent event = null;

		try {

			// ShortMessage stores a note as command type, channel,
			// instrument it has to be played on and its speed.
			ShortMessage a = new ShortMessage();
			a.setMessage(command, channel, note, velocity);

			// A midi event is comprised of a short message(representing
			// a note) and the tick at which that note has to be played
			event = new MidiEvent(a, tick);
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return event;
	}
}
