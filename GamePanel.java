import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    Image backgroundImage;
    Gawang gawang1, gawang2;

    GamePanel() {
        backgroundImage = new ImageIcon("aset/lap.png").getImage();
        this.setPreferredSize(SCREEN_SIZE);

        newGawang();
    }

    public void newGawang() {
        gawang1 = new Gawang(0, GAME_HEIGHT - 150, 10, 150);

        gawang2 = new Gawang(GAME_WIDTH - 10, GAME_HEIGHT - 150, 10, 150);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, this);

        gawang1.draw(g);
        gawang2.draw(g);
    }
}
