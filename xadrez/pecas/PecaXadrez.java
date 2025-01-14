package xadrez.pecas;

import xadrez.jogo.Cor;
import xadrez.tabuleiro.Posicao;
import xadrez.tabuleiro.Tabuleiro;

public abstract class PecaXadrez {
    protected Posicao posicao;
    protected Cor cor;
    protected Tabuleiro tabuleiro;
    
    public PecaXadrez(Posicao posicao, Cor cor, Tabuleiro tabuleiro) {
        this.posicao = posicao;
        this.cor = cor;
        this.tabuleiro = tabuleiro;
    }
    
    public Posicao getPosicao() {
        return posicao;
    }
    
    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }
    
    public Cor getCor() {
        return cor;
    }
    
    public abstract boolean[][] movimentosPossiveis();
    
    protected boolean podeMover(Posicao posicao) {
        if (!tabuleiro.posicaoValida(posicao)) {
            return false;
        }
        PecaXadrez pecaDestino = tabuleiro.getPeca(posicao);
        return pecaDestino == null || pecaDestino.getCor() != this.cor;
    }
    
    protected boolean existePeca(Posicao posicao) {
        return tabuleiro.getPeca(posicao) != null;
    }
    
    protected boolean existePecaInimiga(Posicao posicao) {
        PecaXadrez peca = tabuleiro.getPeca(posicao);
        return peca != null && peca.getCor() != this.cor;
    }
    
    @Override
    public abstract String toString();
}
