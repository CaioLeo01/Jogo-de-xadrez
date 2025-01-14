package xadrez.pecas;

import xadrez.jogo.Cor;
import xadrez.tabuleiro.Posicao;
import xadrez.tabuleiro.Tabuleiro;

public class Bispo extends PecaXadrez {
    
    public Bispo(Posicao posicao, Cor cor, Tabuleiro tabuleiro) {
        super(posicao, cor, tabuleiro);
    }
    
    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[8][8];
        
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
        return "B";
    }
}
