import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TelaJogo extends JPanel implements KeyListener, ActionListener {

    // --- MÁQUINA DE ESTADOS DO JOGO ---
    private static final int ESTADO_MENU = 0;
    private static final int ESTADO_HISTORIA = 1; // NOVO ESTADO ADICIONADO!
    private static final int ESTADO_JOGANDO = 2;
    private int estadoAtual = ESTADO_MENU;

    // --- VARIÁVEIS DA HISTÓRIA ---
    private int paginaHistoria = 0; // Controla qual parte da história está aparecendo
    // Matriz de textos (Array 2D) para separar as falas em páginas e linhas
    private String[][] textoHistoria = {
            { "Em um universo distante, existe a MathWorld,",
                    "um reino onde a inteligência em exatas",
                    "é algo obrigatório." },

            { "Nesse mundo, onde cada passo é uma operação",
                    "matemática e cada respiro é uma raiz quadrada,",
                    "um jovem herói inicia sua jornada..." },

            { "Ele parte em uma aventura inexplicável",
                    "para salvar alguém muito importante para ele.",
                    "Seria essa a sua amada?" }
    };

    private Jogador cavaleiro;
    private Timer gameLoop;

    public TelaJogo() {
        cavaleiro = new Jogador(380, 280);

        setFocusable(true);
        addKeyListener(this);
        setBackground(new Color(20, 20, 30));

        gameLoop = new Timer(30, this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Controle de renderização baseado no estado
        if (estadoAtual == ESTADO_MENU) {
            desenharMenu(g2d);
        } else if (estadoAtual == ESTADO_HISTORIA) {
            desenharHistoria(g2d); // Chama o novo método de desenhar a história
        } else if (estadoAtual == ESTADO_JOGANDO) {
            cavaleiro.desenhar(g2d);
            desenharHUD(g2d);
        }

        g2d.dispose();
    }

    private void desenharMenu(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 38));
        g2d.drawString("THE LAST VARIABLE", 200, 250);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 18));
        g2d.drawString("Pressione [ ENTER ] para Iniciar", 225, 330);
    }

    // --- NOVO MÉTODO: DESENHAR HISTÓRIA ---
    private void desenharHistoria(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 22));

        // Pega as linhas da página atual
        String[] linhasDaPagina = textoHistoria[paginaHistoria];

        // Loop 'for' para desenhar as linhas uma embaixo da outra
        int yAtual = 200; // Posição vertical inicial do texto
        for (String linha : linhasDaPagina) {
            g2d.drawString(linha, 70, yAtual);
            yAtual += 40; // Desce 40 pixels para escrever a próxima linha
        }

        // Dica piscante no rodapé para o jogador saber como avançar
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
        g2d.drawString("Pressione [ ENTER ] para avançar >>", 200, 480);
    }

    private void desenharHUD(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
        g2d.drawString("Vida: " + cavaleiro.getVida(), 20, 35);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (estadoAtual == ESTADO_JOGANDO) {
            cavaleiro.atualizar();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        // Lógica de transição de telas
        if (estadoAtual == ESTADO_MENU) {
            if (tecla == KeyEvent.VK_ENTER) {
                // Sai do menu e vai para a página 0 da história
                estadoAtual = ESTADO_HISTORIA;
                paginaHistoria = 0;
            }
        }
        else if (estadoAtual == ESTADO_HISTORIA) {
            if (tecla == KeyEvent.VK_ENTER) {
                paginaHistoria++; // Avança a página

                // Se acabaram as páginas, começa o jogo pra valer!
                if (paginaHistoria >= textoHistoria.length) {
                    estadoAtual = ESTADO_JOGANDO;
                }
            }
        }
        else if (estadoAtual == ESTADO_JOGANDO) {
            // Controles do jogador
            if (tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_W) cavaleiro.mover(0, -1);
            if (tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_S) cavaleiro.mover(0, 1);
            if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_A) cavaleiro.mover(-1, 0);
            if (tecla == KeyEvent.VK_RIGHT || tecla == KeyEvent.VK_D) cavaleiro.mover(1, 0);

            if (tecla == KeyEvent.VK_Z) cavaleiro.atacar();
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}