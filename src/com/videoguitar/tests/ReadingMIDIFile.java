package com.videoguitar.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import com.videoguitar.listeners.MetaEventListenerImpl;

public class ReadingMIDIFile {

	private static final String[] NOTAS = getNotas();

	private static final Map<String, Set<String>> ACORDES = getAcordes();

	public static void main(String[] args)
			throws MidiUnavailableException, InvalidMidiDataException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		//

		Sequencer sequencer = MidiSystem.getSequencer();
		sequencer.open();

		// Creating a sequence.
		Sequence sequence = MidiSystem
				.getSequence(new File("/home/kafurtado/Downloads/midifiles/Raul_Seixas_-_Medo_da_chuva.kar"));

		Sequence newSeq = new Sequence(sequence.getDivisionType(), sequence.getResolution());
		Map<Integer, Integer> counts = new HashMap<>();
		Map<Long, Set<Integer>> ticks = new LinkedHashMap<>();
		Track newTrack;

		System.out.printf("--- INICIO ANÁLISE, TRACKS: %d\n", sequence.getTracks().length);

		for (int t = 0; t < sequence.getTracks().length; t++) {
			newTrack = newSeq.createTrack();
			Track track = sequence.getTracks()[t];

			for (int i = 0; i < track.size(); i++) {

				MidiEvent e = track.get(i);

//				// Track;event;tick;class
//				System.out.printf("%d;%d;%d;%s;", t, i, e.getTick(), e.getMessage().getClass().getName());
//
//				// Message params
//				if (e.getMessage() instanceof MetaMessage) {
//					MetaMessage m = (MetaMessage) e.getMessage();
//					System.out.printf("TP = %d, ST = %d, SZ = %d;", m.getType(), m.getStatus(), m.getLength());
//				} else if (e.getMessage() instanceof ShortMessage) {
//					ShortMessage m = (ShortMessage) e.getMessage();
//					System.out.printf("CM = %d, CH = %d, ST = %d, SZ = %d, DATA = [%d, %d];", m.getCommand(), m.getChannel(), m.getStatus(), m.getLength(), m.getData1(),
//								m.getData2());
//				}
//
//				int l = e.getMessage().getLength(); 
//				if (l > 0) {
//					System.out.print((int) e.getMessage().getMessage()[0]);
//					for (int ce = 1; ce < l; ce++) {
//						System.out.printf( ", %d", (int) e.getMessage().getMessage()[ce]);
//					}
//				}
//				
//				System.out.println();

				Integer command = new Integer(e.getMessage().getStatus() & 0xF0);
				Integer channel = new Integer(e.getMessage().getStatus() & 15);

				Integer count = counts.get(command);

				counts.put(command, count == null ? 1 : ++count);

				MidiMessage msg = null;

				if (e.getMessage() instanceof MetaMessage) {
					int type = ((MetaMessage) e.getMessage()).getType();
					byte[] data = ((MetaMessage) e.getMessage()).getData().clone();

					msg = new MetaMessage(type, data, data.length);
				}

				else if (e.getMessage() instanceof ShortMessage) {

					ShortMessage nota = (ShortMessage) e.getMessage();

					if (nota.getCommand() == 144 && nota.getData2() > 0) {

						Set<Integer> notas = ticks.get(e.getTick());

						if (notas == null) {
							notas = new TreeSet<>();
							ticks.put(e.getTick(), notas);
						}

						notas.add(nota.getData1());

						msg = new ShortMessage(nota.getCommand(), nota.getChannel(), nota.getData1(), nota.getData2());
					}

				}

				long tick = e.getTick();

//				if (msg instanceof MetaMessage) {
//					tick -= 1200;
//				}

				MidiEvent newEvent = new MidiEvent(msg, tick);

				try {
					newTrack.add(newEvent);
				} catch (NullPointerException ex) {

				}

			}

		}

		newTrack = newSeq.createTrack();

		String lastAcorde = "";
		for (Long tick : ticks.keySet()) {

			Iterator<Integer> itNotas = ticks.get(tick).iterator();

			Set<String> notas = new TreeSet<>();
			Set<Integer> notasInt = new TreeSet<>();
			int total = 0;
			while (itNotas.hasNext()) {
				Integer iNota = itNotas.next();
				String nota = NOTAS[iNota];
				notasInt.add(iNota);
//				if (!notas.contains(nota)) {
				notas.add(nota);
				total++;
//				}
			}
			if (total >= 3) {
//				String acorde = getAcorde(notas);
				if (tick == 7680L) {
					System.out.println("DEBUG !");
				}

				String acorde = getAcorde(notasInt);

				if (acorde != null && !acorde.isEmpty() && !lastAcorde.equals(acorde)) {

					System.out.printf("%d;%s;NOTAS;%s\n", tick, acorde, notas.toString());
					System.out.printf("%d;%s;VALORES;%s\n", tick, acorde, notasInt.toString());

					lastAcorde = acorde;
					acorde = "(" + acorde + " - " + tick + ") ";
					byte[] data = acorde.getBytes();// sb.toString().getBytes();
					MidiEvent newEvent = new MidiEvent(new MetaMessage(2, data.clone(), data.length), tick);
					newTrack.add(newEvent);
				}
			}

		}

