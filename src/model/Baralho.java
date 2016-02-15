package model;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.List;
import java.util.Random;

public class Baralho implements Jogada{
	
	protected List<Carta> cartas;

	public Baralho() {
		this.populaBaralho();
	}

	public List<Carta> getCartas () {
		return this.cartas;
	}
	
	public Carta getCartaRandom () {
		Random rand = new Random();
		int random = rand.nextInt(this.getCartas().size());
		return this.getCartas().remove(random);
	}
	
	public void populaBaralho () {
		FactoryCarta factory = new FactoryCarta();
		this.cartas = factory.criaCartas();
	}
}
