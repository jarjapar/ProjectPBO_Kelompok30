import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerSet extends GameObject {
    int id;
    int jumpStrength = 20;
    boolean jumping = false;
    int headDiameter = 50;

    public PlayerSet(int x, int y, int width, int height, int id) {
        super(x, y, width, height);
        this.id = id;
    }

    @Override
    public void move() {
        x += xVelocity;
        y += yVelocity;

        // Batas bawah (lantai)
        if (y >= GamePanel.GAME_HEIGHT - height) {
            y = GamePanel.GAME_HEIGHT - height;
            jumping = false;
            yVelocity = 0;
        }

        // gravitasi saat lompat
        if (jumping) {
            yVelocity += 1;
        }

        // batas kiri/kanan
        if (x <= 0) x = 0;
        if (x >= GamePanel.GAME_WIDTH - width) x = GamePanel.GAME_WIDTH - width;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(id == 1 ? Color.blue : Color.red);
        // Kepala
        g.fillOval(x + (width / 2) - (headDiameter / 2), y - 3, headDiameter, headDiameter);
        // Badan
        g.fillRect(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (id == 1) {
            // Player 1: WASD
            if (key == KeyEvent.VK_W && !jumping) jump();
            if (key == KeyEvent.VK_S) setYDirection(speed);
            if (key == KeyEvent.VK_A) setXDirection(-speed);
            if (key == KeyEvent.VK_D) setXDirection(speed);
        } else if (id == 2) {
            // Player 2: Arrow
            if (key == KeyEvent.VK_UP && !jumping) jump();
            if (key == KeyEvent.VK_DOWN) setYDirection(speed);
            if (key == KeyEvent.VK_LEFT) setXDirection(-speed);
            if (key == KeyEvent.VK_RIGHT) setXDirection(speed);
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (id == 1) {
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
                setYDirection(0);
            }
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
                setXDirection(0);
            }
        } else if (id == 2) {
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                setYDirection(0);
            }
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                setXDirection(0);
            }
        }
    }

    public void jump() {
        yVelocity = -jumpStrength;
        jumping = true;
    }

    /**
     * Dipanggil host untuk menerapkan input dari client (P2).
     */
    public void applyNetworkInput(PlayerInput input) {
        if (input == null) return;

        // Horizontal
        if (input.left && !input.right) {
            setXDirection(-speed);
        } else if (input.right && !input.left) {
            setXDirection(speed);
        } else {
            setXDirection(0);
        }

        // Vertical (opsional, kalau mau pakai)
        if (input.down && !input.up) {
            setYDirection(speed);
        } else if (!input.down) {
            // Kalau tidak menekan down, kita biarkan gravitasi / jump yang atur
            setYDirection(0);
        }

        // Jump (sekali pada saat tombol ditekan)
        if (input.up && !jumping) {
            jump();
        } else if (input.jump && !jumping) {
            // kalau mau map SPACE ke jump juga
            jump();
        }
    }
}
