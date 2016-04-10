package model;

import java.util.ArrayList;
import java.util.List;

//import br.ufsc.inf.leobr.cliente.Jogada;

public class FactoryCarta {
	
	public List<Carta> criaCartas() {
		List<Carta> cartas = new ArrayList<Carta>();

		for (int numero = 1; numero <= 13; numero++) {
			System.out.println("/images/" + numero + TipoCarta.Naipe.ESPADAS.toString().trim() + ".png");
//			cartas.add(new Carta(numero, TipoCarta.Naipe.ESPADAS, TipoCarta.Cor.PRETO,
//					new javax.swing.ImageIcon(getClass().getResource("/images/" + numero + TipoCarta.Naipe.ESPADAS.toString().trim() + ".png"))));
//			cartas.add(new Carta(numero, TipoCarta.Naipe.PAUS, TipoCarta.Cor.PRETO,
//					new javax.swing.ImageIcon(getClass().getResource("/images/" + numero + TipoCarta.Naipe.PAUS.toString().trim() + ".png"))));
//			cartas.add(new Carta(numero, TipoCarta.Naipe.OURO, TipoCarta.Cor.VERMELHO,
//					new javax.swing.ImageIcon(getClass().getResource("/images/" + numero + TipoCarta.Naipe.OURO.toString().trim() + ".png"))));
//			cartas.add(new Carta(numero, TipoCarta.Naipe.CORAÇÃO, TipoCarta.Cor.VERMELHO,
//					new javax.swing.ImageIcon(getClass().getResource("/images/" + numero + TipoCarta.Naipe.CORAÇÃO.toString().trim() + ".png"))));
		}
		
		for (int j = 0; j < 2; j++) {
//			cartas.add(new Carta(j, TipoCarta.Naipe.JOKER, TipoCarta.Cor.VERMELHO,
//					new javax.swing.ImageIcon(getClass().getResource("/images/" + TipoCarta.Naipe.JOKER.toString().trim() + TipoCarta.Cor.VERMELHO.toString().trim() + ".png"))));
//			cartas.add(new Carta(j, TipoCarta.Naipe.JOKER, TipoCarta.Cor.PRETO,
//					new javax.swing.ImageIcon(getClass().getResource("/images/" + TipoCarta.Naipe.JOKER.toString().trim() + TipoCarta.Cor.VERMELHO.toString().trim() + ".png"))));
		}

		return cartas;
	}
}
