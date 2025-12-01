import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JPanel {
    private JButton startButton;
    private JButton historyButton;
    private JButton hostButton;
    private JButton joinButton;
    private JLabel titleLabel;
    private JLabel instructionLabel;
    private JLabel player1Label;
    private JLabel player2Label;
    private JTextField player1NameField;
    private JTextField player2NameField;
    private ImageIcon backgroundImage;

    public StartMenu() {
        setLayout(null);
        backgroundImage = new ImageIcon("aset/background.png");
        this.setPreferredSize(new Dimension(1000, 415));

        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, 1000, 600);
        this.add(background);

        titleLabel = new JLabel("Soccer by One");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(Color.white);
        titleLabel.setBounds(285, 50, 500, 100);
        background.add(titleLabel);

        instructionLabel = new JLabel("Player 1 (Biru) vs Player 2 (Merah)");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 25));
        instructionLabel.setForeground(Color.white);
        instructionLabel.setBounds(300, 130, 400, 50);
        background.add(instructionLabel);

        player1Label = new JLabel("Nama Player 1:");
        player1Label.setFont(new Font("Arial", Font.PLAIN, 20));
        player1Label.setForeground(Color.black);
        player1Label.setBounds(320, 180, 150, 30);
        background.add(player1Label);

        player1NameField = new JTextField();
        player1NameField.setFont(new Font("Arial", Font.PLAIN, 20));
        player1NameField.setBounds(470, 180, 200, 30);
        background.add(player1NameField);

        player2Label = new JLabel("Nama Player 2:");
        player2Label.setFont(new Font("Arial", Font.PLAIN, 20));
        player2Label.setForeground(Color.black);
        player2Label.setBounds(320, 220, 150, 30);
        background.add(player2Label);

        player2NameField = new JTextField();
        player2NameField.setFont(new Font("Arial", Font.PLAIN, 20));
        player2NameField.setBounds(470, 220, 200, 30);
        background.add(player2NameField);

        // Tombol Local
        startButton = new JButton("Mulai Local");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setBounds(340, 280, 150, 40);
        startButton.setBackground(Color.decode("#ADD8E6"));
        startButton.setForeground(Color.white);
        startButton.setFocusPainted(false);

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(Color.lightGray);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(Color.decode("#ADD8E6"));
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLocalGame();
            }
        });
        background.add(startButton);

        // Tombol Host Online
        hostButton = new JButton("Host Online");
        hostButton.setFont(new Font("Arial", Font.PLAIN, 20));
        hostButton.setBounds(500, 280, 150, 40);
        hostButton.setBackground(Color.decode("#ADD8E6"));
        hostButton.setForeground(Color.white);
        hostButton.setFocusPainted(false);

        hostButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hostButton.setBackground(Color.lightGray);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                hostButton.setBackground(Color.decode("#ADD8E6"));
            }
        });

        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startHostGame();
            }
        });
        background.add(hostButton);

        // Tombol Join Online
        joinButton = new JButton("Join Online");
        joinButton.setFont(new Font("Arial", Font.PLAIN, 20));
        joinButton.setBounds(660, 280, 150, 40);
        joinButton.setBackground(Color.decode("#ADD8E6"));
        joinButton.setForeground(Color.white);
        joinButton.setFocusPainted(false);

        joinButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                joinButton.setBackground(Color.lightGray);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                joinButton.setBackground(Color.decode("#ADD8E6"));
            }
        });

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startClientGame();
            }
        });
        background.add(joinButton);

        // Tombol History
        historyButton = new JButton("History");
        historyButton.setFont(new Font("Arial", Font.PLAIN, 20));
        historyButton.setBounds(10, 10, 100, 40);
        historyButton.setBackground(Color.decode("#ADD8E6"));
        historyButton.setForeground(Color.white);
        historyButton.setFocusPainted(false);

        historyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                historyButton.setBackground(Color.lightGray);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                historyButton.setBackground(Color.decode("#ADD8E6"));
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistoryPanel();
            }
        });
        background.add(historyButton);
    }

    private String[] getPlayerNamesOrShowError() {
        String player1Name = player1NameField.getText().trim();
        String player2Name = player2NameField.getText().trim();

        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama Player 1 dan Player 2 harus diisi!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new String[]{player1Name, player2Name};
    }

    private void startLocalGame() {
        String[] names = getPlayerNamesOrShowError();
        if (names == null) return;

        String player1Name = names[0];
        String player2Name = names[1];

        System.out.println("Local - Player 1: " + player1Name);
        System.out.println("Local - Player 2: " + player2Name);

        PlayerDB.insertPlayer(player1Name, 0);
        PlayerDB.insertPlayer(player2Name, 0);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.remove(this);

        GamePanel gamePanel = new GamePanel(player1Name, player2Name);
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();

        SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
    }
    

    private void startHostGame() {
        String[] names = getPlayerNamesOrShowError();
        if (names == null) return;

        String player1Name = names[0];
        String player2Name = names[1];

        PlayerDB.insertPlayer(player1Name, 0);
        PlayerDB.insertPlayer(player2Name, 0);

        NetworkHost host = new NetworkHost();

        // Jalankan listen di thread sendiri supaya UI tidak nge-freeze
        new Thread(() -> {
            try {
                host.start(5001); // port boleh kamu ganti
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    frame.remove(this);

                    GamePanel gamePanel = new GamePanel(
                            player1Name,
                            player2Name,
                            GamePanel.GameMode.HOST, // butuh akses, jadi ubah access modifier kalau perlu
                            host,
                            null
                    );
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this,
                                "Gagal membuat host: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE));
            }
        }, "Host-Accept-Thread").start();
    }

    private void startClientGame() {
        String[] names = getPlayerNamesOrShowError();
        if (names == null) return;

        String player1Name = names[0];
        String player2Name = names[1];

        String hostIp = JOptionPane.showInputDialog(this, "Masukkan IP Host:");
        if (hostIp == null || hostIp.trim().isEmpty()) return;

        NetworkClient client = new NetworkClient();

        new Thread(() -> {
            try {
                client.connect(hostIp.trim(), 5001);
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    frame.remove(this);

                    GamePanel gamePanel = new GamePanel(
                            player1Name,
                            player2Name,
                            GamePanel.GameMode.CLIENT,
                            null,
                            client
                    );
                    frame.add(gamePanel);
                    frame.revalidate();
                    frame.repaint();
                    SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this,
                                "Gagal konek ke host: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE));
            }
        }, "Client-Connect-Thread").start();
    }

    private void showHistoryPanel() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.remove(this);

        HistoryPanel historyPanel = new HistoryPanel();
        frame.add(historyPanel);
        frame.revalidate();
        frame.repaint();

        SwingUtilities.invokeLater(historyPanel::requestFocusInWindow);
    }
}
