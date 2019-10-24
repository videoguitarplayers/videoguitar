package com.videoguitar.tela.teste;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Tela extends JFrame{

	private static final long serialVersionUID = 1L;

	Timer timer;

	public Tela() {
		setSize(500, 500);

		JButton botao = new JButton("Tocar Legenda");
		Legenda draw = new Legenda( "Testeeeeeeeeeeeeeee" );

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

				if (d.valorCor1 < 400) {
					d.atualizarGradiente();
					timer.setDelay(60);

					if (d.valorCor1 >= 100 && d.valorCor1 <= 200) {
						timer.setDelay(20);
					}

					if (d.valorCor1 >= 200) {
						timer.setDelay(100);
					}
				} else {
					timer.stop();
					System.err.println("Timer stopped");
				}

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
