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

    public void handleBallCollision(Ball ball) {
        Rectangle body = new Rectangle(x, y, width, height);
        Rectangle head = new Rectangle(x + (width / 2) - (headDiameter / 2), y - headDiameter, headDiameter, headDiameter);
        resolveCollisionWithPlayer(ball, body, new Rectangle(ball.x + ball.xVelocity, ball.y, ball.width, ball.height), new Rectangle(ball.x, ball.y + ball.yVelocity, ball.width, ball.height));
        resolveCollisionWithPlayer(ball, head, new Rectangle(ball.x + ball.xVelocity, ball.y, ball.width, ball.height), new Rectangle(ball.x, ball.y + ball.yVelocity, ball.width, ball.height));
    }

    private void resolveCollisionWithPlayer(Ball ball, Rectangle playerPart, Rectangle ballBoundsNextX, Rectangle ballBoundsNextY) {
        if (ballBoundsNextX.intersects(playerPart)) {
            if (ball.xVelocity > 0) { ball.setXDirection(-Math.abs(ball.xVelocity)); ball.x = playerPart.x - ball.width; }
            else if (ball.xVelocity < 0) { ball.setXDirection(Math.abs(ball.xVelocity)); ball.x = playerPart.x + playerPart.width; }
        }
        if (ballBoundsNextY.intersects(playerPart)) {
            if (ball.yVelocity > 0) { ball.setYDirection(-Math.abs(ball.yVelocity)); ball.y = playerPart.y - ball.height; }
            else if (ball.yVelocity < 0) { ball.setYDirection(Math.abs(ball.yVelocity)); ball.y = playerPart.y + playerPart.height; }
        }
    }

    public void draw(Graphics g) {
        g.setColor(id == 1 ? Color.blue : Color.red);
        g.fillOval(x + (width / 2) - (headDiameter / 2), y - headDiameter, headDiameter, headDiameter);
        g.fillRect(x, y, width, height);
    }
}