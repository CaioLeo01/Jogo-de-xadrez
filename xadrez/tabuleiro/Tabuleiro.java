package xadrez.tabuleiro;

import xadrez.pecas.*;
import xadrez.jogo.Cor;

public class Tabuleiro {
    private PecaXadrez[][] pecas;
    private static final int TAMANHO = 8;
    
    public Tabuleiro() {
        pecas = new PecaXadrez[TAMANHO][TAMANHO];
        inicializarTabuleiro();
    }
    
    private void inicializarTabuleiro() {
        // Peças brancas
        colocarPeca(new Torre(new Posicao(7, 0), Cor.BRANCO, this));
        colocarPeca(new Cavalo(new Posicao(7, 1), Cor.BRANCO, this));
        colocarPeca(new Bispo(new Posicao(7, 2), Cor.BRANCO, this));
        colocarPeca(new Rainha(new Posicao(7, 3), Cor.BRANCO, this));
        colocarPeca(new Rei(new Posicao(7, 4), Cor.BRANCO, this));
        colocarPeca(new Bispo(new Posicao(7, 5), Cor.BRANCO, this));
        colocarPeca(new Cavalo(new Posicao(7, 6), Cor.BRANCO, this));
        colocarPeca(new Torre(new Posicao(7, 7), Cor.BRANCO, this));
        
        for (int j = 0; j < TAMANHO; j++) {
            colocarPeca(new Peao(new Posicao(6, j), Cor.BRANCO, this));
        }
        
        // Peças pretas
        colocarPeca(new Torre(new Posicao(0, 0), Cor.PRETO, this));
        colocarPeca(new Cavalo(new Posicao(0, 1), Cor.PRETO, this));
        colocarPeca(new Bispo(new Posicao(0, 2), Cor.PRETO, this));
        colocarPeca(new Rainha(new Posicao(0, 3), Cor.PRETO, this));
        colocarPeca(new Rei(new Posicao(0, 4), Cor.PRETO, this));
        colocarPeca(new Bispo(new Posicao(0, 5), Cor.PRETO, this));
        colocarPeca(new Cavalo(new Posicao(0, 6), Cor.PRETO, this));
        colocarPeca(new Torre(new Posicao(0, 7), Cor.PRETO, this));
        
        for (int j = 0; j < TAMANHO; j++) {
            colocarPeca(new Peao(new Posicao(1, j), Cor.PRETO, this));
        }
    }
    
    public boolean posicaoValida(Posicao posicao) {
        if (posicao == null) return false;
        return posicao.getLinha() >= 0 && posicao.getLinha() < TAMANHO &&
               posicao.getColuna() >= 0 && posicao.getColuna() < TAMANHO;
    }
    
    public PecaXadrez getPeca(Posicao posicao) {
        if (!posicaoValida(posicao)) {
            return null;
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }
    
    public void colocarPeca(PecaXadrez peca) {
        if (peca == null || !posicaoValida(peca.getPosicao())) {
            throw new IllegalArgumentException("Posição inválida para colocar a peça");
        }
        pecas[peca.getPosicao().getLinha()][peca.getPosicao().getColuna()] = peca;
    }
    
    public PecaXadrez removerPeca(Posicao posicao) {
        if (!posicaoValida(posicao)) {
            throw new IllegalArgumentException("Posição inválida");
        }
        PecaXadrez peca = getPeca(posicao);
        if (peca != null) {
            pecas[posicao.getLinha()][posicao.getColuna()] = null;
        }
        return peca;
    }
    
    public void moverPeca(Posicao origem, Posicao destino) {
        if (!posicaoValida(origem) || !posicaoValida(destino)) {
            throw new IllegalArgumentException("Posição inválida para mover a peça");
        }
        
        PecaXadrez peca = removerPeca(origem);
        if (peca == null) {
            throw new IllegalArgumentException("Não existe peça na posição de origem");
        }
        
        PecaXadrez pecaCapturada = removerPeca(destino);
        peca.setPosicao(destino);
        colocarPeca(peca);
        
        if (peca instanceof Peao) {
            ((Peao) peca).mover();
        }
    }
    
    public boolean[][] getMovimentosPossiveis(Posicao posicao) {
        PecaXadrez peca = getPeca(posicao);
        if (peca == null) {
            return new boolean[TAMANHO][TAMANHO];
        }
        return peca.movimentosPossiveis();
    }
    
    public static int getTamanho() {
        return TAMANHO;
    }
}
