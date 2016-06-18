/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.StatusMesa;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Carta;
import model.Jogador;
import model.Lance;
import view.AtorJogador;

/**
 *
 * @author rodolfolottin
 */
public class JMesa extends javax.swing.JFrame {
    
    protected AtorJogador atorJogador;
    protected static final int START = 1;
    protected static final int CONECTADO = 2;
    
    public JMesa() {
        initComponents();
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
		String  servidor = this.getNomeServidor();
		boolean conectou = atorJogador.conectar(nomeAtual, servidor);
		atorJogador.setJogadorAtual(new Jogador(nomeAtual));
		if (conectou) {
			this.adicionarTitulo(nomeAtual);
			this.atualizarVisibilidadeTela(CONECTADO);
			this.exibeMensagem("Conectado com sucesso!");
		} else {
			this.exibeMensagem("Não foi possível se conectar!");
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
            this.jLabelCartaJogador.setIcon(lance.getCarta().getImage());
        } else {
            this.jLabelCartaAdversario.setIcon(lance.getCarta().getImage());
            this.removeLabel(jPanelAdversario);
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

	public void atualizaJogadorDaVez(control.Mesa mesa) {
		jLabelTextoJogadorDaVez.setText(mesa.getJogadorDaVez().getNome());
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
		jPanelJogador.remove(comp);
        jPanelJogador.validate();
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
        jPanelJogador.removeAll();
        jPanelAdversario.removeAll();
	}

	private void limparPlacarInterface() {
		jLabelValorJogadorUm.setText("0");
		jLabelValorJogadorDois.setText("0");
	}

	private void atualizaCheckCard(control.Mesa mesa) {
        mesa.criaCartaCheck();
        Carta cartaCheck = mesa.getCartaCheck(); 
		jLabelCheckCard.setIcon(cartaCheck.getImage());
    }
	
	private void adicionarTitulo(String nome) {
        this.setTitle(nome);
    }
	
	public void recebeMesa(control.Mesa mesa) {
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
	
	private void setNomeJogadoresLabel(control.Mesa mesa) {
		this.jLabelTextoJogadorUm.setText(mesa.getJogadorUm().getNome());
        this.jLabelTextoJogadorDois.setText(mesa.getJogadorDois().getNome());
	}
	
	private void iniciarPartida(control.Mesa mesa) {
        this.atualizaCamposInicioPartida(mesa);
    }
    
    private void atualizaCamposInicioPartida(control.Mesa mesa) {
    	this.limparPanelsCartas();
        
        Jogador jogadorAtual = this.getJogadorAtualNaMesa(mesa);
        
        this.atualizaCartasJogadorAtual(jogadorAtual);
        this.atualizaCartasAdversarios(jogadorAtual);
        this.atualizaBaralho(mesa);
        this.iniciarRodada(mesa);
    }

	private void atualizaBaralho(control.Mesa mesa) {
		ImageIcon image = new javax.swing.ImageIcon(getClass().getResource("/images/Atras.png"));
        image.setImage(image.getImage().getScaledInstance(110, 160, 150));
        jLabelBaralho.setIcon(image);
	}

	private void atualizaCartasAdversarios(Jogador jogadorAtual) {
		this.adicionaCartas(jPanelAdversario, true);
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
		jPanelJogador.removeAll();
        for (int i = 0; i < jogadorAtual.getCartasMao().size(); i++) {
            JLabel jlabel = new JLabel(jogadorAtual.getCartasMao().get(i).getImage());
            jlabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            jlabel.addMouseListener(new ClickCarta(jogadorAtual.getCartasMao().get(i)));
            jPanelJogador.add(jlabel);
            jPanelJogador.validate();
        }
	}

	private void limparPanelsCartas() {
		jPanelJogador.removeAll();
		jPanelAdversario.removeAll();
		jLabelBaralho.setIcon(null);
        jLabelCheckCard.setIcon(null);
        jLabelCartaJogador.setIcon(null);
        jLabelCartaAdversario.setIcon(null);
        jPanelAdversario.validate();
        jPanelJogador.validate();
        this.validate();
	}

	private Jogador getJogadorAtualNaMesa(control.Mesa mesa) {
		Jogador jogador1 = mesa.getJogadorUm();
        Jogador jogador2 = mesa.getJogadorDois();
        
        if (this.atorJogador.getJogadorAtual().getNome().equals(jogador1.getNome())){
            return jogador1;
        } else {
            return jogador2;
        }
	}

	private void iniciarRodada(control.Mesa mesa) {
        mesa.iniciarRodada(mesa.getJogadorDaVez());
    }

	public void atualizarPontosJogadores(control.Mesa mesa) {
		jLabelValorJogadorUm.setText(mesa.getJogadorUm().getPontuacao() + "");
		jLabelValorJogadorDois.setText(mesa.getJogadorDois().getPontuacao() + "");
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMesa = new javax.swing.JPanel();
        jPanelAdversario = new javax.swing.JPanel();
        jPanelJogador = new javax.swing.JPanel();
        jLabelBaralho = new javax.swing.JLabel();
        jLabelCheckCard = new javax.swing.JLabel();
        jLabelCartaJogador = new javax.swing.JLabel();
        jLabelCartaAdversario = new javax.swing.JLabel();
        jPanelInfos = new javax.swing.JPanel();
        jLabelTextoPlacar = new javax.swing.JLabel();
        jLabelTextoJogadorUm = new javax.swing.JLabel();
        jLabelTextoJogadorDois = new javax.swing.JLabel();
        jLabelValorJogadorUm = new javax.swing.JLabel();
        jLabelValorJogadorDois = new javax.swing.JLabel();
        jLabelTextoCheckCard = new javax.swing.JLabel();
        jLabelValorCheckCard = new javax.swing.JLabel();
        jLabelTextoJogadorDaVez = new javax.swing.JLabel();
        jLabelValorJogadorDaVez = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenu = new javax.swing.JMenu();
        jMenuItemConectar = new javax.swing.JMenuItem();
        jMenuItemIniciarPartida = new javax.swing.JMenuItem();
        jMenuItemDesconectar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelMesa.setBackground(new java.awt.Color(68, 146, 188));

        jPanelAdversario.setBackground(new java.awt.Color(68, 146, 188));
        jPanelAdversario.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanelAdversario.setLayout(new java.awt.GridLayout());

        jPanelJogador.setBackground(new java.awt.Color(68, 146, 188));
        jPanelJogador.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanelJogador.setLayout(new java.awt.GridLayout());

        jLabelTextoPlacar.setText("Placar");

        jLabelTextoJogadorUm.setText("Jogador");

        jLabelTextoJogadorDois.setText("Jogador");

        jLabelValorJogadorUm.setText("0");

        jLabelValorJogadorDois.setText("0");

        jLabelTextoCheckCard.setText("CheckCard");

        jLabelTextoJogadorDaVez.setText("Jogador da vez");
        
        jLabelBaralho.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                clicouBaralho(atorJogador.getControladorMesa().getJogadorAtual());
            }
        });

        javax.swing.GroupLayout jPanelInfosLayout = new javax.swing.GroupLayout(jPanelInfos);
        jPanelInfos.setLayout(jPanelInfosLayout);
        jPanelInfosLayout.setHorizontalGroup(
            jPanelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfosLayout.createSequentialGroup()
                .addGroup(jPanelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInfosLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabelTextoJogadorUm)
                        .addGroup(jPanelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelInfosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelTextoPlacar)
                                .addGap(279, 279, 279)
                                .addComponent(jLabelTextoCheckCard)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelTextoJogadorDaVez))
                            .addGroup(jPanelInfosLayout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabelTextoJogadorDois)
                                .addGap(151, 151, 151)
                                .addComponent(jLabelValorCheckCard, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                                .addComponent(jLabelValorJogadorDaVez, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelInfosLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabelValorJogadorUm)
                        .addGap(119, 119, 119)
                        .addComponent(jLabelValorJogadorDois)))
                .addGap(140, 140, 140))
        );
        jPanelInfosLayout.setVerticalGroup(
            jPanelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfosLayout.createSequentialGroup()
                .addGroup(jPanelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInfosLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTextoJogadorUm, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTextoJogadorDois))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelValorJogadorUm)
                            .addComponent(jLabelValorJogadorDois)))
                    .addGroup(jPanelInfosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTextoPlacar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTextoCheckCard)
                            .addComponent(jLabelTextoJogadorDaVez))
                        .addGroup(jPanelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelInfosLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabelValorCheckCard, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelInfosLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabelValorJogadorDaVez, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelMesaLayout = new javax.swing.GroupLayout(jPanelMesa);
        jPanelMesa.setLayout(jPanelMesaLayout);
        jPanelMesaLayout.setHorizontalGroup(
            jPanelMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMesaLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabelCheckCard, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelBaralho, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMesaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelCartaAdversario, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(365, 365, 365))
            .addGroup(jPanelMesaLayout.createSequentialGroup()
                .addGroup(jPanelMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMesaLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanelMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelAdversario, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
                            .addComponent(jPanelJogador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelMesaLayout.createSequentialGroup()
                        .addGap(365, 365, 365)
                        .addComponent(jLabelCartaJogador, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanelInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelMesaLayout.setVerticalGroup(
            jPanelMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMesaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanelAdversario, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelCartaAdversario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanelMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelBaralho, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(jLabelCheckCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(jLabelCartaJogador, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jPanelJogador, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanelInfos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu.setText("Menu");

        jMenuItemConectar.setText("Conectar");
        jMenuItemConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConectarActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItemConectar);

        jMenuItemIniciarPartida.setText("Iniciar Partida");
        jMenuItemIniciarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jMenuItemIniciarPartidaActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItemIniciarPartida);

        jMenuItemDesconectar.setText("Desconectar");
        jMenuItemDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jMenuItemDesconectarActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItemDesconectar);

        jMenuBar.add(jMenu);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMesa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanelMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    protected void jMenuItemDesconectarActionPerformed(ActionEvent evt) {
		atorJogador.desconectar();
	}

	protected void jMenuItemIniciarPartidaActionPerformed(ActionEvent evt) {
		atorJogador.iniciarPartida();
	}

	protected void jMenuItemConectarActionPerformed(ActionEvent evt) {
		this.conectar();
	}

	/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelBaralho;
    private javax.swing.JLabel jLabelCartaAdversario;
    private javax.swing.JLabel jLabelCartaJogador;
    private javax.swing.JLabel jLabelCheckCard;
    private javax.swing.JLabel jLabelTextoCheckCard;
    private javax.swing.JLabel jLabelTextoJogadorDaVez;
    private javax.swing.JLabel jLabelTextoJogadorDois;
    private javax.swing.JLabel jLabelTextoJogadorUm;
    private javax.swing.JLabel jLabelTextoPlacar;
    private javax.swing.JLabel jLabelValorCheckCard;
    private javax.swing.JLabel jLabelValorJogadorDaVez;
    private javax.swing.JLabel jLabelValorJogadorDois;
    private javax.swing.JLabel jLabelValorJogadorUm;
    private javax.swing.JMenu jMenu;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItemConectar;
    private javax.swing.JMenuItem jMenuItemDesconectar;
    private javax.swing.JMenuItem jMenuItemIniciarPartida;
    private javax.swing.JPanel jPanelAdversario;
    private javax.swing.JPanel jPanelInfos;
    private javax.swing.JPanel jPanelJogador;
    private javax.swing.JPanel jPanelMesa;
    // End of variables declaration//GEN-END:variables
}
