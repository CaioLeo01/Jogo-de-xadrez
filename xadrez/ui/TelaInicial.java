package xadrez.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaInicial extends JFrame {
    private String jogador1;
    private String jogador2;
    private boolean modoBot;
    
    public TelaInicial() {
        configurarJanela();
    }
    
    private void configurarJanela() {
        setTitle("Xadrez - Menu Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        // Define o fundo e o layout
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        painelPrincipal.setBackground(new Color(240, 240, 240));
        
        // Título
        JLabel titulo = new JLabel("Jogo de Xadrez");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(new Color(50, 50, 50));
        
        // Subtítulo
        JLabel subtitulo = new JLabel("Escolha o modo de jogo");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setForeground(new Color(100, 100, 100));
        
        // Botão Player x Player
        JButton btnPlayerVsPlayer = criarBotao("Player vs Player", false);
        
        // Botão Player x Bot
        JButton btnPlayerVsBot = criarBotao("Player vs Bot", true);
        
        // Adiciona componentes ao painel
        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(subtitulo);
        painelPrincipal.add(Box.createVerticalStrut(40));
        painelPrincipal.add(btnPlayerVsPlayer);
        painelPrincipal.add(Box.createVerticalStrut(20));
        painelPrincipal.add(btnPlayerVsBot);
        
        // Define o fundo da janela
        getContentPane().setBackground(new Color(240, 240, 240));
        add(painelPrincipal);
    }
    
    private JButton criarBotao(String texto, boolean bot) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(200, 50));
        botao.setPreferredSize(new Dimension(200, 50));
        
        // Estilo do botão
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setForeground(new Color(50, 50, 50));
        botao.setBackground(new Color(230, 230, 230));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Efeito hover
        botao.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(new Color(220, 220, 220));
                botao.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
            
            public void mouseExited(MouseEvent e) {
                botao.setBackground(new Color(230, 230, 230));
                botao.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });
        
        botao.addActionListener(e -> abrirTelaJogadores(bot));
        return botao;
    }
    
    private void abrirTelaJogadores(boolean bot) {
        modoBot = bot;
        JDialog dialog = new JDialog(this, "Informações dos Jogadores", true);
        dialog.setSize(350, bot ? 200 : 250);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));
        
        // Instrução inicial
        JLabel instrucao = new JLabel(bot ? "Configure o jogo contra o computador:" : "Configure o jogo entre dois jogadores:");
        instrucao.setFont(new Font("Arial", Font.BOLD, 14));
        instrucao.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instrucao);
        panel.add(Box.createVerticalStrut(15));
        
        // Campo para o Jogador 1
        JTextField campoJogador1 = criarCampoTexto("Digite o nome do Jogador 1 (Peças Brancas):", "Jogador 1");
        panel.add(campoJogador1);
        panel.add(Box.createVerticalStrut(15));
        
        // Campo para o Jogador 2 (apenas se não for modo bot)
        JTextField campoJogador2 = null;
        if (!bot) {
            campoJogador2 = criarCampoTexto("Digite o nome do Jogador 2 (Peças Pretas):", "Jogador 2");
            panel.add(campoJogador2);
            panel.add(Box.createVerticalStrut(15));
        }
        
        // Botão Confirmar
        JButton btnConfirmar = new JButton("Iniciar Jogo");
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        estilizarBotao(btnConfirmar);
        
        final JTextField finalCampoJogador2 = campoJogador2;
        btnConfirmar.addActionListener(e -> {
            jogador1 = campoJogador1.getText().trim();
            if (jogador1.isEmpty() || jogador1.equals("Jogador 1")) {
                mostrarErro(dialog, "Por favor, digite o nome do Jogador 1");
                return;
            }
            
            if (!bot) {
                jogador2 = finalCampoJogador2.getText().trim();
                if (jogador2.isEmpty() || jogador2.equals("Jogador 2")) {
                    mostrarErro(dialog, "Por favor, digite o nome do Jogador 2");
                    return;
                }
            } else {
                jogador2 = "Computador";
            }
            
            dialog.dispose();
            iniciarJogo();
        });
        
        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        estilizarBotao(btnVoltar);
        btnVoltar.addActionListener(e -> dialog.dispose());
        
        // Painel para os botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
        painelBotoes.setBackground(new Color(240, 240, 240));
        painelBotoes.add(btnVoltar);
        painelBotoes.add(Box.createHorizontalStrut(20));
        painelBotoes.add(btnConfirmar);
        
        panel.add(painelBotoes);
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private JTextField criarCampoTexto(String label, String placeholder) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.setBackground(new Color(240, 240, 240));
        
        JLabel lblNome = new JLabel(label);
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNome.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JTextField campo = new JTextField(placeholder, 20);
        campo.setMaximumSize(new Dimension(300, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setForeground(new Color(150, 150, 150));
        
        // Adiciona os listeners para o placeholder
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(new Color(150, 150, 150));
                }
            }
        });
        
        container.add(lblNome);
        container.add(Box.createVerticalStrut(5));
        container.add(campo);
        
        return campo;
    }
    
    private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setForeground(new Color(50, 50, 50));
        botao.setBackground(new Color(230, 230, 230));
        botao.setMaximumSize(new Dimension(150, 40));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        botao.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(new Color(220, 220, 220));
            }
            
            public void mouseExited(MouseEvent e) {
                botao.setBackground(new Color(230, 230, 230));
            }
        });
    }
    
    private void mostrarErro(JDialog parent, String mensagem) {
        JOptionPane.showMessageDialog(parent,
            mensagem,
            "Erro",
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void iniciarJogo() {
        try {
            InterfaceGrafica interfaceGrafica = new InterfaceGrafica(jogador1, jogador2, modoBot);
            interfaceGrafica.setVisible(true);
            this.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao iniciar o jogo: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
