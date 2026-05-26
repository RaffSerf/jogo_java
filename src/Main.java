import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Protótipo");
        TelaJogo jogo = new TelaJogo();

        janela.add(jogo);
        janela.setSize(800, 600); // Resolução da janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.setLocationRelativeTo(null); // Centraliza no monitor
        janela.setVisible(true); // Faz a janela aparecer
    }

}