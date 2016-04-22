package view;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import control.Mesa;
import model.Carta;
import model.Jogador;
import model.Lance;

public class JMesa {

	protected AtorJogador atorJogador;
	
	public JMesa () {
		this.atorJogador = new AtorJogador(this);
	}
	
	public void clicouBaralho (Jogador jogador) {
		if (this.atorJogador.comprarCarta(jogador)) 
			this.atualizarCartasJogadorAtual(jogador);
	}

	public void clicouCarta (Carta carta, Component comp) {
		if (this.atorJogador.jogarCarta(carta)) 
			this.atualizarPanelJogador(comp);
	}
	
	public void recebeLance(Lance lance) {
		// TODO Auto-generated method stub
	}
	
	public void atualizaJogadorDaVez(Mesa mesa) {
		// TODO Auto-generated method stub
	}
	
	public void atualizarVisibilidadeTela(int mode) {
		// TODO Auto-generated method stub
	}
	
	private void atualizarPanelJogador(Component comp) {
		//TODO
	}

	private void atualizarCartasJogadorAtual(Jogador jogador) {
		//TODO
	}

	public void exibeMensagem(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void limparTodosCampos() {
		//TODO
	}
	
	public void recebeMesa(Mesa mesa) {
		// TODO Auto-generated method stub
	}
	
	public void atualizarPontosJogadores() {
		// TODO Auto-generated method stub
		
	}
	
	public class ClickCarta extends MouseAdapter {

        private Carta carta;

        public ClickCarta(Carta carta) {
            this.carta = carta;
        }

        @Override
        public void mouseClicked(MouseEvent me) {

            clicouCarta(carta, me.getComponent());
        }
    }
}
