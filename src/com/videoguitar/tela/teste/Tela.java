package com.videoguitar.tela.teste;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Tela extends JFrame{

	private static final long serialVersionUID = 1L;

	Timer timer;

	public Tela() {
		setSize(500, 500);

		JButton botao = new JButton("Tocar Legenda");
		
		List< TextoLegenda > listaTextoLegenda = new ArrayList<>();
		
		listaTextoLegenda.add( new TextoLegenda( "Teste ", 6 ) );
		listaTextoLegenda.add( new TextoLegenda( "de ", 20 ) );
		listaTextoLegenda.add( new TextoLegenda( "texto ", 100 ) );
		listaTextoLegenda.add( new TextoLegenda( "legenda", 150 ) );
		
		Legenda draw = new Legenda( listaTextoLegenda );

		botao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				evento(draw);
			}
		});

		draw.add(botao);

		this.getContentPane().add(draw);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void evento(Legenda d) {

		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {

				d.tocarLegenda( timer );
			}

		};

		timer = new Timer(1, actionListener);
		timer.setInitialDelay(0);
		timer.start();
	}

	public static void main(String arg[]) {
		new Tela();
	}
}
