import javax.swing.*;

public class GameFrame extends JFrame {
    GamePanel panel;
    StartMenu startMenu;

    GameFrame() {
        startMenu = new StartMenu();
        this.add(startMenu);

        this.setTitle("Soccer");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
