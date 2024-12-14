import java.awt.*;

public class Gawang extends Rectangle implements Movable {
    public Gawang(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void move() {
        
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, 10);
        g.fillRect(x, y + 10, 10, height - 10);
        g.fillRect(x + width - 10, y + 10, 10, height - 10);
    }
}
