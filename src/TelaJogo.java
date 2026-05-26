import javax.swing.JPanel;
import javax.swing.Timer; // A classe mágica que faz o tempo passar
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class TelaJogo extends JPanel implements KeyListener, ActionListener {
    private Jogador cavaleiro;
    private Timer gameLoop;

    public TelaJogo() {
        cavaleiro = new Jogador(380, 280);
        setFocusable(true);
        addKeyListener(this);


        gameLoop = new Timer(30, this);
        gameLoop.start(); // O tempo começa a correr aqui!
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cavaleiro != null) {
            cavaleiro.desenhar(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cavaleiro.atualizar();
        repaint(); // Atualiza a tela automaticamente
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        if (tecla == KeyEvent.VK_UP) cavaleiro.moverCima();
        if (tecla == KeyEvent.VK_DOWN) cavaleiro.moverBaixo();
        if (tecla == KeyEvent.VK_LEFT) cavaleiro.moverEsquerda();
        if (tecla == KeyEvent.VK_RIGHT) cavaleiro.moverDireita();
        if (tecla == KeyEvent.VK_Z)  cavaleiro.atacar();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}