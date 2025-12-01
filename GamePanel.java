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
    private int remainingTime = 15;
    private boolean isExtraTime = false;
    private boolean isDrawNotificationShown = false;
    private boolean clientGameOverShown = false;
    private boolean clientResultShown = false;
    private String player1Name;
    private String player2Name;

    public static enum GameMode {
        LOCAL,
        HOST,
        CLIENT
    }

    private final GameMode gameMode;
    private NetworkHost networkHost;
    private NetworkClient networkClient;
    private PlayerInput clientInput = new PlayerInput();

    public GamePanel(String player1Name, String player2Name) {
        this(player1Name, player2Name, GameMode.LOCAL, null, null);
    }

    public GamePanel(String player1Name, String player2Name, GameMode mode,
                     NetworkHost host, NetworkClient client) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.gameMode = mode;
        this.networkHost = host;
        this.networkClient = client;

        backgroundImage = new ImageIcon("aset/lap.png").getImage();
        this.setPreferredSize(SCREEN_SIZE);
        this.setFocusable(true);
        this.addKeyListener(createKeyListener());

        initializeGame();
        startGameThread();

        // Timer hanya berjalan di LOCAL dan HOST
        if (gameMode != GameMode.CLIENT) {
            startGameTimer();
        }
    }

    private void initializeGame() {
        player1 = new PlayerSet(0,
                PLAYER_START_Y - PLAYER_HEIGHT / 2,
                PLAYER_WIDTH, PLAYER_HEIGHT, 1);
        player2 = new PlayerSet(GAME_WIDTH - PLAYER_WIDTH,
                PLAYER_START_Y - PLAYER_HEIGHT / 2,
                PLAYER_WIDTH, PLAYER_HEIGHT, 2);
        gawang1 = new Gawang(0, GAME_HEIGHT - 125, 40, 125);
        gawang2 = new Gawang(GAME_WIDTH - 40, GAME_HEIGHT - 125, 40, 125);
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2,
                GAME_HEIGHT / 2 - BALL_DIAMETER / 2,
                BALL_DIAMETER, BALL_DIAMETER);
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
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

    private void startGameThread() {
        new Thread(this::runGameLoop, "GameLoopThread").start();
    }

    private void runGameLoop() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60.0;
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            if (delta >= 1) {
                if (gameMode == GameMode.CLIENT) {
                    // CLIENT: hanya ambil state dari network dan gambar
                    updateFromNetworkState();
                } else {
                    // HOST / LOCAL: logic game full
                    if (gameMode == GameMode.HOST && networkHost != null) {
                        PlayerInput remoteInput = networkHost.getLatestInput();
                        player2.applyNetworkInput(remoteInput);
                    }

                    move();
                    checkCollisions();

                    if (gameMode == GameMode.HOST && networkHost != null) {
                        GameState state = createCurrentState();
                        networkHost.sendState(state);
                    }
                }

                repaint();
                delta--;
            }
        }
    }

    private void updateFromNetworkState() {
    if (networkClient == null) return;
    GameState state = networkClient.getLatestState();
    if (state == null) return;

    // Terapkan state ke objek lokal
    ball.x = state.ballX;
    ball.y = state.ballY;

    player1.x = state.p1X;
    player1.y = state.p1Y;

    player2.x = state.p2X;
    player2.y = state.p2Y;

    score.player1 = state.score1;
    score.player2 = state.score2;

    remainingTime = state.remainingTime;
    isExtraTime = state.extraTime;

    if (gameMode == GameMode.CLIENT && networkClient != null &&
        networkClient.isGameOver() && !clientResultShown) {

    clientResultShown = true;

    int winnerCode = networkClient.getFinalWinnerCode();
    int s1 = networkClient.getFinalScore1();
    int s2 = networkClient.getFinalScore2();

    showClientResult(winnerCode, s1, s2);
}

}

    private GameState createCurrentState() {
    GameState state = new GameState();
    state.ballX = ball.x;
    state.ballY = ball.y;
    state.p1X = player1.x;
    state.p1Y = player1.y;
    state.p2X = player2.x;
    state.p2Y = player2.y;
    state.score1 = score.player1;
    state.score2 = score.player2;
    state.remainingTime = remainingTime;
    state.extraTime = isExtraTime;
    return state;
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
        if (gameTimer != null) {
            gameTimer.cancel();
        }
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
            if (frame == null) return;

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
        int winnerCode; // 0 draw, 1 p1, 2 p2

        if (score.player1 > score.player2) {
            message = "Player 1 Wins!\nSkor: " + score.player1 + " - " + score.player2;
            winnerCode = 1;
        } else if (score.player2 > score.player1) {
            message = "Player 2 Wins!\nSkor: " + score.player1 + " - " + score.player2;
            winnerCode = 2;
        } else {
            message = "Draw!\nSkor: " + score.player1 + " - " + score.player2;
            winnerCode = 0;
        }

        // 🔴 Kirim pesan GAMEOVER ke client jika ini HOST
        if (gameMode == GameMode.HOST && networkHost != null) {
            networkHost.sendGameOver(winnerCode, score.player1, score.player2);
        }

        // Update DB hanya di LOCAL / HOST
        if (gameMode != GameMode.CLIENT) {
            PlayerDB.updatePlayerScore(player1Name, score.player1);
            PlayerDB.updatePlayerScore(player2Name, score.player2);
            HistoryDB.insertHistory(player1Name, player2Name, score.player1, score.player2);
        }

        if (networkHost != null) {
            networkHost.stop();    // tutup ServerSocket, client socket, dll
        }
        if (networkClient != null) {
            networkClient.stop();  // tutup koneksi dari sisi client
        }
        
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
        int key = e.getKeyCode();

        switch (gameMode) {
            case LOCAL:
                if (isPressed) {
                    player1.keyPressed(e);
                    player2.keyPressed(e);
                } else {
                    player1.keyReleased(e);
                    player2.keyReleased(e);
                }
                break;

            case HOST:
                // Host hanya pakai WASD + SPACE untuk player 1,
                // arrow key diserahkan ke client (via network)
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_A ||
                    key == KeyEvent.VK_S || key == KeyEvent.VK_D ||
                    key == KeyEvent.VK_SPACE) {
                    if (isPressed) {
                        player1.keyPressed(e);
                    } else {
                        player1.keyReleased(e);
                    }
                }
                break;

            case CLIENT:
                // Client tidak langsung menggerakkan PlayerSet.
                // Hanya update state input dan kirim ke host.
                if (isPressed) {
                    updateClientInputOnPress(key);
                } else {
                    updateClientInputOnRelease(key);
                }
                if (networkClient != null) {
                    networkClient.sendInput(clientInput);
                }
                break;
        }
    }

    private void updateClientInputOnPress(int key) {
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            clientInput.up = true;
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            clientInput.down = true;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            clientInput.left = true;
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            clientInput.right = true;
        } else if (key == KeyEvent.VK_SPACE) {
            clientInput.jump = true;
        }
    }

    private void updateClientInputOnRelease(int key) {
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            clientInput.up = false;
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            clientInput.down = false;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            clientInput.left = false;
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            clientInput.right = false;
        } else if (key == KeyEvent.VK_SPACE) {
            clientInput.jump = false;
        }
    }

    private void move() {
        player1.move();
        player2.move();
        ball.move();
    }

    private void checkCollisions() {
        // Pantulan atas/bawah
        if (ball.y < 0 || ball.y > GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }

        // Lewat kiri/kanan (gawang atau pantulan)
        if (ball.x < 0 || ball.x > GAME_WIDTH - BALL_DIAMETER) {
            handleGoalOrBounce();
        }

        // Tabrak pemain
        if (ball.intersects(player1)) {
            ball.setXDirection(ball.xVelocity * -1);
            if (ball.x >= player1.x) {
                ball.x = player1.x + BALL_DIAMETER;
            } else {
                ball.x = player1.x - BALL_DIAMETER;
            }
        } else if (ball.intersects(player2)) {
            ball.setXDirection(ball.xVelocity * -1);
            if (ball.x >= player2.x) {
                ball.x = player2.x + BALL_DIAMETER;
            } else {
                ball.x = player2.x - BALL_DIAMETER;
            }
        }

        // Tabrak antar pemain
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
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2,
                GAME_HEIGHT / 2 - BALL_DIAMETER / 2,
                BALL_DIAMETER, BALL_DIAMETER);
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

    private void showClientResult(int winnerCode, int score1, int score2) {
    String message;
    if (winnerCode == 1) {
        message = "Player 1 Wins!\nSkor: " + score1 + " - " + score2;
    } else if (winnerCode == 2) {
        message = "Player 2 Wins!\nSkor: " + score1 + " - " + score2;
    } else {
        message = "Draw!\nSkor: " + score1 + " - " + score2;
    }

    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
    if (frame != null) {
        frame.remove(this);
        frame.add(new Notification(message));
        frame.revalidate();
        frame.repaint();
    }
}


}
