package com.videoguitar.tela.teste;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Legenda extends JPanel {
	
	private static final long serialVersionUID = 1L;
	Font font = new Font("Dialog", Font.BOLD, 40);
	FontMetrics fontMetrics;

	float valorCor1 = 20;
	float valorCor2 = 21;
	
	String texto;

	Graphics2D g2D;

	Legenda( String texto ) {
		setSize(300, 300);
		setBackground(Color.white);
		fontMetrics = getFontMetrics(font);
		this.texto = texto;
	}
	
	public void paint(Graphics g) {
		this.g2D = (Graphics2D) g;

		g2D.setFont(font);

		this.setGradiente();
		this.setTextoLegenda( this.texto );
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
		valorCor1 += 2;
		valorCor2 += 2;

		this.setGradiente();
		this.repaint();
	}
}
