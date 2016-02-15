package control;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.inf.leobr.cliente.Jogada;
import model.Carta;
import model.Jogador;
import rede.AtorNetGames;
import view.JMesa;

public class ControladorMesa {
	
	protected Jogador jogadorAtual;
	protected Mesa mesa;
	protected AtorNetGames rede;
	protected JMesa interfaceMesa;
	protected boolean conectado;
	
	public ControladorMesa (JMesa jMesa) {
		this.rede = new AtorNetGames(this);
		this.mesa = new Mesa();
		this.interfaceMesa = jMesa;
	}

	public Jogador getJogadorAtual() {
		return this.jogadorAtual;
	}

	public void setJogadorAtual(Jogador jogadorAtual) {
		this.jogadorAtual = jogadorAtual;
	}

	public Mesa getMesa() {
		return this.mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public AtorNetGames getRede() {
		return this.rede;
	}

	public void setRede(AtorNetGames rede) {
		this.rede = rede;
	}

	public JMesa getInterfaceMesa() {
		return this.interfaceMesa;
	}

	public void setInterfaceMesa(JMesa interfaceMesa) {
		this.interfaceMesa = interfaceMesa;
	}

	public boolean isConectado() {
		return this.conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}
	
	public void criarJogadorAtual(String nome) {
		this.jogadorAtual = new Jogador(nome);
	}
	
	public boolean conectarRede(String nome, String servidor) {
		boolean retorno = this.rede.conectar(nome, servidor);
		this.conectado = retorno;
		if (retorno) {
			this.criarJogadorAtual(nome);
		}
		return retorno;
	}
	
	public void desconectarRede() {
		if (this.conectado) {
			this.rede.desconectar();
			this.atualizarVisibilidadeTela(1);
			this.conectado = false;
		}
	}

	public void atualizarVisibilidadeTela(int mode) {
		
	}

	public void iniciarPartida() {
		this.rede.iniciarPartida();
		
		List<Jogador> jogadores = this.rede.getJogadores();
		
		if (jogadores.size() == 2) {
			this.mesa.setJogadores(jogadores);
			this.criarJogadores(jogadores);
		}
	}
	
	public void criarJogadores(List<Jogador> jogadores) {
		this.mesa.setJogadorUm(jogadores.get(0));
		this.mesa.getJogadorUm().setId(1);
		
		this.mesa.setJogadorDois(jogadores.get(1));
		this.mesa.getJogadorDois().setId(2);
	}

	public void receberJogada(Jogada jogada) {
		// TODO Auto-generated method stub
	}

	public void limpaTodosCampos() {
		// TODO Auto-generated method stub
	}

	public void exibeMensagem(String message) {
		interfaceMesa.exibeMensagem(message);
	}

	public boolean comprarCarta(Jogador jogador) {
		boolean retorno = false;
		
//		if (algo) {
			Carta carta = this.mesa.compraCarta();
			retorno = true;
			this.enviarJogada(carta);
			
			this.receberJogada(carta);
//		}
		
		return retorno;
	}

	public void enviarJogada(Jogada jogada) {
		this.rede.enviarJogada(jogada);
	}

	public boolean jogarCarta(Carta carta) {
		// TODO Auto-generated method stub
		return false;
	}

}
