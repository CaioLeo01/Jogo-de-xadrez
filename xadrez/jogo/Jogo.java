package xadrez.jogo;

import xadrez.pecas.PecaXadrez;
import xadrez.pecas.Rei;
import xadrez.tabuleiro.Posicao;
import xadrez.tabuleiro.Tabuleiro;

public class Jogo {
    private Tabuleiro tabuleiro;
    private Cor jogadorAtual;
    private boolean jogoTerminado;
    private Posicao posicaoSelecionada;
    private boolean[][] movimentosPossiveis;
    
    public Jogo() {
        tabuleiro = new Tabuleiro();
        jogadorAtual = Cor.BRANCO;
        jogoTerminado = false;
        posicaoSelecionada = null;
        movimentosPossiveis = null;
    }
    
    public boolean isJogoTerminado() {
        return jogoTerminado;
    }
    
    public Cor getJogadorAtual() {
        return jogadorAtual;
    }
    
    public PecaXadrez getPeca(Posicao posicao) {
        if (posicao == null || !tabuleiro.posicaoValida(posicao)) {
            return null;
        }
        return tabuleiro.getPeca(posicao);
    }
    
    public boolean[][] getMovimentosPossiveis() {
        return movimentosPossiveis;
    }
    
    public Posicao getPosicaoSelecionada() {
        return posicaoSelecionada;
    }
    
    public void selecionarPosicao(Posicao posicao) {
        if (jogoTerminado) {
            throw new IllegalStateException("O jogo já terminou!");
        }
        
        if (posicao == null || !tabuleiro.posicaoValida(posicao)) {
            throw new IllegalArgumentException("Posição inválida!");
        }
        
        PecaXadrez peca = tabuleiro.getPeca(posicao);
        
        if (posicaoSelecionada == null) {
            if (peca == null) {
                throw new IllegalStateException("Nao ha peça na posicao selecionada!");
            }
            if (peca.getCor() != jogadorAtual) {
                throw new IllegalStateException("Esta peça pertence ao outro jogador!");
            }
            
            posicaoSelecionada = posicao;
            movimentosPossiveis = tabuleiro.getMovimentosPossiveis(posicao);
        } else {
            if (posicao.equals(posicaoSelecionada)) {
                posicaoSelecionada = null;
                movimentosPossiveis = null;
            }
            else if (peca != null && peca.getCor() == jogadorAtual) {
                posicaoSelecionada = posicao;
                movimentosPossiveis = tabuleiro.getMovimentosPossiveis(posicao);
            }
            else if (movimentosPossiveis != null && movimentosPossiveis[posicao.getLinha()][posicao.getColuna()]) {
                realizarMovimento(posicaoSelecionada, posicao);
                posicaoSelecionada = null;
                movimentosPossiveis = null;
                
                if (testeXequeMate(jogadorAtual)) {
                    jogoTerminado = true;
                } else {
                    trocarJogador();
                }
            }
            else {
                throw new IllegalStateException("Movimento invalido para a peça selecionada!");
            }
        }
    }
    
    private void realizarMovimento(Posicao origem, Posicao destino) {
        try {
            tabuleiro.moverPeca(origem, destino);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao mover peça: " + e.getMessage());
        }
    }
    
    private void trocarJogador() {
        jogadorAtual = jogadorAtual.oponente();
    }
    
    private boolean testeXequeMate(Cor cor) {
        if (!testeXeque(cor)) {
            return false;
        }
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Posicao posicao = new Posicao(i, j);
                PecaXadrez peca = tabuleiro.getPeca(posicao);
                
                if (peca != null && peca.getCor() == cor) {
                    boolean[][] movimentos = tabuleiro.getMovimentosPossiveis(posicao);
                    
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (movimentos[k][l]) {
                                Posicao destino = new Posicao(k, l);
                                PecaXadrez pecaMovida = tabuleiro.removerPeca(posicao);
                                PecaXadrez pecaCapturada = tabuleiro.removerPeca(destino);
                                
                                pecaMovida.setPosicao(destino);
                                tabuleiro.colocarPeca(pecaMovida);
                                
                                boolean testeXeque = testeXeque(cor);
                                
                                pecaMovida.setPosicao(posicao);
                                tabuleiro.colocarPeca(pecaMovida);
                                if (pecaCapturada != null) {
                                    pecaCapturada.setPosicao(destino);
                                    tabuleiro.colocarPeca(pecaCapturada);
                                }
                                
                                if (!testeXeque) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private boolean testeXeque(Cor cor) {
        Posicao posicaoRei = encontrarRei(cor);
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Posicao posicao = new Posicao(i, j);
                PecaXadrez peca = tabuleiro.getPeca(posicao);
                
                if (peca != null && peca.getCor() != cor) {
                    boolean[][] movimentos = tabuleiro.getMovimentosPossiveis(posicao);
                    if (movimentos[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private Posicao encontrarRei(Cor cor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Posicao posicao = new Posicao(i, j);
                PecaXadrez peca = tabuleiro.getPeca(posicao);
                
                if (peca instanceof Rei && peca.getCor() == cor) {
                    return posicao;
                }
            }
        }
        throw new IllegalStateException("Não existe rei da cor " + cor + " no tabuleiro!");
    }
    
    public void reiniciarJogo() {
        tabuleiro = new Tabuleiro();
        jogadorAtual = Cor.BRANCO;
        jogoTerminado = false;
        posicaoSelecionada = null;
        movimentosPossiveis = null;
    }
}
