package xadrez.pecas;

import xadrez.jogo.Cor;
import xadrez.tabuleiro.Posicao;
import xadrez.tabuleiro.Tabuleiro;

public class Rainha extends PecaXadrez {
    
    public Rainha(Posicao posicao, Cor cor, Tabuleiro tabuleiro) {
        super(posicao, cor, tabuleiro);
    }
    
    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[8][8];
        
        // Movimentos horizontais e verticais (como a Torre)
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
        
        // Movimentos diagonais (como o Bispo)
        // Diagonal superior esquerda
        for (int i = 1; posicao.getLinha() - i >= 0 && posicao.getColuna() - i >= 0; i++) {
            Posicao novaPosicao = new Posicao(posicao.getLinha() - i, posicao.getColuna() - i);
            if (!podeMover(novaPosicao)) {
                break;
            }
            movimentos[novaPosicao.getLinha()][novaPosicao.getColuna()] = true;
            if (existePecaInimiga(novaPosicao)) {
                break;
            }
        }
        
        // Diagonal superior direita
        for (int i = 1; posicao.getLinha() - i >= 0 && posicao.getColuna() + i < 8; i++) {
            Posicao novaPosicao = new Posicao(posicao.getLinha() - i, posicao.getColuna() + i);
            if (!podeMover(novaPosicao)) {
                break;
            }
            movimentos[novaPosicao.getLinha()][novaPosicao.getColuna()] = true;
            if (existePecaInimiga(novaPosicao)) {
                break;
            }
        }
        
        // Diagonal inferior esquerda
        for (int i = 1; posicao.getLinha() + i < 8 && posicao.getColuna() - i >= 0; i++) {
            Posicao novaPosicao = new Posicao(posicao.getLinha() + i, posicao.getColuna() - i);
            if (!podeMover(novaPosicao)) {
                break;
            }
            movimentos[novaPosicao.getLinha()][novaPosicao.getColuna()] = true;
            if (existePecaInimiga(novaPosicao)) {
                break;
            }
        }
        
        // Diagonal inferior direita
        for (int i = 1; posicao.getLinha() + i < 8 && posicao.getColuna() + i < 8; i++) {
            Posicao novaPosicao = new Posicao(posicao.getLinha() + i, posicao.getColuna() + i);
            if (!podeMover(novaPosicao)) {
                break;
            }
            movimentos[novaPosicao.getLinha()][novaPosicao.getColuna()] = true;
            if (existePecaInimiga(novaPosicao)) {
                break;
            }
        }
        
        return movimentos;
    }
    
    @Override
    public String toString() {
        return "Q";
    }
}
