package com.videoguitar.tela.teste;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Legenda extends JPanel {
	
	private static final long serialVersionUID = 1L;
	Font font = new Font("Dialog", Font.BOLD, 40);
	FontMetrics fontMetrics;

	float valorCor1;
	float valorCor2;
	
	String texto;

	Graphics2D g2D;
	
	List<TextoLegenda> listaTextoLegenda;

	Legenda( List< TextoLegenda > listaTextoLegenda ) {
		setSize(300, 300);
		setBackground(Color.white);
		fontMetrics = getFontMetrics(font);
		this.listaTextoLegenda = listaTextoLegenda;
		this.texto = this.getTextoLegendaCompleta();
		this.valorCor1 = 20;
		this.valorCor2 = this.valorCor1 + 1;
	}
	
	private String getTextoLegendaCompleta() {
		StringBuilder retorno = new StringBuilder();
		
		for (TextoLegenda textoLegenda : this.listaTextoLegenda) {
			retorno.append( textoLegenda.getTexto() );
		}
		
		return retorno.toString();
	}

	public void paint(Graphics g) {
		this.g2D = (Graphics2D) g;

		g2D.setFont(font);

		this.setGradiente();
		this.setTextoLegenda( this.texto );

		this.preencherMetaDataTexto();		
	}
	

	private void preencherMetaDataTexto() {

		// posicao inicial do texto
		int tamanhoTextoAnterior = 20;
		
		for (int i = 0; i < this.listaTextoLegenda.size(); i++) {
			final double comprimentoTexto = font.getStringBounds( this.listaTextoLegenda.get(i).getTexto(), this.g2D.getFontRenderContext() ).getMaxX();
			tamanhoTextoAnterior += comprimentoTexto; 
			// salva o comprimento do texto
			this.listaTextoLegenda.get(i).setPosicaoDireitaTexto(tamanhoTextoAnterior);
			
			System.out.println("Comprimento: " + comprimentoTexto);
			
			// calcula o tempo de delay conforme o tempo de duracao
			int delay = (int) ( ( this.listaTextoLegenda.get(i).getDuracaoEfeito() / comprimentoTexto) + 0.5);
			this.listaTextoLegenda.get(i).setDelay(delay);
			
			System.out.println( "Delay: " +delay);
			
		}
	}

	private void setTextoLegenda(String texto2) {
		g2D.drawString( this.texto, 20, 200);
		
	}

	private void setGradiente() {
		GradientPaint gp = new GradientPaint( valorCor1, fontMetrics.getHeight(), Color.blue, 
				valorCor2,fontMetrics.getHeight(), Color.red);

		g2D.setPaint(gp);
	}

	public void atualizarGradiente() {
		valorCor1 += 1;
		valorCor2 += 1;

		this.setGradiente();
		this.repaint();
	}

	public void tocarLegenda(Timer timer) {
		
		for (TextoLegenda textoLegenda : listaTextoLegenda) {
			timer.setDelay( textoLegenda.getDelay() );
			if( textoLegenda.getPosicaoDireitaTexto() >= this.valorCor1 ){
				this.atualizarGradiente();
				return;
			}
		}
	}
	
}
