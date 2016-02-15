package model;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Lance implements Jogada {

	protected Carta carta;
	protected Jogador jogador;

	public Lance(Carta carta, Jogador jogador) {
		super();
		this.carta = carta;
		this.jogador = jogador;
	}

	public Lance() {
		super();
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}
}
