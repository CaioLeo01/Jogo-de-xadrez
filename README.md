# 🏁 Jogo de Xadrez em Java

## 📝 Descrição do Projeto

Este é um jogo de xadrez desenvolvido em Java, com uma interface gráfica Swing que permite a dois jogadores disputarem uma partida de xadrez tradicional. O projeto implementa todas as regras clássicas do xadrez, incluindo movimentos especiais, xeque, xeque-mate e captura de peças.

## ✨ Funcionalidades Principais

### 1. Interface Gráfica Interativa
- Tabuleiro de xadrez 8x8 totalmente funcional
- Destaque de movimentos possíveis
- Feedback visual para seleção de peças
- Numeração de linhas e colunas no tabuleiro

### 2. Regras do Jogo
- Movimentação de peças segundo as regras tradicionais do xadrez
- Verificação automática de movimentos válidos
- Detecção de xeque e xeque-mate
- Alternância de turnos entre jogadores

### 3. Modos de Jogo
- Jogador vs Jogador (PvP)
- Suporte para personalização de nomes dos jogadores

## 🎮 Como Funciona

### Inicialização do Jogo
- Ao iniciar, o jogo solicita os nomes dos jogadores
- Carrega as imagens das peças de xadrez
- Configura a janela com layout personalizado

### Mecânica de Jogo
1. **Seleção de Peças**
   - Clique em uma peça para selecionar
   - Sistema destaca movimentos possíveis
   - Valida se o movimento é permitido para a peça

2. **Movimentação**
   - Clique na casa de destino para mover
   - Verifica regras específicas de cada tipo de peça
   - Atualiza o estado do tabuleiro

3. **Fim de Jogo**
   - Detecta xeque-mate automaticamente
   - Mostra mensagem com o vencedor
   - Opção de reiniciar ou encerrar o jogo

## 🛠 Componentes Principais

### Classes Chave
- [InterfaceGrafica](cci:2://file:///c:/Users/CAIO/Desktop/Estudo/Jogo%20de%20xadrez/xadrez/ui/InterfaceGrafica.java:10:0-289:1): Gerencia a interface do usuário
- [Jogo](cci:1://file:///c:/Users/CAIO/Desktop/Estudo/Jogo%20de%20xadrez/xadrez/ui/InterfaceGrafica.java:284:4-288:5): Lógica central do jogo de xadrez
- `RecursosImagem`: Carrega e gerencia imagens das peças
- `Posicao`: Representa posições no tabuleiro

### Tratamento de Erros
- Validação de movimentos
- Mensagens de erro personalizadas
- Prevenção de movimentos inválidos

## 🚀 Melhorias Recentes

- Tratamento de erros mais robusto
- Opção de reiniciar jogo após xeque-mate
- Feedback visual aprimorado
- Mensagens de erro mais informativas

## 🔧 Requisitos
- Java 8 ou superior
- Biblioteca Swing
