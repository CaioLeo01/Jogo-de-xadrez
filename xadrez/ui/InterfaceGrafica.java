package xadrez.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import xadrez.jogo.Jogo;
import xadrez.jogo.Cor;
import xadrez.tabuleiro.Posicao;
import xadrez.pecas.PecaXadrez;

public class InterfaceGrafica extends JFrame {
    private static final int TAMANHO_QUADRADO = 80;
    private static final int TAMANHO_BORDA = 25;
    private JPanel tabuleiro;
    private Jogo jogo;
    private JButton[][] casas;
    private String nomeJogador1;
    private String nomeJogador2;
    private boolean modoBot;
    private JLabel labelJogadorAtual;
    
    // Cores do tabuleiro
    private Color corCasaClara = new Color(240, 217, 181);
    private Color corCasaEscura = new Color(181, 136, 99);
    private Color corSelecao = new Color(255, 255, 0, 128); // Amarelo semi-transparente
    private Color corMovimentoPossivel = new Color(0, 255, 0, 128); // Verde semi-transparente
    
    public InterfaceGrafica(String jogador1, String jogador2, boolean modoBot) {
        super("Xadrez - " + jogador1 + " vs " + jogador2);
        this.nomeJogador1 = jogador1;
        this.nomeJogador2 = jogador2;
        this.modoBot = modoBot;
        
        // Carrega as imagens das peças
        RecursosImagem.carregarImagens();
        
        // Configura a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Inicializa o jogo e a interface
        jogo = new Jogo();
        inicializarTabuleiro();
        configurarJanela();
        
        // Centraliza a janela
        pack();
        setLocationRelativeTo(null);
    }
    
