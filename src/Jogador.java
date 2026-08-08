import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Jogador {

    // Atributos de Posição e Estado
    private int x;
    private int y;
    private int velocidade = 5;
    private int vida = 3;

    // Direção: 0 = Cima, 1 = Baixo, 2 = Esquerda, 3 = Direita
    private int direcaoAtual = 1;
    private boolean atacando = false;
    private boolean movendo = false;

    // Animação
    private int frameAtual = 0;
    private int timerAnimacao = 0;

    // Sprites de Movimento (6 frames cada)
    private BufferedImage[] spritesCima = new BufferedImage[6];
    private BufferedImage[] spritesBaixo = new BufferedImage[6];
    private BufferedImage[] spritesFrente = new BufferedImage[6];
    private BufferedImage[] spritesMeio = new BufferedImage[6];

    // Sprites de Ataque (6 frames cada)
    private BufferedImage[] atkCima = new BufferedImage[6];
    private BufferedImage[] atkBaixo = new BufferedImage[6];
    private BufferedImage[] atkEsquerda = new BufferedImage[6];
    private BufferedImage[] atkDireita = new BufferedImage[6];

    public Jogador(int xInicial, int yInicial) {
        this.x = xInicial;
        this.y = yInicial;
        carregarImagens();
    }

    private void carregarImagens() {
        try {
            // IMAGENS DE ANDAR
            // Andar Cima
            spritesCima[0] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_cima_1.png"));
            spritesCima[1] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_cima_2.png"));
            spritesCima[2] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_cima_3.png"));
            spritesCima[3] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_cima_4.png"));
            spritesCima[4] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_cima_5.png"));
            spritesCima[5] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_cima_6.png"));

            // Andar Baixo
            spritesBaixo[0] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_baixo_1.png"));
            spritesBaixo[1] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_baixo_2.png"));
            spritesBaixo[2] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_baixo_3.png"));
            spritesBaixo[3] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_baixo_4.png"));
            spritesBaixo[4] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_baixo_5.png"));
            spritesBaixo[5] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_baixo_6.png"));

            // Andar Frente
            spritesFrente[0] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_frente_1.png"));
            spritesFrente[1] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_frente_2.png"));
            spritesFrente[2] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_frente_3.png"));
            spritesFrente[3] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_frente_4.png"));
            spritesFrente[4] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_frente_5.png"));
            spritesFrente[5] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_frente_6.png"));

            //Andar Meio (Esquerda)
            spritesMeio[0] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_meio_1.png"));
            spritesMeio[1] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_meio_2.png"));
            spritesMeio[2] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_meio_3.png"));
            spritesMeio[3] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_meio_4.png"));
            spritesMeio[4] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_meio_5.png"));
            spritesMeio[5] = ImageIO.read(getClass().getResourceAsStream("/personagem/play/Boneco_andando_meio_6.png"));

            // IMAGENS DE ATAQUE (Ajuste o "r1, r2, r3, r4" conforme a sua imagem real!)
            // Se a linha 1 (r1) da sua imagem for ele atacando para a DIREITA:
            atkDireita[0] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r3-c1.png"));
            atkDireita[1] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r3-c2.png"));
            atkDireita[2] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r3-c3.png"));
            atkDireita[3] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r3-c4.png"));
            atkDireita[4] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r3-c5.png"));
            atkDireita[5] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r3-c6.png"));

            // Se a linha 2 (r2) da sua imagem for ele atacando para CIMA:
            atkCima[0] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r4-c1.png"));
            atkCima[1] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r4-c2.png"));
            atkCima[2] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r4-c3.png"));
            atkCima[3] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r4-c4.png"));
            atkCima[4] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r4-c5.png"));
            atkCima[5] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r4-c6.png"));

            // Se a linha 3 (r3) da sua imagem for ele atacando para BAIXO:
            atkBaixo[0] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r1-c1.png"));
            atkBaixo[1] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r1-c2.png"));
            atkBaixo[2] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r1-c3.png"));
            atkBaixo[3] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r1-c4.png"));
            atkBaixo[4] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r1-c5.png"));
            atkBaixo[5] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r1-c6.png"));

            // Se a linha 4 (r4) da sua imagem for ele atacando para a ESQUERDA:
            atkEsquerda[0] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r2-c1.png"));
            atkEsquerda[1] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r2-c2.png"));
            atkEsquerda[2] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r2-c3.png"));
            atkEsquerda[3] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r2-c4.png"));
            atkEsquerda[4] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r2-c5.png"));
            atkEsquerda[5] = ImageIO.read(getClass().getResourceAsStream("/ataque/z/splitanimage-r2-c6.png"));

        } catch (IOException e) {
            System.out.println("Erro crítico ao carregar sprites: " + e.getMessage());
        }
    }

    public void atualizar() {
        // Controle da Animação
        timerAnimacao++;
        if (timerAnimacao >= 4) {
            timerAnimacao = 0;
            frameAtual++;

            if (atacando) {
                if (frameAtual >= 6) {
                    atacando = false;
                    frameAtual = 0;
                }
            } else if (movendo) {
                if (frameAtual >= 6) {
                    frameAtual = 0;
                }
            } else {
                frameAtual = 0;
            }
        }
        movendo = false;
    }

    public void mover(int dx, int dy) {
        if (!atacando) {
            this.x += dx * velocidade;
            this.y += dy * velocidade;
            this.movendo = true;

            if (dy < 0) direcaoAtual = 0;
            else if (dy > 0) direcaoAtual = 1;
            else if (dx < 0) direcaoAtual = 2;
            else if (dx > 0) direcaoAtual = 3;
        }
    }

    public void atacar() {
        if (!atacando) {
            atacando = true;
            frameAtual = 0;
            timerAnimacao = 0;
        }
    }

    public void desenhar(Graphics2D g2d) {
        BufferedImage spriteAtual = null;

        // 1. Descobrir qual imagem exibir
        if (atacando) {
            switch (direcaoAtual) {
                case 0: spriteAtual = atkCima[frameAtual]; break;
                case 1: spriteAtual = atkBaixo[frameAtual]; break;
                case 2: spriteAtual = atkEsquerda[frameAtual]; break;
                case 3: spriteAtual = atkDireita[frameAtual]; break;
            }
        } else {
            switch (direcaoAtual) {
                case 0: spriteAtual = spritesCima[frameAtual]; break;
                case 1: spriteAtual = spritesBaixo[frameAtual]; break;
                case 2: spriteAtual = spritesMeio[frameAtual]; break;   // Esquerda
                case 3: spriteAtual = spritesFrente[frameAtual]; break; // Direita
            }
        }

        // 2. Desenhar na tela (TAMANHO ORIGINAL)
        if (spriteAtual != null) {
            if (atacando) {
                // A imagem de ataque costuma ser desenhada um pouco "para trás e para cima"
                // para o corpo do jogador não sair do lugar. Se o ataque ficar torto, mude os números '-16'.
                g2d.drawImage(spriteAtual, x - 16, y - 16, null);
            } else {
                // Desenha a movimentação normal, sem forçar esmagar a imagem
                g2d.drawImage(spriteAtual, x, y, null);
            }
        } else {
            // MODO DE EMERGÊNCIA: Se não achar as imagens na pasta
            if (atacando) {
                g2d.setColor(Color.RED);
                g2d.fillRect(x - 16, y - 16, 96, 96);
            } else {
                g2d.setColor(Color.BLUE);
                g2d.fillRect(x, y, 64, 64);
            }
        }
    }

    // Encapsulamento
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getVida() { return vida; }
    public void setVida(int vida) {
        if (vida >= 0 && vida <= 3) this.vida = vida;
    }
}