import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Projeto LPOO - The Last Variable");
        TelaJogo jogo = new TelaJogo();

        janela.add(jogo);
        janela.setSize(800, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
}