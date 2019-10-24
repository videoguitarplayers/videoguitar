package com.videoguitar.tela.teste;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
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

		this.preencherTamanhoTexto();
	}
	

	private void preencherTamanhoTexto() {

		// posicao inicial do texto
		int tamanhoTextoAnterior = 20;
		
		for (int i = 0; i < this.listaTextoLegenda.size(); i++) {
			tamanhoTextoAnterior += font.getStringBounds( this.listaTextoLegenda.get(i).getTexto(), this.g2D.getFontRenderContext() ).getMaxX(); 
			this.listaTextoLegenda.get(i).setTemanhoTexto(tamanhoTextoAnterior);
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
			if( textoLegenda.getTemanhoTexto() >= this.valorCor1 ){
				this.atualizarGradiente();
			}
		}
		
		
//		if (this.valorCor1 < 400) {
//			this.atualizarGradiente();
//			timer.setDelay(60);
//
//			if (this.valorCor1 >= 100 && this.valorCor1 <= 200) {
//				timer.setDelay(20);
//			}
//
//			if (this.valorCor1 >= 200) {
//				timer.setDelay(100);
//			}
//		} else {
//			timer.stop();
//			System.err.println("Timer stopped");
//		}	
	}
	
}
