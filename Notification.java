import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Notification extends JPanel {
    private JButton backButton;

    public Notification(String message) {
        this.setPreferredSize(new Dimension(1000, 415));
        this.setLayout(new GridBagLayout());

        setBackground(new Color(0, 0, 0, 180));

        JLabel messageLabel = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        backButton = new JButton("Kembali ke Start");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(new Color(34, 193, 195));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(200, 50));

        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                backButton.setBackground(new Color(253, 187, 45));
            }

            public void mouseExited(MouseEvent evt) {
                backButton.setBackground(new Color(34, 193, 195));
            }
        });

        backButton.addActionListener(_ -> goBackToStartMenu());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(messageLabel, gbc);

        gbc.gridy = 1;
        this.add(backButton, gbc);
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
