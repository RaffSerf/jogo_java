import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Jogador {

    private int x, y;
    private int velocidade;

    private BufferedImage[] spritesCima = new BufferedImage[6];
    private BufferedImage[] spritesBaixo = new BufferedImage[6];
    private BufferedImage[] spritesFrente = new BufferedImage[6];
    private BufferedImage[] spritesMeio = new BufferedImage[6];

    private int direcaoAtual;
    private int frameAtual;
    private int timerAnimacao;
    private int velocidadeAnimacao;

    private BufferedImage[] atkCima = new BufferedImage[6];
    private BufferedImage[] atkBaixo = new BufferedImage[6];
    private BufferedImage[] atkEsquerda = new BufferedImage[6];
    private BufferedImage[] atkDireita = new BufferedImage[6];

    private boolean atacando = false;
    private int frameAtk = 0;
    private int timerAtk = 0;
    private int velocidadeAtaque = 9; // Um controle independente para a espada!

    public Jogador(int xInicial, int yInicial) {
        this.x = xInicial;
        this.y = yInicial;
        this.velocidade = 5;
        this.direcaoAtual = 1;

        this.frameAtual = 0;
        this.timerAnimacao = 0;
        this.velocidadeAnimacao = 2;

        carregarImagens();
    }

    private void carregarImagens() {
        try {
            // IMAGENS DE ANDAR
            // Andar Cima
            spritesCima[0] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_cima_1.png"));
            spritesCima[1] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_cima_2.png"));
            spritesCima[2] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_cima_3.png"));
            spritesCima[3] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_cima_4.png"));
            spritesCima[4] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_cima_5.png"));
            spritesCima[5] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_cima_6.png"));

            // Andar Baixo
            spritesBaixo[0] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_baixo_1.png"));
            spritesBaixo[1] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_baixo_2.png"));
            spritesBaixo[2] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_baixo_3.png"));
            spritesBaixo[3] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_baixo_4.png"));
            spritesBaixo[4] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_baixo_5.png"));
            spritesBaixo[5] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_baixo_6.png"));

            // Andar Frente

            spritesFrente[0] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_frente_1.png"));
            spritesFrente[1] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_frente_2.png"));
            spritesFrente[2] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_frente_3.png"));
            spritesFrente[3] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_frente_4.png"));
            spritesFrente[4] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_frente_5.png"));
            spritesFrente[5] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_frente_6.png"));

            //Andar Meio
            spritesMeio[0] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_meio_1.png"));
            spritesMeio[1] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_meio_2.png"));
            spritesMeio[2] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_meio_3.png"));
            spritesMeio[3] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_meio_4.png"));
            spritesMeio[4] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_meio_5.png"));
            spritesMeio[5] = ImageIO.read(getClass().getResourceAsStream("personagem/play/Boneco_andando_meio_6.png"));

            // IMAGENS DE ATAQUE (Caminhos Corrigidos)
            // IMAGENS DE ATAQUE (Ajuste o "r1, r2, r3, r4" conforme a sua imagem real!)

// Se a linha 1 (r1) da sua imagem for ele atacando para a DIREITA:
            atkDireita[0] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r3-c1.png"));
            atkDireita[1] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r3-c2.png"));
            atkDireita[2] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r3-c3.png"));
            atkDireita[3] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r3-c4.png"));
            atkDireita[4] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r3-c5.png"));
            atkDireita[5] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r3-c6.png"));

// Se a linha 2 (r2) da sua imagem for ele atacando para CIMA:
            atkCima[0] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r4-c1.png"));
            atkCima[1] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r4-c2.png"));
            atkCima[2] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r4-c3.png"));
            atkCima[3] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r4-c4.png"));
            atkCima[4] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r4-c5.png"));
            atkCima[5] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r4-c6.png"));




// Se a linha 3 (r3) da sua imagem for ele atacando para BAIXO:
            atkBaixo[0] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r1-c1.png"));
            atkBaixo[1] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r1-c2.png"));
            atkBaixo[2] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r1-c3.png"));
            atkBaixo[3] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r1-c4.png"));
            atkBaixo[4] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r1-c5.png"));
            atkBaixo[5] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r1-c6.png"));

// Se a linha 4 (r4) da sua imagem for ele atacando para a ESQUERDA:
            atkEsquerda[0] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r2-c1.png"));
            atkEsquerda[1] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r2-c2.png"));
            atkEsquerda[2] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r2-c3.png"));
            atkEsquerda[3] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r2-c4.png"));
            atkEsquerda[4] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r2-c5.png"));
            atkEsquerda[5] = ImageIO.read(getClass().getResourceAsStream("ataque/z/splitanimage-r2-c6.png"));
        } catch (IOException e) {
            System.out.println("Erro crítico ao carregar sprites: " + e.getMessage());
        }
    }

    private void atualizarAnimacao() {
        if (!atacando) {
            timerAnimacao++;
            if (timerAnimacao >= velocidadeAnimacao) {
                timerAnimacao = 0;
                frameAtual++;
                if (frameAtual >= 6) {
                    frameAtual = 0;
                }
            }
        }
    }

    public void moverCima() {
        if (!atacando) { y -= velocidade; direcaoAtual = 0; atualizarAnimacao(); }
    }
    public void moverBaixo() {
        if (!atacando) { y += velocidade; direcaoAtual = 1; atualizarAnimacao(); }
    }
    public void moverEsquerda() {
        if (!atacando) { x -= velocidade; direcaoAtual = 2; atualizarAnimacao(); }
    }
    public void moverDireita() {
        if (!atacando) { x += velocidade; direcaoAtual = 3; atualizarAnimacao(); }
    }

    public void atacar() {
        if (!atacando) {
            atacando = true;
            frameAtk = 0;
            timerAtk = 0;
        }
    }

    public void atualizar() {
        if (atacando) {
            timerAtk++;
            // Usamos a velocidadeAtaque independente aqui
            if (timerAtk >= velocidadeAtaque) {
                timerAtk = 0;
                frameAtk++;

                if (frameAtk >= 2) {
                    atacando = false; // Isso destrava o boneco para andar novamente!
                    frameAtk = 0;
                }
            }
        }
    }

    public void desenhar(Graphics g) {

        // --- ADICIONE ESTE BLOCO AQUI ---
        // Convertemos o 'g' simples para 'Graphics2D' (um pincel mais poderoso)
        // Isso é herança/polimorfismo em ação no POO!
        Graphics2D g2d = (Graphics2D) g;

        // Ativamos a configuração mágica para Pixel Art (Nearest Neighbor)
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                java.awt.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // ---------------------------------

        // Tamanho para o personagem apenas andando (esticando para 64x64)
        int larguraExibicao = 64;
        int alturaExibicao = 64;

        // Tamanho para a caixa de ataque (Proporcionalmente maior para caber a espada!)
        // Se a imagem nativa do ataque for o dobro do tamanho da de andar, dobramos aqui também.
        int larguraAtaque = 128;
        int alturaAtaque = 128;

        // Compensação (offset) para centralizar o corpo do ataque com o corpo da caminhada.
        // Como esticamos a imagem, talvez seja necessário puxá-la um pouco mais para a esquerda e para cima.
        int offsetX = -32;
        int offsetY = -32;

        if (atacando) {
            // Usamos drawImage com redimensionamento para o ATAQUE
            // Note que agora usamos g2d!
            if (direcaoAtual == 0 && atkCima[frameAtk] != null) {
                g2d.drawImage(atkCima[frameAtk], x + offsetX, y + offsetY, larguraAtaque, alturaAtaque, null);
            } else if (direcaoAtual == 1 && atkBaixo[frameAtk] != null) {
                g2d.drawImage(atkBaixo[frameAtk], x + offsetX, y + offsetY, larguraAtaque, alturaAtaque, null);
            } else if (direcaoAtual == 2 && atkEsquerda[frameAtk] != null) {
                g2d.drawImage(atkEsquerda[frameAtk], x + offsetX, y + offsetY, larguraAtaque, alturaAtaque, null);
            } else if (direcaoAtual == 3 && atkDireita[frameAtk] != null) {
                g2d.drawImage(atkDireita[frameAtk], x + offsetX, y + offsetY, larguraAtaque, alturaAtaque, null);
            } else {
                g2d.setColor(Color.RED);
                g2d.fillRect(x, y, larguraExibicao, alturaExibicao);
            }
        }
        else {
            // Usamos drawImage com redimensionamento para ANDAR
            if (direcaoAtual == 0 && spritesCima[frameAtual] != null) {
                g2d.drawImage(spritesCima[frameAtual], x, y, larguraExibicao, alturaExibicao, null);
            } else if (direcaoAtual == 1 && spritesBaixo[frameAtual] != null) {
                g2d.drawImage(spritesBaixo[frameAtual], x, y, larguraExibicao, alturaExibicao, null);
            } else if (direcaoAtual == 2 && spritesMeio[frameAtual] != null) {
                g2d.drawImage(spritesMeio[frameAtual], x, y, larguraExibicao, alturaExibicao, null);
            } else if (direcaoAtual == 3 && spritesFrente[frameAtual] != null) {
                g2d.drawImage(spritesFrente[frameAtual], x, y, larguraExibicao, alturaExibicao, null);
            } else {
                g2d.setColor(Color.BLUE);
                g2d.fillRect(x, y, larguraExibicao, alturaExibicao);
            }
        }
    }
}