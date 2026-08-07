import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TelaJogo extends JPanel implements KeyListener, ActionListener {
    private Jogador cavaleiro;
    private Inimigo slime; // O nosso primeiro inimigo instanciado!
    private Timer gameLoop;

    // Elementos do HUD de Vida do Jogador
    private BufferedImage hudVida3;
    private BufferedImage hudVida2;
    private BufferedImage hudVida1;

    public TelaJogo() {
        cavaleiro = new Jogador(380, 280);

        // Criamos o Slime em uma posição específica do mapa (Ex: X=200, Y=200)
        slime = new Inimigo(200, 200);

        setFocusable(true);
        addKeyListener(this);

        // Carrega as imagens do HUD
        try {
            hudVida3 = ImageIO.read(getClass().getResourceAsStream("HUD/vida_cheia.png"));
            hudVida2 = ImageIO.read(getClass().getResourceAsStream("HUD/vida_media.png"));
            hudVida1 = ImageIO.read(getClass().getResourceAsStream("HUD/vida_baixa.png"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar as imagens do HUD: " + e.getMessage());
        }

        gameLoop = new Timer(30, this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. Desenha o Slime se ele não estiver morto
        //if (slime != null && !slime.isMorto()) {
           // slime.desenhar(g2d);
      // }

        // 2. Desenha o Cavaleiro principal
        if (cavaleiro != null) {
            cavaleiro.desenhar(g);
        }

        // 3. Desenha a interface (HUD) fixa por cima de tudo
        desenharHUD(g2d);
    }

    private void desenharHUD(Graphics2D g2d) {
        if (cavaleiro == null) return;
        BufferedImage hudAtual = null;

        if (cavaleiro.getVida() == 3) hudAtual = hudVida3;
        else if (cavaleiro.getVida() == 2) hudAtual = hudVida2;
        else if (cavaleiro.getVida() == 1) hudAtual = hudVida1;

        if (hudAtual != null) {
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                    java.awt.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            // Desenha o HUD no tamanho original no canto (X:20, Y:20)
            g2d.drawImage(hudAtual, 20, 20, hudAtual.getWidth(), hudAtual.getHeight(), null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Atualiza a lógica do jogador
        cavaleiro.atualizar();

        // Se o slime estiver vivo, atualiza a IA dele passando a posição do jogador
        if (slime != null && !slime.isMorto()) {
            slime.atualizar(cavaleiro.getX(), cavaleiro.getY());

            // Executa o teste de colisão do ataque do jogador
            verificarAtaqueJogador();
        }

        repaint();
    }

    // Lógica Matemática de Colisão do Combate
    private void verificarAtaqueJogador() {
        // Só verificamos colisão se o jogador estiver de fato executando a animação de ataque
        if (cavaleiro.isAtacando()) {

            // Criamos uma caixa para representar o alcance da espada (64x64 pixels)
            int alcanceEspada = 64;
            Rectangle areaCorte = null;

            // Dependendo da direção que o cavaleiro olha, a espada bate em um lugar diferente
            if (cavaleiro.getDirecaoAtual() == 0) { // Cima
                areaCorte = new Rectangle(cavaleiro.getX(), cavaleiro.getY() - alcanceEspada, 64, alcanceEspada);
            } else if (cavaleiro.getDirecaoAtual() == 1) { // Baixo
                areaCorte = new Rectangle(cavaleiro.getX(), cavaleiro.getY() + 64, 64, alcanceEspada);
            } else if (cavaleiro.getDirecaoAtual() == 2) { // Esquerda
                areaCorte = new Rectangle(cavaleiro.getX() - alcanceEspada, cavaleiro.getY(), alcanceEspada, 64);
            } else if (cavaleiro.getDirecaoAtual() == 3) { // Direita
                areaCorte = new Rectangle(cavaleiro.getX() + 64, cavaleiro.getY(), alcanceEspada, 64);
            }

            // Se a área do corte da espada encostar na Hitbox (getBounds) do Slime...
            if (areaCorte != null && areaCorte.intersects(slime.getBounds())) {
                slime.receberDano(15); // Slime perde 15 de vida por frame/golpe válido!
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        if (tecla == KeyEvent.VK_UP) cavaleiro.moverCima();
        if (tecla == KeyEvent.VK_DOWN) cavaleiro.moverBaixo();
        if (tecla == KeyEvent.VK_LEFT) cavaleiro.moverEsquerda();
        if (tecla == KeyEvent.VK_RIGHT) cavaleiro.moverDireita();
        if (tecla == KeyEvent.VK_Z)  cavaleiro.atacar();

        if (tecla == KeyEvent.VK_MINUS) {
            cavaleiro.setVida(cavaleiro.getVida() - 1);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}