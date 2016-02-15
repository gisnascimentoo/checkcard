package model;

import java.util.ArrayList;
import java.util.List;

//import br.ufsc.inf.leobr.cliente.Jogada;

public class FactoryCarta {

	/**
	 * Create the 56 cards of the deck. 13 Spades (black) 13 Hearts (red) 13
	 * Diamonds (red) 13 Clubs (black) 4 Jokers (2 red, 2 black)
	 * 
	 * card name: numero + suit
	 * 
	 */
	
	//TODO
	//Arrumar como pegar imagem
	public List<Carta> criaCartas() {
		List<Carta> cartas = new ArrayList<Carta>();

		for (int numero = 1; numero <= 13; numero++) {
			cartas.add(new Carta(numero, TipoCarta.Naipe.ESPADAS, TipoCarta.Cor.PRETO/*, 
					new javax.swing.ImageIcon(getClass().getResource("/images/" + numero + TipoCarta.Suit.SPADES.toString() + ".png"))*/));
			cartas.add(new Carta(numero, TipoCarta.Naipe.PAUS, TipoCarta.Cor.PRETO/*,
					new javax.swing.ImageIcon(getClass().getResource("/images/" + numero + TipoCarta.Color.BLACK.toString() + ".png"))*/));
			cartas.add(new Carta(numero, TipoCarta.Naipe.OURO, TipoCarta.Cor.VERMELHO/*,
					new javax.swing.ImageIcon(getClass().getResource("/images/" + numero + TipoCarta.Color.RED.toString() + ".png"))*/));
			cartas.add(new Carta(numero, TipoCarta.Naipe.CORAÇÃO, TipoCarta.Cor.VERMELHO/*,
					new javax.swing.ImageIcon(getClass().getResource("/images/" + numero + TipoCarta.Color.RED.toString() + ".png"))*/));
		}
		
		for (int j = 0; j < 2; j++) {
			cartas.add(new Carta(j, TipoCarta.Naipe.JOKER, TipoCarta.Cor.VERMELHO/*,
					new javax.swing.ImageIcon(getClass().getResource("/images/" + TipoCarta.Color.RED.toString() + ".png"))*/));
			cartas.add(new Carta(j, TipoCarta.Naipe.JOKER, TipoCarta.Cor.PRETO/*,
					new javax.swing.ImageIcon(getClass().getResource("/images/" + TipoCarta.Color.BLACK.toString() + ".png"))*/));
		}

		return cartas;
	}
}
