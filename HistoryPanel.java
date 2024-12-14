import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HistoryPanel extends JPanel {
    private JButton backButton;
    private JLabel titleLabel;
    private JTextArea historyTextArea;
    private JScrollPane scrollPane;

    public HistoryPanel() {
        setLayout(null);
        this.setPreferredSize(new Dimension(1000, 415));

        titleLabel = new JLabel("Riwayat Permainan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 45));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(270, 30, 500, 50);
        add(titleLabel);

        historyTextArea = new JTextArea();
        historyTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        historyTextArea.setEditable(false);
        historyTextArea.setText(getHistory());
        historyTextArea.setLineWrap(true);
        historyTextArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(historyTextArea);
        scrollPane.setBounds(50, 100, 900, 250);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#4A6FA2"), 3));
        scrollPane.setBackground(Color.decode("#F4F4F9"));
        add(scrollPane);

        backButton = new JButton("Kembali");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setBounds(10, 10, 120, 40);
        backButton.setBackground(Color.decode("#4A6FA2"));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.decode("#638AC7"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.decode("#4A6FA2"));
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToStartMenu();
            }
        });

        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#1E2A47"), 0, getHeight(), Color.decode("#2D3B6C"));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private String getHistory() {
        StringBuilder history = new StringBuilder();
    
        try (Connection connection = ConnectionDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM history ORDER BY id_histori DESC LIMIT 10")) {
    
            while (resultSet.next()) {
                String player1 = resultSet.getString("namaplayer1");
                String player2 = resultSet.getString("namaplayer2");
                int score1 = resultSet.getInt("skorplayer1");
                int score2 = resultSet.getInt("skorplayer2");
    
                history.append(player1).append(" vs ").append(player2).append(": Skor ")
                        .append(score1).append("-").append(score2).append("\n");
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            history.append("Gagal mengambil data dari database.\n");
        }
    
        return history.toString();
    }
    

    private void goBackToStartMenu() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.remove(this);

        StartMenu startMenu = new StartMenu();
        frame.add(startMenu);
        frame.revalidate();
        frame.repaint();

        SwingUtilities.invokeLater(startMenu::requestFocusInWindow);
    }
}