    private void inicializarTabuleiro() {
        tabuleiro = new JPanel(new GridLayout(8, 8));
        casas = new JButton[8][8];
        boolean corClara = true;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton casa = new JButton();
                casa.setPreferredSize(new Dimension(TAMANHO_QUADRADO, TAMANHO_QUADRADO));
                
                Color corCasa = corClara ? corCasaClara : corCasaEscura;
                casa.setBackground(corCasa);
                casa.setBorderPainted(false);
                casa.setFocusPainted(false);
                casa.setContentAreaFilled(true);
                casa.setOpaque(true);
                
                final int linha = i;
                final int coluna = j;
                casa.addActionListener(e -> cliqueNaCasa(linha, coluna));
                
                casas[i][j] = casa;
                tabuleiro.add(casa);
                corClara = !corClara;
            }
            corClara = !corClara;
        }
        
        // Adiciona borda ao tabuleiro
        tabuleiro.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        
        // Atualiza o tabuleiro com as peças iniciais
        atualizarTabuleiro();
    }
    
    private void configurarJanela() {
        // Painel principal com padding
        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(240, 240, 240));
        
        // Painel do tabuleiro com borda elevada
        JPanel painelTabuleiro = new JPanel(new BorderLayout(10, 10));
        painelTabuleiro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createLineBorder(new Color(50, 50, 50), 2)
        ));
        painelTabuleiro.setBackground(new Color(230, 230, 230));
        
        // Números na vertical (1-8)
        JPanel numerosVertical = new JPanel(new GridLayout(8, 1));
        numerosVertical.setBackground(new Color(230, 230, 230));
        for (int i = 8; i >= 1; i--) {
            JLabel numero = new JLabel(" " + i + " ", SwingConstants.CENTER);
            numero.setFont(new Font("Arial", Font.BOLD, 14));
            numerosVertical.add(numero);
        }
        
        // Letras na horizontal (a-h)
        JPanel letrasHorizontal = new JPanel(new GridLayout(1, 8));
        letrasHorizontal.setBackground(new Color(230, 230, 230));
        for (char c = 'a'; c <= 'h'; c++) {
            JLabel letra = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            letra.setFont(new Font("Arial", Font.BOLD, 14));
            letrasHorizontal.add(letra);
        }
        
        // Adiciona os componentes ao painel do tabuleiro
        painelTabuleiro.add(numerosVertical, BorderLayout.WEST);
        painelTabuleiro.add(letrasHorizontal, BorderLayout.SOUTH);
        painelTabuleiro.add(tabuleiro, BorderLayout.CENTER);
        
        // Painel de informações do jogo
        JPanel painelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelInfo.setBackground(new Color(230, 230, 230));
        painelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Label do jogador atual com estilo
        labelJogadorAtual = new JLabel("Vez de " + nomeJogador1, SwingConstants.CENTER);
        labelJogadorAtual.setFont(new Font("Arial", Font.BOLD, 16));
        labelJogadorAtual.setForeground(new Color(50, 50, 50));
        painelInfo.add(labelJogadorAtual);
        
        // Botão para reiniciar o jogo
        JButton btnReiniciar = new JButton("Reiniciar Jogo");
        btnReiniciar.addActionListener(e -> reiniciarJogo());
        estilizarBotao(btnReiniciar);
        painelInfo.add(btnReiniciar);
        
        // Adiciona os painéis principais
        painelPrincipal.add(painelTabuleiro, BorderLayout.CENTER);
        painelPrincipal.add(painelInfo, BorderLayout.SOUTH);
        
        // Adiciona o painel principal à janela
        setContentPane(painelPrincipal);
    }
    
    private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setForeground(new Color(50, 50, 50));
        botao.setBackground(new Color(230, 230, 230));
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
    
    private void cliqueNaCasa(int linha, int coluna) {
        try {
            Posicao posicao = new Posicao(linha, coluna);
            
            // Verifica se é um movimento válido
            if (!jogo.isPosicaoValida(posicao)) {
                JOptionPane.showMessageDialog(this,
                    "Posição inválida. Selecione uma casa válida.",
                    "Erro",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            jogo.selecionarPosicao(posicao);
            
            // Atualiza a interface após o movimento
            atualizarTabuleiro();
            
            // Se o jogo terminou, mostra mensagem
            if (jogo.isJogoTerminado()) {
                Cor vencedor = jogo.getJogadorAtual().oponente();
                String nomeVencedor = vencedor == Cor.BRANCO ? nomeJogador1 : nomeJogador2;
                
                int resposta = JOptionPane.showConfirmDialog(this,
                    "Xeque-mate! " + nomeVencedor + " venceu!\nDeseja jogar novamente?",
                    "Fim de Jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
                
                if (resposta == JOptionPane.YES_OPTION) {
                    reiniciarJogo();
                } else {
                    dispose(); // Fecha a janela
                }
            } else {
                // Atualiza o label do jogador atual
                labelJogadorAtual.setText("Vez de " + (jogo.getJogadorAtual() == Cor.BRANCO ? nomeJogador1 : nomeJogador2));
            }
            
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Movimento Inválido",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro inesperado: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarTabuleiro() {
        boolean[][] movimentosPossiveis = jogo.getMovimentosPossiveis();
        Posicao posicaoSelecionada = jogo.getPosicaoSelecionada();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton casa = casas[i][j];
                PecaXadrez peca = jogo.getPeca(new Posicao(i, j));
                boolean casaClara = (i + j) % 2 == 0;
                
                // Define o ícone da peça
                if (peca != null) {
                    ImageIcon icone = RecursosImagem.getIconePeca(peca);
                    if (icone != null) {
                        casa.setIcon(icone);
                    } else {
                        casa.setIcon(null);
                        System.err.println("Ícone não encontrado para a peça: " + peca.getClass().getSimpleName());
                    }
                } else {
                    casa.setIcon(null);
                }
                
                // Cria um painel personalizado para o fundo da casa
                JPanel fundoCasa = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g;
                        
                        // Define a cor base da casa
                        g2d.setColor(casaClara ? corCasaClara : corCasaEscura);
                        g2d.fillRect(0, 0, getWidth(), getHeight());
                        
                        // Adiciona a cor de seleção se necessário
                        if (posicaoSelecionada != null && 
                            i == posicaoSelecionada.getLinha() && 
                            j == posicaoSelecionada.getColuna()) {
                            g2d.setColor(corSelecao);
                            g2d.fillRect(0, 0, getWidth(), getHeight());
                        }
                        
                        // Adiciona a cor de movimento possível se necessário
                        if (movimentosPossiveis != null && movimentosPossiveis[i][j]) {
                            g2d.setColor(corMovimentoPossivel);
                            g2d.fillRect(0, 0, getWidth(), getHeight());
                        }
                    }
                };
                
                fundoCasa.setOpaque(true);
                casa.setContentAreaFilled(false);
                casa.add(fundoCasa);
            }
        }
        
        // Força a atualização visual
        tabuleiro.revalidate();
        tabuleiro.repaint();
    }
    
    private void reiniciarJogo() {
        jogo = new Jogo();
        labelJogadorAtual.setText("Vez de " + nomeJogador1);
        atualizarTabuleiro();
    }
}
