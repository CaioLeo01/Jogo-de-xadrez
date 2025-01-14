package xadrez.jogo;

public enum Cor {
    BRANCO, PRETO;
    
    public Cor oponente() {
        return this == BRANCO ? PRETO : BRANCO;
    }
}