		System.out.println("-- Comandos agrupados -------------");
		for (

		Integer key : counts.keySet()) {
			System.out.println(key + ": " + counts.get(key));
		}
		System.out.println("-----------------------------------");

		counts.clear();

//		for (int i = 0; i < sequence.getTracks()[0].size(); i++) {
//			MidiEvent e = sequence.getTracks()[0].get(i);
//			int length = e.getMessage().getMessage().length;
//			int i0 = length >= 1 ? e.getMessage().getMessage()[0] & 0xFE : -999;
//			int i1 = length >= 2 ? e.getMessage().getMessage()[1] & 0xFE : -998;
//			int i2 = length >= 3 ? e.getMessage().getMessage()[2] & 0xFE : -997;
//			System.out.println(e.getTick() + ": " + i0 + " - " + i1 + " - " + i2);
//			
//			if (i0 == 144 || i0 == 128) {
//				int nota = (int) e.getMessage().getMessage()[1] - 12;
//				e.getMessage().getMessage()[1] = (byte) nota; 
//			}
//		}

//		Thread.sleep(5000);

		sequencer.setSequence(sequence);

		sequencer.start();

		int v = 0;
		long lastTick = -1;

//		AccordImageListener accord = new AccordImageListener();
		MetaEventListener metaEvent = new MetaEventListenerImpl();

		int controllers[] = new int[128];
		for (int i = 0; i < controllers.length; i++) {
			controllers[i] = i;
		}

		sequencer.addMetaEventListener(metaEvent);
//		sequencer.addControllerEventListener(accord, controllers);

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

	private static Integer getNumeroNota(int nota) {
		int retorno = nota;
		while (retorno >= 12) {
			retorno -= 12;
		}

		return retorno;
	}

//	private static String getAcorde(Set<String> notas) {
//
//		Iterator<String> acordes = ACORDES.keySet().iterator();
//
//		while (acordes.hasNext()) {
//
//			String acorde = acordes.next();
//			if (notas.containsAll(ACORDES.get(acorde))) {
//				return acorde;
//			}
//
//		}
//
//		return null;
//	}

	private static String getAcorde(Set<Integer> notas) {

		Integer anotas[] = new Integer[notas.size()];
		anotas = notas.toArray(anotas);
		
//		E (12360)
//		36, 51, 54, 64, 68, 71		

		String acorde = "";
		for (int i = 0; i < anotas.length - 1; i++) {

			boolean dueto = false;
			boolean triade = false;
			boolean tetrade = false;
			
			if (anotas[i + 1] - anotas[i] < 5) {

				acorde = NOTAS[anotas[i]];

				if (anotas.length - i >= 4) { // Tétrade
					tetrade = anotas[i + 3] - anotas[i] == 10;
					triade = anotas[i + 2] - anotas[i] == 7;
					dueto = false; // despois nóis vê

				} else if (anotas.length - i == 3) { // Tríade
					triade = anotas[i + 2] - anotas[i] == 7;
					dueto = false; // despois nóis vê

				} else if (anotas.length - i == 2) { // Dueto
					dueto = false; // despois nóis vê
				}

				if (!dueto && !triade && !tetrade) {
					continue;
				}

				if (triade) {
					if (anotas[i + 1] - anotas[i] == 3) {
						acorde += "m";
					}
				}

				if (tetrade) {
					if (anotas[i + 3] - anotas[i] == 10) {
						acorde += "7";
					}
				}
				
				return acorde;

			}
		}

		return null;
	}

	private static Map<String, Set<String>> getAcordes() {

		Map<String, Set<String>> retorno = new HashMap<>();
		Set<String> notas;

		// D
		retorno.put("D", notas = new LinkedHashSet<>());
		notas.add("D");
		notas.add("F#");
		notas.add("A");

		// Em
		retorno.put("Em", notas = new LinkedHashSet<>());
		notas.add("E");
		notas.add("G");
		notas.add("B");

		// F#m
		retorno.put("F#m", notas = new LinkedHashSet<>());
		notas.add("F#");
		notas.add("A");
		notas.add("C#");

		// G
		retorno.put("G", notas = new LinkedHashSet<>());
		notas.add("G");
		notas.add("B");
		notas.add("D");

		// A7
//		retorno.put("A7", notas = new LinkedHashSet<>());
//		notas.add("A");
//		notas.add("C#");
//		notas.add("E");
//		notas.add("G");

		// A7
		retorno.put("A", notas = new LinkedHashSet<>());
		notas.add("A");
		notas.add("C#");
		notas.add("E");

		// Bm
		retorno.put("Bm", notas = new LinkedHashSet<>());
		notas.add("B");
		notas.add("D");
		notas.add("F#");

		return retorno;
	}

	private static String[] getNotas() {
		final String[] CROMATICA = new String[] { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
		final String[] retorno = new String[128];

		for (int i = 0, n = 0; i <= 127; i++) {
			retorno[i] = CROMATICA[n++];

			if (n == CROMATICA.length) {
				n = 0;
			}

		}

		return retorno;

	}

}
