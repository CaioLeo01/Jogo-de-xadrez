package xadrez;

import xadrez.ui.TelaInicial;
import javax.swing.SwingUtilities;

public class Program {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaInicial telaInicial = new TelaInicial();
            telaInicial.setVisible(true);
        });
    }
}
