package com.videoguitar.listeners;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.ShortMessage;

import com.videoguitar.common.Nota;

public class AccordImageListener implements ControllerEventListener {
	
	private Nota[] musica;

	@Override
	public void controlChange(ShortMessage event) {
//		System.out.println("Passou no evento");
		System.out.println(event.getCommand() + " - " + event.getData1() + " - " + event.getData2());
	}
	
	public void setMusica(Nota[] musica) {
		this.musica = musica;
	}
	

}
