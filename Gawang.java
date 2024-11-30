import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Gawang extends Rectangle {
    int width, height;

    public Gawang(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white); 
        g.fillRect(x, y, width, height);
    }
}
