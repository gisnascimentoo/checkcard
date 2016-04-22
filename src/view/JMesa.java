package view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.Mesa;
import control.StatusMesa;
import model.Carta;
import model.Jogador;
import model.Lance;

public class JMesa extends javax.swing.JFrame {

	protected AtorJogador atorJogador;
	protected static final int START = 1;
    protected static final int CONECTADO = 2;
	
	public JMesa () {
//		initComponents();
        this.setResizable(false);
        this.atualizarVisibilidadeTela(START);
		this.atorJogador = new AtorJogador(this);
	}
	
	public String getNomeJogador() {
		return JOptionPane.showInputDialog(this, "Digite seu nome: ", "jogador");
	}

	public String getNomeServidor() {
		return JOptionPane.showInputDialog(this, "Digite o servidor: ", "localhost");
	}

	public void conectar() {
		String nomeAtual = this.getNomeJogador();
		String servidor = this.getNomeServidor();
		boolean conectou = atorJogador.conectar(servidor, nomeAtual);
		atorJogador.setJogadorAtual(new Jogador(nomeAtual));
		if (conectou) {
			this.adicionarTitulo(nomeAtual);
			this.atualizarVisibilidadeTela(CONECTADO);
			this.exibeMensagem("Conectado com sucesso!");
		} else {
			this.exibeMensagem("Não foi possível se!");
		}
	}
	
	public void clicouBaralho (Jogador jogador) {
		if (this.atorJogador.comprarCarta(jogador)) 
			this.atualizaCartasJogadorAtual(jogador);
	}

	public void clicouCarta (Carta carta, Component comp) {
		if (this.atorJogador.jogarCarta(carta)) 
			this.atualizarPanelJogador(comp);
	}
	
	public void recebeLance(Lance lance) {
		Jogador jogadorAtual = atorJogador.getJogadorAtual();
        if (lance.getJogador().getNome().equals(jogadorAtual.getNome())) {
            this.jLabelJogadorCompartilhado.setIcon(lance.getCarta().getImage());
        } else {
            this.jLabelAdversarioCompartilhado.setIcon(lance.getCarta().getImage());
            this.removeLabel(jPanelJogadorAdversario);
        }
	}
	
	private void removeLabel(JPanel panel) {
		try {
            panel.remove(0);
            panel.validate();
        } catch (Exception e) {
            e.getMessage();
        }
	}

	public void atualizaJogadorDaVez(Mesa mesa) {
		jLabelJogadorVez.setText(mesa.getJogadorDaVez().getNome());
	}
	
	public void atualizarVisibilidadeTela(int mode) {
		if (mode == START) {
            jMenuItemConectar.setEnabled(true);
            jMenuItemDesconectar.setEnabled(false);
            jMenuItemIniciarPartida.setEnabled(false);
        } else if (mode == CONECTADO) {
            jMenuItemDesconectar.setEnabled(true);
            jMenuItemIniciarPartida.setEnabled(true);
            jMenuItemConectar.setEnabled(false);
        }
	}
	
	private void atualizarPanelJogador(Component comp) {
		jPanelJogadorAtual.remove(comp);
        jPanelJogadorAtual.validate();
	}

