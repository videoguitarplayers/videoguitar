package com.videoguitar.tela.teste;

public class TextoLegenda {

	private String texto;
	private int delay;
	private int temanhoTexto;
	
	public TextoLegenda( String texto, int delay ){
		this.texto = texto;
		this.delay = delay;
	}
	
	
	public int getTemanhoTexto() {
		return temanhoTexto;
	}
	public void setTemanhoTexto(int temanhoTexto) {
		this.temanhoTexto = temanhoTexto;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	
}
