package control;

import br.ufsc.inf.leobr.cliente.Jogada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Carta;
import model.Baralho;
import model.Jogador;
import model.Rodada;

public class Mesa implements Jogada {

	protected Baralho baralho;
	protected StatusMesa statusMesa;
	protected List<Jogador> jogadores;
	protected Jogador jogadorUm;
	protected Jogador jogadorDois;
	protected Jogador jogadorVencedor;
	protected Jogador jogadorDaVez;
	protected Carta cartaCheck;
	protected List<Rodada> rodadas;
	protected Rodada rodadaAtual;
	
	public Mesa () {
		this.baralho = new Baralho();
	}

	public StatusMesa getStatusMesa() {
		return statusMesa;
	}

	public void setStatusMesa(StatusMesa statusMesa) {
		this.statusMesa = statusMesa;
	}
	
	public List<Jogador> getJogadores () {
		return this.jogadores;
	}
	
	public void setJogadores (List<Jogador> novosJogadores) {
		this.jogadores = novosJogadores;
	}

	public Jogador getJogadorUm() {
		return jogadorUm;
	}

	public void setJogadorUm(Jogador jogadorUm) {
		this.jogadorUm = jogadorUm;
	}

	public Jogador getJogadorDois() {
		return jogadorDois;
	}

	public void setJogadorDois(Jogador jogadorDois) {
		this.jogadorDois = jogadorDois;
	}
	
	public Jogador getJogadorVencedor() {
		return jogadorVencedor;
	}

	public void setJogadorVencedor(Jogador jogadorVencedor) {
		this.jogadorVencedor = jogadorVencedor;
	}

	public Jogador getJogadorDaVez() {
		return jogadorDaVez;
	}

	public void setJogadorDaVez(Jogador jogadorDaVez) {
		this.jogadorDaVez = jogadorDaVez;
	}

	public Baralho getBaralho() {
		return baralho;
	}

	public void setBaralho(Baralho baralho) {
		this.baralho = baralho;
	}
	
	public Carta getCartaCheck() {
		return cartaCheck;
	}

	public void setCartaCheck(Carta cartaCheck) {
		this.cartaCheck = cartaCheck;
	}

	/**END: Getters e setters*/

	public void embaralhaBaralho () {
		Collections.shuffle(this.getBaralho().getCartas());
	}
	
	public List<Carta> distribuiCartas () {
		List<Carta> cartas = new ArrayList<Carta>();
		
		for (int i = 0; i < 5; i++) {
			cartas.add(this.getBaralho().getCartaRandom());
		}
		
		return cartas;
	}
	
	public void distribuiCartasParaJogadores () {
		this.getJogadorUm().setCartasMao(this.distribuiCartas());
		this.getJogadorDois().setCartasMao(this.distribuiCartas());
	}
	
	public Carta compraCarta () {
		return this.getBaralho().getCartaRandom();
	}
	
	public boolean isBaralhoVazio () {
		return this.getBaralho().getCartas().isEmpty();
	}
	
	public void criaCartaCheck () {
		this.setCartaCheck(this.getBaralho().getCartaRandom());
	}
	
	public boolean isCartaLancadaChecandoComCartaCheck (Carta carta) {
		return carta.getNumero() == cartaCheck.getNumero();
	}

	public boolean verificarMaoJogadorParaComprar(Jogador jogador) {
		return jogador.getCartasMao().size() < 5;
	}
}
