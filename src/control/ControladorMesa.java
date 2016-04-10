package control;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.inf.leobr.cliente.Jogada;
import model.Carta;
import model.Jogador;
import model.Lance;
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
		this.interfaceMesa.atualizarVisibilidadeTela(mode);
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
		Jogador jogando = this.getMesa().getJogadorDaVez();
		Carta carta = null;
        Lance lance = null;

        if (jogada instanceof Mesa) {
            this.mesa = (Mesa) jogada;
            this.setJogadorAtualIniciarPartida(mesa);
            this.interfaceMesa.recebeMesa(mesa);
        } else if (jogada instanceof Lance) {
        	lance = (Lance) jogada;
        	if (lance.getTipoLance().equals(Lance.TipoLance.COMPRAR_CARTA)) {
	        	this.mesa.removeCartaBaralho(lance.getCarta());
	        	this.mesa.adicionaCartaMaoJogador(lance);
        	} else if (lance.getTipoLance().equals(Lance.TipoLance.JOGAR_CARTA)) {
        		this.mesa.removeCartaMaoJogador(lance);
        	}
        	this.alterarJogadorDaVezNaMesa(jogando);
            this.interfaceMesa.atualizaJogadorDaVez(mesa);
            this.interfaceMesa.recebeLance(lance);
            this.mesa.addLance(lance);
        	this.verificarFimDaRodada();
        }
 	}

	public void enviarJogada(Jogada jogada) {
		this.rede.enviarJogada(jogada);
	}

	public void limparTodosCampos() {
		this.interfaceMesa.limparTodosCampos();
	}

	public void exibeMensagem(String message) {
		interfaceMesa.exibeMensagem(message);
	}

	public boolean comprarCarta(Jogador jogador) {
		boolean retorno = false;

		if (this.tratarPossibilidadeJogada()) {
			if (tratarPossibilidadeComprarCarta(jogador)) {
				Lance lance = new Lance();
				lance.setJogador(jogador);
				lance.setCarta(this.mesa.compraCartaBaralho());
				lance.setTipoLance(Lance.TipoLance.COMPRAR_CARTA);

				retorno = true;

				this.enviarJogada(lance);
				this.receberJogada(lance);
			} else {
				this.exibeMensagem("Você atingiu o limite cartas.");
			}
		} else {
			this.exibeMensagem("Espere a sua vez.");
		}

		return retorno;
	}

	public boolean jogarCarta(Carta carta) {
		boolean retorno = false;

		if (this.tratarPossibilidadeJogada()) {
			Lance lance = new Lance();
			lance.setJogador(this.mesa.getJogadorDaVez());
			lance.setCarta(carta);
			lance.setTipoLance(Lance.TipoLance.JOGAR_CARTA);

			retorno = true;

			this.enviarJogada(lance);
			this.receberJogada(lance);
		} else {
			this.exibeMensagem("Espere a sua vez.");
		}

		return retorno;
	}

	private void setJogadorAtualIniciarPartida(Mesa mesa) {
		if (this.mesa.getStatusMesa().equals(StatusMesa.INICAR_PARTIDA)) {
            for (Jogador jog : mesa.getJogadores()) {
                if (jog.getNome().equals(jogadorAtual.getNome())) {
                    jogadorAtual = jog;
                }
            }
        }
	}

	private void verificarFimDaRodada() {
		//pontuacoes aqui
	}

	private void alterarJogadorDaVezNaMesa(Jogador jogador) {
		if (this.mesa.getJogadorUm().getNome().equals(jogador.getNome())) {
			this.mesa.setJogadorDaVez(this.mesa.getJogadorDois());
		} else {
			this.mesa.setJogadorDaVez(this.mesa.getJogadorUm());
		}
	}

	private boolean tratarPossibilidadeComprarCarta(Jogador jogador) {
		return mesa.verificarMaoJogadorParaComprar(jogador) && !(mesa.isBaralhoVazio());
	}

	private boolean tratarPossibilidadeJogada() {
		return this.isVezJogador(jogadorAtual) && this.isConectado();
	}

	private boolean isVezJogador(Jogador jogador) {
		return jogador.getNome().equals(this.mesa.getJogadorDaVez().getNome());
	}

	private int compararCartaComCheckCard (Carta carta) {
		if (carta.getNumero() == this.mesa.getCartaCheck().getNumero() || carta.getNaipe() == this.mesa.getCartaCheck().getNaipe()) {
			return 2;
		} else if (carta.getNumero() == this.mesa.getCartaCheck().getNumero() && carta.getNaipe() == this.mesa.getCartaCheck().getNaipe()) {
			return 5;
		}
		return 0;
	}
}
