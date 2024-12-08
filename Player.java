import java.awt.*;
import java.awt.event.*;

public class Player extends Rectangle {
    int id, xVelocity, yVelocity, speed = 10, jumpStrength = 20, headDiameter = 50;
    boolean jumping = false;

    Player(int x, int y, int width, int height, int id) {
        super(x, y, width, height);
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (id == 1) {
            if (key == KeyEvent.VK_W && !jumping) jump();
            if (key == KeyEvent.VK_S) setYDirection(speed);
            if (key == KeyEvent.VK_A) setXDirection(-speed);
            if (key == KeyEvent.VK_D) setXDirection(speed);
        } else if (id == 2) {
            if (key == KeyEvent.VK_UP && !jumping) jump();
            if (key == KeyEvent.VK_DOWN) setYDirection(speed);
            if (key == KeyEvent.VK_LEFT) setXDirection(-speed);
            if (key == KeyEvent.VK_RIGHT) setXDirection(speed);
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (id == 1 || id == 2) {
            if (key == (id == 1 ? KeyEvent.VK_W : KeyEvent.VK_UP) || key == (id == 1 ? KeyEvent.VK_S : KeyEvent.VK_DOWN)) setYDirection(0);
            if (key == (id == 1 ? KeyEvent.VK_A : KeyEvent.VK_LEFT) || key == (id == 1 ? KeyEvent.VK_D : KeyEvent.VK_RIGHT)) setXDirection(0);
        }
    }

    public void jump() {
        yVelocity = -jumpStrength;
        jumping = true;
    }

    public void setYDirection(int yDirection) { yVelocity = yDirection; }
    public void setXDirection(int xDirection) { xVelocity = xDirection; }

    public void move() {
        y += yVelocity; x += xVelocity;
        if (y <= 0) yVelocity = 0;
        if (y >= GamePanel.GAME_HEIGHT - height) { jumping = false; y = GamePanel.GAME_HEIGHT - height; }
        if (x <= 0) x = 0;
        if (x >= GamePanel.GAME_WIDTH - width) x = GamePanel.GAME_WIDTH - width;
        if (jumping) yVelocity += 1;
    }

    public void draw(Graphics g) {
        g.setColor(id == 1 ? Color.blue : Color.red);
        g.fillOval(x + (width/2) - (headDiameter/2), y - 3 ,  headDiameter, headDiameter);
        g.fillRect(x, y, width, height);
    }
}