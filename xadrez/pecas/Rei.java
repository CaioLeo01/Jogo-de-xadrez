package xadrez.pecas;

import xadrez.jogo.Cor;
import xadrez.tabuleiro.Posicao;
import xadrez.tabuleiro.Tabuleiro;

public class Rei extends PecaXadrez {
    
    public Rei(Posicao posicao, Cor cor, Tabuleiro tabuleiro) {
        super(posicao, cor, tabuleiro);
    }
    
    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[8][8];
        
        // Todas as direções possíveis para o rei (uma casa em qualquer direção)
        int[][] direcoes = {
            {-1, -1}, {-1, 0}, {-1, 1},  // Superior esquerda, cima, superior direita
            {0, -1},           {0, 1},    // Esquerda, direita
            {1, -1},  {1, 0},  {1, 1}     // Inferior esquerda, baixo, inferior direita
        };
        
        for (int[] direcao : direcoes) {
            Posicao novaPosicao = new Posicao(
                posicao.getLinha() + direcao[0],
                posicao.getColuna() + direcao[1]
            );
            
            if (tabuleiro.posicaoValida(novaPosicao) && podeMover(novaPosicao)) {
                movimentos[novaPosicao.getLinha()][novaPosicao.getColuna()] = true;
            }
        }
        
        return movimentos;
    }
    
    @Override
    public String toString() {
        return "R";
    }
}
