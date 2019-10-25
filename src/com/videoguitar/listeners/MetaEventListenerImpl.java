package com.videoguitar.listeners;

import java.nio.charset.StandardCharsets;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

public class MetaEventListenerImpl implements MetaEventListener {

	public static final StringBuilder sb = new StringBuilder();
	
	@Override
	public void meta(MetaMessage meta) {

		if (meta.getType() == 1) {

			String s = new String(meta.getData(), StandardCharsets.ISO_8859_1);
			s = s.replace("\\", "\n\n");
			s = s.replace("/", "\n");
			
			System.out.print(s);
			sb.setLength(0);
			
		}
		
		else if (meta.getType() == 2) {
			String s = new String(meta.getData(), StandardCharsets.ISO_8859_1);
			
			if (sb.indexOf(s) < 0) {
				sb.setLength(0);
				sb.append(s);
				System.out.print(s);
			}
			
		}
	}

}
