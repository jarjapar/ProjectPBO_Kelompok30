import java.awt.*;
public abstract class GameObject extends Rectangle implements Movable {
    int xVelocity, yVelocity;
    int speed = 10;

    public GameObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void draw(Graphics g);

    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }
}