	public void exibeMensagem(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void limparTodosCampos() {
        this.limparPanelsCartas();
        this.limparPlacarInterface();
        this.limparPanelsCartasJogadores();
        this.limparCheckCard();
	}
	
	private void limparCheckCard() {
        jLabelCheckCard.setIcon(null);
	}

	private void limparPanelsCartasJogadores() {
        jPanelJogadorAtual.removeAll();
        jPanelJogadorAdversario.removeAll();
	}

	private void limparPlacarInterface() {
		jLabelValuePlacarJogadorUm.setText("0");
        jLabelValuePlacarJogadorDois.setText("0");
	}

	private void atualizaCheckCard(Mesa mesa) {
        mesa.criaCartaCheck();
        Carta cartaCheck = mesa.getCartaCheck(); 
		jLabelCheckCard.setIcon(cartaCheck.getImage());
    }
	
	private void adicionarTitulo(String nome) {
        this.setTitle(nome);
    }
	
	public void recebeMesa(Mesa mesa) {
		if (mesa.getStatusMesa().equals(StatusMesa.INICAR_PARTIDA)) {
            this.iniciarPartida(mesa);
            this.setNomeJogadoresLabel(mesa);
            JOptionPane.showMessageDialog(this, "Uma nova partida vai iniciar");
        }  else if (mesa.getStatusMesa().equals(StatusMesa.INICIAR_RODADA)) {
            this.iniciarPartida(mesa);
        } 
        this.atualizaJogadorDaVez(mesa);
        this.validate();
	}
	
	private void setNomeJogadoresLabel(Mesa mesa) {
		this.jLabelNomeJogadorUm.setText(mesa.getJogadorUm().getNome());
        this.jLabelNomeJogadorDois.setText(mesa.getJogadorDois().getNome());
	}
	
	private void iniciarPartida(Mesa mesa) {
        this.atualizaCamposInicioPartida(mesa);
    }
    
    private void atualizaCamposInicioPartida(Mesa mesa) {
    	this.limparPanelsCartas();
        
        Jogador jogadorAtual = this.getJogadorAtualNaMesa(mesa);
        
        this.atualizaCartasJogadorAtual(jogadorAtual);
        this.atualizaCartasAdversarios(jogadorAtual);
        this.atualizaBaralho(mesa);
        this.iniciarRodada(mesa);
    }

	private void atualizaBaralho(Mesa mesa) {
		ImageIcon image = new javax.swing.ImageIcon(getClass().getResource("/images/Atras.png"));
        image.setImage(image.getImage().getScaledInstance(110, 160, 150));
        jLabelBaralhoCompartilhado.setIcon(image);
	}

	private void atualizaCartasAdversarios(Jogador jogadorAtual) {
		this.adicionaCartas(jPanelJogadorAdversario, true);
	}

	private void adicionaCartas(JPanel jPanelJogador, boolean adversario) {
		ImageIcon image = null;

        if (adversario) {
            image = new javax.swing.ImageIcon(getClass().getResource("/images/Atras.png"));
        } else {
            image = new javax.swing.ImageIcon(getClass().getResource("/images/Atras.png"));
        }
        image.setImage(image.getImage().getScaledInstance(100, 150, 150));
        JLabel label = new JLabel(image);
        jPanelJogador.add(label);
        jPanelJogador.validate();
	}

	private void atualizaCartasJogadorAtual(Jogador jogadorAtual) {
		jPanelJogadorAtual.removeAll();
        for (int i = 0; i < jogadorAtual.getCartasMao().size(); i++) {
            JLabel jlabel = new JLabel(jogadorAtual.getCartasMao().get(i).getImage());
            jlabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            jlabel.addMouseListener(new ClickCarta(jogadorAtual.getCartasMao().get(i)));
            jPanelJogadorAtual.add(jlabel);
            jPanelJogadorAtual.validate();
        }
	}

	private void limparPanelsCartas() {
		jPanelJogadorAtual.removeAll();
        jPanelJogadorAdversario.removeAll();
        jLabelBaralhoCompartilhado.setIcon(null);
        jLabelCheckCard.setIcon(null);
        jLabelJogadorCompartilhado.setIcon(null);
        jLabelAdversarioCompartilhado.setIcon(null);
        jPanelJogadorAdversario.validate();
        jPanelJogadorAtual.validate();
        this.validate();
	}

	private Jogador getJogadorAtualNaMesa(Mesa mesa) {
		Jogador jogador1 = mesa.getJogadorUm();
        Jogador jogador2 = mesa.getJogadorDois();
        
        if (this.atorJogador.getJogadorAtual().getNome().equals(jogador1.getNome())){
            return jogador1;
        } else {
            return jogador2;
        }
	}

	private void iniciarRodada(Mesa mesa) {
        mesa.iniciarRodada(mesa.getJogadorDaVez());
    }

	public void atualizarPontosJogadores(Mesa mesa) {
		jLabelValuePlacarJogadorUm.setText(mesa.getJogadorUm().getPontuacao() + "");
        jLabelValuePlacarJogadorDois.setText(mesa.getJogadorDois().getPontuacao() + "");
	}
	
	/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JMesa().setVisible(true);
            }
        });
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
	
	private javax.swing.JLabel jLabelAdversarioCompartilhado;
    private javax.swing.JLabel jLabelJogadorCompartilhado;
	private javax.swing.JLabel jLabelBaralhoCompartilhado;
    private javax.swing.JLabel jLabelCheckCard;
    private javax.swing.JLabel jLabelJogadorVez;
    private javax.swing.JLabel jLabelNomeJogadorUm;
	private javax.swing.JLabel jLabelNomeJogadorDois;
	private javax.swing.JLabel jLabelValuePlacarJogadorUm;
	private javax.swing.JLabel jLabelValuePlacarJogadorDois;
	/*Menu*/
	private javax.swing.JLabel jMenuItemConectar;
	private javax.swing.JLabel jMenuItemDesconectar;
	private javax.swing.JLabel jMenuItemIniciarPartida;
	/*Panels*/
	private JPanel jPanel;
    private JPanel jPanelJogadorAtual;
	private JPanel jPanelJogadorAdversario;
}
