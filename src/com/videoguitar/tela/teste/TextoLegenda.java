package com.videoguitar.tela.teste;

public class TextoLegenda {

	private String texto;
	private int delay;
	private int duracaoEfeito;
	private int posicaoDireitaTexto;

	public TextoLegenda(String texto, int duracaoEfeito) {
		this.texto = texto;
		this.duracaoEfeito = duracaoEfeito;
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

	public int getDuracaoEfeito() {
		return duracaoEfeito;
	}

	public void setDuracaoEfeito(int duracaoEfeito) {
		this.duracaoEfeito = duracaoEfeito;
	}

	public int getPosicaoDireitaTexto() {
		return posicaoDireitaTexto;
	}

	public void setPosicaoDireitaTexto(int posicaoDireitaTexto) {
		this.posicaoDireitaTexto = posicaoDireitaTexto;
	}

}
