import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 400;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 70;
    static final int PLAYER_START_Y = (int) (GAME_HEIGHT * 1);

    Image backgroundImage;
    Gawang gawang1, gawang2;
    Player player1, player2;
    Ball ball; 
    Timer timer;
    Random random = new Random();

    GamePanel() {
        backgroundImage = new ImageIcon("aset/lap.png").getImage();
        this.setPreferredSize(SCREEN_SIZE);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                player1.keyPressed(e);
                player2.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player1.keyReleased(e);
                player2.keyReleased(e);
            }
        });
        newPlayers();
        newGawang();
        newBall();
        timer = new Timer(10, this);
        timer.start();
    }

    public void newGawang() {
        gawang1 = new Gawang(0, GAME_HEIGHT - 125, 40, 125);
        gawang2 = new Gawang(GAME_WIDTH - 40, GAME_HEIGHT - 125, 40, 125);
    }

    public void newPlayers() {
        player1 = new Player(0, PLAYER_START_Y - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        player2 = new Player(GAME_WIDTH - PADDLE_WIDTH, PLAYER_START_Y - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void newBall() {
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2, GAME_HEIGHT / 2 - BALL_DIAMETER / 2, BALL_DIAMETER, BALL_DIAMETER);
    }

    public void move() {
        player1.move();
        player2.move();
        ball.move();
    }

    public void checkCollisions() {
        if (ball.y <= 0 || ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.x <= 0 || ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            ball.setXDirection(-ball.xVelocity);
        }
        if (ball.intersects(player1)) {
            ball.setXDirection(Math.abs(ball.xVelocity));
        }
        if (ball.intersects(player2)) {
            ball.setXDirection(-Math.abs(ball.xVelocity));
        }
        if (ball.intersects(gawang1) || ball.intersects(gawang2)) {
            newBall();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, this);
        player1.draw(g);
        player2.draw(g);
        ball.draw(g);
        gawang1.draw(g);
        gawang2.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        checkCollisions();
        repaint();
    }
}
