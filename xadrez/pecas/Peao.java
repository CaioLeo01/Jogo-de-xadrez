package xadrez.pecas;

import xadrez.jogo.Cor;
import xadrez.tabuleiro.Posicao;
import xadrez.tabuleiro.Tabuleiro;

public class Peao extends PecaXadrez {
    private boolean primeiroMovimento;

    public Peao(Posicao posicao, Cor cor, Tabuleiro tabuleiro) {
        super(posicao, cor, tabuleiro);
        this.primeiroMovimento = true;
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[8][8];
        int direcao = (cor == Cor.BRANCO) ? -1 : 1; // Peões brancos movem para cima (-1), pretos para baixo (+1)
        
        // Movimento básico: uma casa para frente
        Posicao novaPosicao = new Posicao(posicao.getLinha() + direcao, posicao.getColuna());
        if (tabuleiro.posicaoValida(novaPosicao) && !existePeca(novaPosicao)) {
            movimentos[novaPosicao.getLinha()][novaPosicao.getColuna()] = true;
            
            // Primeiro movimento: opção de mover duas casas
            if (primeiroMovimento) {
                Posicao segundaCasa = new Posicao(posicao.getLinha() + (2 * direcao), posicao.getColuna());
                if (tabuleiro.posicaoValida(segundaCasa) && !existePeca(segundaCasa)) {
                    movimentos[segundaCasa.getLinha()][segundaCasa.getColuna()] = true;
                }
            }
        }
        
        // Capturas na diagonal
        // Diagonal esquerda
        novaPosicao = new Posicao(posicao.getLinha() + direcao, posicao.getColuna() - 1);
        if (tabuleiro.posicaoValida(novaPosicao) && existePecaInimiga(novaPosicao)) {
            movimentos[novaPosicao.getLinha()][novaPosicao.getColuna()] = true;
        }
        
        // Diagonal direita
        novaPosicao = new Posicao(posicao.getLinha() + direcao, posicao.getColuna() + 1);
        if (tabuleiro.posicaoValida(novaPosicao) && existePecaInimiga(novaPosicao)) {
            movimentos[novaPosicao.getLinha()][novaPosicao.getColuna()] = true;
        }
        
        return movimentos;
    }
    
    public void mover() {
        primeiroMovimento = false;
    }
    
    @Override
    public String toString() {
        return "P";
    }
}
