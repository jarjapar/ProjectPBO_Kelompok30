import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 415;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 35;
    static final int PLAYER_WIDTH = 25;
    static final int PLAYER_HEIGHT = 100;
    static final int PLAYER_START_Y = GAME_HEIGHT;

    private Image backgroundImage;
    private Gawang gawang1, gawang2;
    private PlayerSet player1, player2;
    private Ball ball;
    private Score score;
    private Timer gameTimer;
    private int remainingTime = 120;
    private boolean isExtraTime = false;
    private boolean isDrawNotificationShown = false;
    private String player1Name;
    private String player2Name;

    public GamePanel(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        backgroundImage = new ImageIcon("aset/lap.png").getImage();
        this.setPreferredSize(SCREEN_SIZE);
        this.setFocusable(true);
        this.addKeyListener(createKeyListener());
        player1 = new PlayerSet(0, PLAYER_START_Y - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT, 1);
        player2 = new PlayerSet(GAME_WIDTH - PLAYER_WIDTH, PLAYER_START_Y - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT, 2);
        initializeGame();
        startGameThread();
        startGameTimer();
    }

    private KeyListener createKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(e, true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyEvent(e, false);
            }
        };
    }

    private void initializeGame() {
        player1 = new PlayerSet(0, PLAYER_START_Y - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT, 1);
        player2 = new PlayerSet(GAME_WIDTH - PLAYER_WIDTH, PLAYER_START_Y - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT, 2);
        gawang1 = new Gawang(0, GAME_HEIGHT - 125, 40, 125);
        gawang2 = new Gawang(GAME_WIDTH - 40, GAME_HEIGHT - 125, 40, 125);
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2, GAME_HEIGHT / 2 - BALL_DIAMETER / 2, BALL_DIAMETER, BALL_DIAMETER);
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
    }

    private void startGameThread() {
        new Thread(this::runGameLoop).start();
    }

    private void runGameLoop() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollisions();
                repaint();
                delta--;
            }
        }
    }

    private void startGameTimer() {
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    remainingTime--;
                    repaint();
                } else {
                    handleGameEnd();
                }
            }
        }, 1000, 1000);
    }

    private void handleGameEnd() {
        gameTimer.cancel();
        if (score.player1 == score.player2 && !isExtraTime) {
            isExtraTime = true;
            showDrawNotification();
            remainingTime = 60;
            startGameTimer();
        } else {
            declareWinner();
        }
    }

    private void showDrawNotification() {
        if (!isDrawNotificationShown) {
            isDrawNotificationShown = true;
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            JLayeredPane layeredPane = frame.getLayeredPane();
            Draw notificationPanel = new Draw();
            notificationPanel.setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT);
            layeredPane.add(notificationPanel, JLayeredPane.POPUP_LAYER);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    layeredPane.remove(notificationPanel);
                    layeredPane.repaint();
                }
            }, 1500);
        }
    }

    private void declareWinner() {
        String message;
        if (score.player1 > score.player2) {
            message = "Player 1 Wins!\nSkor: " + score.player1 + " - " + score.player2;
        } else if (score.player2 > score.player1) {
            message = "Player 2 Wins!\nSkor: " + score.player1 + " - " + score.player2;
        } else {
            message = "Draw!\nSkor: " + score.player1 + " - " + score.player2;
        }
    
        PlayerDB.updatePlayerScore(player1Name, score.player1);
        PlayerDB.updatePlayerScore(player2Name, score.player2);
    
        HistoryDB.insertHistory(player1Name, player2Name, score.player1, score.player2);
    
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.remove(this);
            frame.add(new Notification(message));
            frame.revalidate();
            frame.repaint();
        } else {
            System.err.println("Error: JFrame is null.");
        }
    }
    

    private void handleKeyEvent(KeyEvent e, boolean isPressed) {
        if (isPressed) {
            player1.keyPressed(e);
            player2.keyPressed(e);
        } else {
            player1.keyReleased(e);
            player2.keyReleased(e);
        }
    }

    private void move() {
        player1.move();
        player2.move();
        ball.move();
    }

    private void checkCollisions() {
        if (ball.y < 0 || ball.y > GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.x < 0 || ball.x > GAME_WIDTH - BALL_DIAMETER) {
            handleGoalOrBounce();
        }
        if (ball.intersects(player1)) {
            ball.setXDirection(ball.xVelocity * -1);
            if(ball.x >= player1.x){
                ball.x = player1.x + BALL_DIAMETER;
            } else{
                ball.x = player1.x - BALL_DIAMETER;
            }
        } 
        else if (ball.intersects(player2)) {
            ball.setXDirection(ball.xVelocity * -1);
            if(ball.x >= player2.x){
                ball.x = player2.x + BALL_DIAMETER;
            } else{
                ball.x = player2.x - BALL_DIAMETER;
            }
        }
        if (player1.intersects(player2)) {
            if (player1.x + player1.width > player2.x && player1.x < player2.x + player2.width) {
                if (player1.xVelocity > 0) {
                    player1.x = player2.x - player1.width;
                } else if (player1.xVelocity < 0) {
                    player1.x = player2.x + player2.width;
                }
                if (player2.xVelocity > 0) {
                    player2.x = player1.x - player2.width;
                } else if (player2.xVelocity < 0) {
                    player2.x = player1.x + player1.width;
                }
            }
        }
    }

    private void handleGoalOrBounce() {
        if (ball.x <= 0) {
            if (ball.intersects(gawang1)) {
                score.player2++;
                if (isExtraTime) {
                    declareWinner();
                } else {
                    newBall();
                }
            } else {
                ball.setXDirection(-ball.xVelocity);
            }
        } else if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            if (ball.intersects(gawang2)) {
                score.player1++;
                if (isExtraTime) {
                    declareWinner();
                } else {
                    newBall();
                }
            } else {
                ball.setXDirection(-ball.xVelocity);
            }
        }
    }

    private void newBall() {
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2, GAME_HEIGHT / 2 - BALL_DIAMETER / 2, BALL_DIAMETER, BALL_DIAMETER);
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
        score.draw(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        g.drawString(String.format("%02d:%02d", minutes, seconds), GAME_WIDTH / 2 - 20, 30);
    }
}
