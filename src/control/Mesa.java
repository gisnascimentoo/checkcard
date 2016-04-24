package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.org.apache.xalan.internal.utils.Objects;

import br.ufsc.inf.leobr.cliente.Jogada;
import model.Baralho;
import model.Carta;
import model.Jogador;
import model.Lance;
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
	
	public Rodada getRodadaAtual() {
		return this.rodadaAtual;
	}
	
	public void setRodadaAtual(Rodada rodadaAtual) {
        	this.rodadaAtual = rodadaAtual;
       }  

	/**END: Getters e setters*/

	public void embaralhaBaralho () {
		Collections.shuffle(this.baralho.getCartas());
	}
	
	public Carta compraCartaBaralho () {
		return this.baralho.getCartaRandom();
	}
	
	public boolean isBaralhoVazio () {
		return this.baralho.getCartas().isEmpty();
	}
	
	public void criaCartaCheck () {
		this.setCartaCheck(this.baralho.getCartaRandom());
	}
	
	public List<Carta> distribuiCartas () {
		List<Carta> cartas = new ArrayList<Carta>();
		
		for (int i = 0; i < 5; i++) {
			cartas.add(this.baralho.getCartaRandom());
		}
		
		return cartas;
	}
	
	public void distribuiCartasParaJogadores () {
		this.jogadorUm.setCartasMao(this.distribuiCartas());
		this.jogadorDois.setCartasMao(this.distribuiCartas());
	}

	public boolean verificarMaoJogadorParaComprar(Jogador jogador) {
		return jogador.getCartasMao().size() < 5;
	}

	public void removeCartaBaralho(Carta carta) {
		this.baralho.getCartas().remove(carta);
	}

	public void adicionaCartaMaoJogador(Lance lance) {
		lance.getJogador().getCartasMao().add(lance.getCarta());
	}

	public void removeCartaMaoJogador(Lance lance) {
		lance.getJogador().getCartasMao().remove(lance.getCarta());
    	}

	public void addLance(Lance lance) {
		this.rodadaAtual.addLance(lance);
	}

	public void iniciarRodada(Jogador jogadorDaVez) {
		Rodada rodada = new Rodada();
		this.setRodadaAtual(rodada);
		this.setJogadorDaVez(jogadorDaVez);
	}

	public boolean acabouPartida() {
		for (Jogador jogador : this.jogadores) {
			return jogador.getPontuacao() >= 20;
		}
		return false;
	}
}
