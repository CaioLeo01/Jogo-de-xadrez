package xadrez.pecas;

import xadrez.jogo.Cor;
import xadrez.tabuleiro.Posicao;
import xadrez.tabuleiro.Tabuleiro;

public class Cavalo extends PecaXadrez {
    
    public Cavalo(Posicao posicao, Cor cor, Tabuleiro tabuleiro) {
        super(posicao, cor, tabuleiro);
    }
    
    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] movimentos = new boolean[8][8];
        
        // Movimentos possíveis do cavalo em L
        int[][] movimentosL = {
            {-2, -1}, {-2, 1},  // Dois para cima, um para cada lado
            {2, -1}, {2, 1},    // Dois para baixo, um para cada lado
            {-1, -2}, {1, -2},  // Um para cima/baixo, dois para esquerda
            {-1, 2}, {1, 2}     // Um para cima/baixo, dois para direita
        };
        
        for (int[] movimento : movimentosL) {
            Posicao novaPosicao = new Posicao(
                posicao.getLinha() + movimento[0],
                posicao.getColuna() + movimento[1]
            );
            
            if (tabuleiro.posicaoValida(novaPosicao) && podeMover(novaPosicao)) {
                movimentos[novaPosicao.getLinha()][novaPosicao.getColuna()] = true;
            }
        }
        
        return movimentos;
    }
    
    @Override
    public String toString() {
        return "C";
    }
}
