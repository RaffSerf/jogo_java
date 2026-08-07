import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Inimigo {
    // Atributos de Posição e Configuração (Encapsulados)
    private int x, y;
    private int velocidade = 1;
    private int largura = 96;
    private int altura = 96;

    // --- SISTEMA DE VIDA E COMBATE ---
    private int vidaMaxima = 100;
    private int vidaAtual = 100;
    private int timerInvulneravel = 0;

    // Guardar a posição inicial para patrulha
    private int xInicial, yInicial;
    private int direcaoPatrulha = 1; // 1 para direita, -1 para esquerda
    private int limitePatrulha = 50;

    // Configurações das Zonas de Alcance
    private int raioVisao = 180;
    private int raioAtaque = 45;

    // --- MÁQUINA DE ESTADOS ---
    private int estadoAtual = 0;
    private final int ESTADO_LONGE = 0;
    private final int ESTADO_PERSEGUINDO = 1;
    private final int ESTADO_ATACANDO = 2;

    // Vetores de Animação
    private BufferedImage[] spritesCaminhada;
    private BufferedImage[] spritesMovDiferente;
    private BufferedImage[] spritesAtaque;

    // Controle da Animação
    private int frameAtual = 0;
    private int timerAnimacao = 0;
    private int velocidadeAnimacao = 10;

    public Inimigo(int xInicial, int yInicial) {
        this.x = xInicial;
        this.y = yInicial;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        carregarImagens();
    }

    private void carregarImagens() {
        spritesCaminhada = new BufferedImage[6];
        spritesMovDiferente = new BufferedImage[3];
        spritesAtaque = new BufferedImage[3];

        try {
            // CORRIGIDO: Agora usa caminhos relativos à pasta de recursos do projeto (mude se sua pasta chamar assets)
            spritesCaminhada[0] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r1-c1.png"));
            spritesCaminhada[1] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r1-c2.png"));
            spritesCaminhada[2] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r2-c1.png"));
            spritesCaminhada[3] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r2-c2.png"));
            spritesCaminhada[4] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r3-c1.png"));
            spritesCaminhada[5] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r3-c2.png"));

            spritesMovDiferente[0] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r1-c1.png"));
            spritesMovDiferente[1] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r1-c2.png"));
            spritesMovDiferente[2] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r1-c1.png"));

            spritesAtaque[0] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r2-c1.png"));
            spritesAtaque[1] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r2-c2.png"));
            spritesAtaque[2] = ImageIO.read(getClass().getResourceAsStream("/inimigo/splitanimage-r3-c1.png"));

        } catch (Exception e) {
            System.out.println("Erro crítico ao carregar imagens do Slime: " + e.getMessage());
        }
    }

    public void atualizar(int jogadorX, int jogadorY) {
        if (timerInvulneravel > 0) timerInvulneravel--;

        int difX = jogadorX - this.x;
        int difY = jogadorY - this.y;
        double distanciaReal = Math.sqrt((difX * difX) + (difY * difY));

        int estadoAnterior = estadoAtual;

        if (distanciaReal <= raioAtaque) {
            estadoAtual = ESTADO_ATACANDO;
        } else if (distanciaReal <= raioVisao) {
            estadoAtual = ESTADO_PERSEGUINDO;
        } else {
            estadoAtual = ESTADO_LONGE;
        }

        if (estadoAtual != estadoAnterior) {
            frameAtual = 0;
            timerAnimacao = 0;
        }

        if (estadoAtual == ESTADO_ATACANDO) {
            atualizarAnimacao(spritesAtaque.length);
        } else if (estadoAtual == ESTADO_PERSEGUINDO) {
            if (this.x < jogadorX) this.x += velocidade;
            if (this.x > jogadorX) this.x -= velocidade;
            if (this.y < jogadorY) this.y += velocidade;
            if (this.y > jogadorY) this.y -= velocidade;
            atualizarAnimacao(spritesCaminhada.length);
        } else {
            this.x += velocidade * direcaoPatrulha;
            if (this.x > xInicial + limitePatrulha) {
                direcaoPatrulha = -1;
            } else if (this.x < xInicial - limitePatrulha) {
                direcaoPatrulha = 1;
            }
            atualizarAnimacao(spritesMovDiferente.length);
        }
    }

    private void atualizarAnimacao(int quantidadeDeFrames) {
        timerAnimacao++;
        if (timerAnimacao >= velocidadeAnimacao) {
            frameAtual++;
            if (frameAtual >= quantidadeDeFrames) {
                frameAtual = 0;
            }
            timerAnimacao = 0;
        }
    }

    public void desenhar(Graphics2D g2) {
        // Se o inimigo estiver morto, não desenha nada
        if (isMorto()) return;

        BufferedImage spriteParaDesenhar = null;

        if (estadoAtual == ESTADO_ATACANDO) {
            spriteParaDesenhar = spritesAtaque[frameAtual];
        } else if (estadoAtual == ESTADO_PERSEGUINDO) {
            spriteParaDesenhar = spritesCaminhada[frameAtual];
        } else {
            spriteParaDesenhar = spritesMovDiferente[frameAtual];
        }

        // CORRIGIDO: Bloco unificado de renderização (evita desenhar a mesma imagem duas vezes)
        if (spriteParaDesenhar != null) {
            g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                    java.awt.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            g2.drawImage(spriteParaDesenhar, x, y, largura, altura, null);

            // --- BARRA DE VIDA ACIMA DA CABEÇA ---
            g2.setColor(java.awt.Color.RED);
            g2.fillRect(x, y - 15, largura, 8);

            g2.setColor(java.awt.Color.GREEN);
            int larguraVida = (int) ((vidaAtual / (double) vidaMaxima) * largura);
            g2.fillRect(x, y - 15, larguraVida, 8);
        }
    }

    public java.awt.Rectangle getBounds() {
        return new java.awt.Rectangle(x, y, largura, altura);
    }

    public void receberDano(int dano) {
        if (timerInvulneravel == 0) {
            vidaAtual -= dano;
            timerInvulneravel = 20;
            if (vidaAtual < 0) vidaAtual = 0;
        }
    }

    // Método utilitário crucial para a TelaJogo saber se deleta ele da lista
    public boolean isMorto() {
        return this.vidaAtual <= 0;
    }

    // Getters e Setters
    public void setX(int novoX) { this.x = novoX; }
    public void setY(int novoY) { this.y = novoY; }
    public int getX() { return x; }
    public int getY() { return y; }
}