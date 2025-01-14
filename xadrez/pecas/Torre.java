package xadrez.pecas;

import xadrez.jogo.Cor;
import xadrez.tabuleiro.Posicao;
import xadrez.tabuleiro.Tabuleiro;

public class Torre extends PecaXadrez {
    
    public Torre(Posicao posicao, Cor cor, Tabuleiro tabuleiro) {
        super(posicao, cor, tabuleiro);
    }
    
    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[8][8];
        
        // Movimento para cima
        for (int i = posicao.getLinha() - 1; i >= 0; i--) {
            Posicao novaPosicao = new Posicao(i, posicao.getColuna());
            if (!podeMover(novaPosicao)) {
                break;
            }
            movimentos[i][posicao.getColuna()] = true;
            if (existePecaInimiga(novaPosicao)) {
                break;
            }
        }
        
        // Movimento para baixo
        for (int i = posicao.getLinha() + 1; i < 8; i++) {
            Posicao novaPosicao = new Posicao(i, posicao.getColuna());
            if (!podeMover(novaPosicao)) {
                break;
            }
            movimentos[i][posicao.getColuna()] = true;
            if (existePecaInimiga(novaPosicao)) {
                break;
            }
        }
        
        // Movimento para esquerda
        for (int j = posicao.getColuna() - 1; j >= 0; j--) {
            Posicao novaPosicao = new Posicao(posicao.getLinha(), j);
            if (!podeMover(novaPosicao)) {
                break;
            }
            movimentos[posicao.getLinha()][j] = true;
            if (existePecaInimiga(novaPosicao)) {
                break;
            }
        }
        
        // Movimento para direita
        for (int j = posicao.getColuna() + 1; j < 8; j++) {
            Posicao novaPosicao = new Posicao(posicao.getLinha(), j);
            if (!podeMover(novaPosicao)) {
                break;
            }
            movimentos[posicao.getLinha()][j] = true;
            if (existePecaInimiga(novaPosicao)) {
                break;
            }
        }
        
        return movimentos;
    }
    
    @Override
    public String toString() {
        return "T";
    }
}
