import javax.swing.*;
import java.awt.*;

public class StartMenu extends JPanel {
    private JButton startButton;
    private JButton historyButton;
    private JButton hostButton;
    private JButton joinButton;
    private JLabel titleLabel;
    private JLabel instructionLabel;
    private ImageIcon backgroundImage;

    // Port yang dipakai host & client
    private static final int ONLINE_PORT = 5000;

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

        // Tombol Local
        startButton = new JButton("Mulai Local");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setBounds(320, 220, 160, 40);
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
        startButton.addActionListener(e -> startLocalGame());
        background.add(startButton);

        // Tombol Host Online
        hostButton = new JButton("Host Online");
        hostButton.setFont(new Font("Arial", Font.PLAIN, 20));
        hostButton.setBounds(500, 220, 160, 40);
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
        hostButton.addActionListener(e -> startHostGame());
        background.add(hostButton);

        // Tombol Join Online
        joinButton = new JButton("Join Online");
        joinButton.setFont(new Font("Arial", Font.PLAIN, 20));
        joinButton.setBounds(680, 220, 160, 40);
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
        joinButton.addActionListener(e -> startClientGame());
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
        historyButton.addActionListener(e -> showHistoryPanel());
        background.add(historyButton);
    }

    // ---------- LOCAL MODE ----------
    private void startLocalGame() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        String p1Name = JOptionPane.showInputDialog(frame,
                "Masukkan nama Player 1 (Kiri / Biru):");
        if (p1Name == null || p1Name.trim().isEmpty()) {
            return;
        }

        String p2Name = JOptionPane.showInputDialog(frame,
                "Masukkan nama Player 2 (Kanan / Merah):");
        if (p2Name == null || p2Name.trim().isEmpty()) {
            return;
        }

        // Simpan ke DB (local)
        PlayerDB.insertPlayer(p1Name, 0);
        PlayerDB.insertPlayer(p2Name, 0);

        frame.remove(this);
        GamePanel gamePanel = new GamePanel(p1Name, p2Name); // default = LOCAL
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
    }

    // ---------- HOST MODE ----------
    private void startHostGame() {
        hostButton.setEnabled(false); // biar nggak diklik 2x

        NetworkHost host = new NetworkHost();

        new Thread(() -> {
            try {
                // 1. Buka server & tunggu client connect
                host.start(ONLINE_PORT); // blocking sampai client connect

                // 2. Setelah client connect, tunggu sampai client mengirim nama (NAME;...)
                String clientName = host.waitForClientName();
                if (clientName == null || clientName.trim().isEmpty()) {
                    clientName = "Player2";
                }

                String finalClientName = clientName;

                // 3. Minta nama host (Player 1) di EDT
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

                    String p1Name = JOptionPane.showInputDialog(frame,
                            "Anda adalah Player 1 (Host / Biru).\nMasukkan nama Anda:");
                    if (p1Name == null || p1Name.trim().isEmpty()) {
                        p1Name = "Player1";
                    }

                    // Host menyimpan kedua nama ke DB
                    PlayerDB.insertPlayer(p1Name, 0);
                    PlayerDB.insertPlayer(finalClientName, 0);

                    frame.remove(this);
                    GamePanel gamePanel = new GamePanel(
                            p1Name,
                            finalClientName,
                            GamePanel.GameMode.HOST,
                            host,
                            null
                    );
                    frame.add(gamePanel);
                    frame.revalidate();
                    frame.repaint();
                    SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    hostButton.setEnabled(true);
                    JOptionPane.showMessageDialog(this,
                            "Gagal membuat host: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }, "Host-Accept-Thread").start();
    }

    // ---------- CLIENT MODE ----------
    private void startClientGame() {
        joinButton.setEnabled(false);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        String hostIp = JOptionPane.showInputDialog(frame,
                "Masukkan IP Host:");
        if (hostIp == null || hostIp.trim().isEmpty()) {
            joinButton.setEnabled(true);
            return;
        }

        NetworkClient client = new NetworkClient();

        new Thread(() -> {
            try {
                // 1. Konek ke host
                client.connect(hostIp.trim(), ONLINE_PORT);

                // 2. Setelah connect, minta nama client (Player 2) di EDT
                SwingUtilities.invokeLater(() -> {
                    JFrame f = (JFrame) SwingUtilities.getWindowAncestor(this);

                    String p2Name = JOptionPane.showInputDialog(f,
                            "Anda adalah Player 2 (Client / Merah).\nMasukkan nama Anda:");
                    if (p2Name == null || p2Name.trim().isEmpty()) {
                        p2Name = "Player2";
                    }

                    // Kirim nama Player 2 ke host
                    client.sendPlayerName(p2Name);

                    // Di sisi client, nama host belum tahu (tidak terlalu penting di sini),
                    // tapi untuk konsistensi parameter, kita beri label "Host".
                    String p1Name = "Host";

                    f.remove(this);
                    GamePanel gamePanel = new GamePanel(
                            p1Name,
                            p2Name,
                            GamePanel.GameMode.CLIENT,
                            null,
                            client
                    );
                    f.add(gamePanel);
                    f.revalidate();
                    f.repaint();
                    SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    joinButton.setEnabled(true);
                    JOptionPane.showMessageDialog(this,
                            "Gagal konek ke host: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }, "Client-Connect-Thread").start();
    }

    // ---------- HISTORY ----------
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
