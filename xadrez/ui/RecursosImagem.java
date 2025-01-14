package xadrez.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import xadrez.pecas.PecaXadrez;
import xadrez.jogo.Cor;

public class RecursosImagem {
    private static final Map<String, ImageIcon> pecasIcones = new HashMap<>();
    private static final int TAMANHO_PECA = 70;
    
    public static void carregarImagens() {
        String diretorioBase = System.getProperty("user.dir");
        String[] possiveisCaminhos = {
            "/resources",
            "/xadrez/resources",
            "resources",
            "xadrez/resources"
        };
        
        boolean imagensCarregadas = false;
        
        for (String caminho : possiveisCaminhos) {
            String caminhoCompleto = diretorioBase + File.separator + caminho;
            File diretorio = new File(caminhoCompleto);
            
            if (diretorio.exists() && diretorio.isDirectory()) {
                if (carregarImagensDoDiretorio(diretorio)) {
                    imagensCarregadas = true;
                    System.out.println("Imagens carregadas com sucesso do diretório: " + caminhoCompleto);
                    break;
                }
            }
        }
        
        if (!imagensCarregadas) {
            throw new RuntimeException("Nao foi possível carregar as imagens das peças. Verifique se o diretório 'resources' existe e contém as imagens necessárias.");
        }
    }
    
    private static boolean carregarImagensDoDiretorio(File diretorio) {
        boolean todasCarregadas = true;
        
        // Mapeia os nomes das peças para seus arquivos correspondentes
        Map<String, String> pecasArquivos = new HashMap<>();
        pecasArquivos.put("rei_branco", "rei_branco.png");
        pecasArquivos.put("rei_preto", "rei_preto.png");
        pecasArquivos.put("rainha_branco", "rainha_branca.png");
        pecasArquivos.put("rainha_preto", "rainha_preta.png");
        pecasArquivos.put("torre_branco", "torre_branca.png");
        pecasArquivos.put("torre_preto", "torre_preta.png");
        pecasArquivos.put("bispo_branco", "bispo_branco.png");
        pecasArquivos.put("bispo_preto", "bispo_preto.png");
        pecasArquivos.put("cavalo_branco", "cavalo_branco.png");
        pecasArquivos.put("cavalo_preto", "cavalo_preto.png");
        pecasArquivos.put("peao_branco", "peao_branco.png");
        pecasArquivos.put("peao_preto", "peao_preto.png");
        
        for (Map.Entry<String, String> entrada : pecasArquivos.entrySet()) {
            String nomeIcone = entrada.getKey();
            String nomeArquivo = entrada.getValue();
            File arquivo = new File(diretorio, nomeArquivo);
            
            if (!arquivo.exists()) {
                System.err.println("Arquivo não encontrado: " + arquivo.getAbsolutePath());
                todasCarregadas = false;
                continue;
            }
            
            try {
                BufferedImage imagem = ImageIO.read(arquivo);
                if (imagem == null) {
                    System.err.println("Não foi possível ler a imagem: " + arquivo.getAbsolutePath());
                    todasCarregadas = false;
                    continue;
                }
                
                // Redimensiona a imagem para o tamanho padrão
                Image imagemRedimensionada = imagem.getScaledInstance(TAMANHO_PECA, TAMANHO_PECA, Image.SCALE_SMOOTH);
                ImageIcon icone = new ImageIcon(imagemRedimensionada);
                pecasIcones.put(nomeIcone, icone);
                System.out.println("Imagem carregada: " + nomeIcone + " do arquivo: " + nomeArquivo);
                
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem " + nomeArquivo + ": " + e.getMessage());
                todasCarregadas = false;
            }
        }
        
        return todasCarregadas;
    }
    
    public static ImageIcon getIconePeca(PecaXadrez peca) {
        // Obtém o nome da classe da peça (ex: "ReiXadrez" -> "rei")
        String nomePeca = peca.getClass().getSimpleName().toLowerCase();
        nomePeca = nomePeca.replace("xadrez", "");
        
        // Obtém a cor da peça
        String cor = peca.getCor() == Cor.BRANCO ? "branco" : "preto";
        
        // Monta o nome do ícone
        String nomeIcone = nomePeca + "_" + cor;
        
        // Debug para verificar o nome do ícone sendo procurado
        System.out.println("Procurando ícone: " + nomeIcone);
        
        return getIcone(nomeIcone);
    }
    
    public static ImageIcon getIcone(String nomePeca) {
        ImageIcon icone = pecasIcones.get(nomePeca);
        if (icone == null) {
            System.err.println("Ícone não encontrado: " + nomePeca);
        }
        return icone;
    }
    
    public static void aplicarEfeitoSombra(JButton botao) {
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
    public static void aplicarEfeitoHover(JButton botao, Color corPadrao) {
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(corPadrao.brighter());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(corPadrao);
            }
        });
    }
}
