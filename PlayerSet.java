    import java.awt.*;
    import java.awt.event.KeyEvent;

    public class PlayerSet extends GameObject {
        int id, jumpStrength = 20;
        boolean jumping = false;
        int headDiameter = 50;

        public PlayerSet(int x, int y, int width, int height, int id) {
            super(x, y, width, height);
            this.id = id;
        }

        @Override
        public void move() {
            x += xVelocity;
            y += yVelocity;

            if (y >= GamePanel.GAME_HEIGHT - height) {
                y = GamePanel.GAME_HEIGHT - height;
                jumping = false;
                yVelocity = 0;
            }

            if (jumping) {
                yVelocity += 1; 
            }

            if (x <= 0) x = 0;
            if (x >= GamePanel.GAME_WIDTH - width) x = GamePanel.GAME_WIDTH - width;
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(id == 1 ? Color.blue : Color.red);
            g.fillOval(x + (width / 2) - (headDiameter / 2), y - 3, headDiameter, headDiameter);
            g.fillRect(x, y, width, height);
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (id == 1) {
                if (key == KeyEvent.VK_W && !jumping) jump();
                if (key == KeyEvent.VK_S) setYDirection(speed);
                if (key == KeyEvent.VK_A) setXDirection(-speed);
                if (key == KeyEvent.VK_D) setXDirection(speed);
            } else if (id == 2) {
                if (key == KeyEvent.VK_UP && !jumping) jump();
                if (key == KeyEvent.VK_DOWN) setYDirection(speed);
                if (key == KeyEvent.VK_LEFT) setXDirection(-speed);
                if (key == KeyEvent.VK_RIGHT) setXDirection(speed);
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == (id == 1 ? KeyEvent.VK_W : KeyEvent.VK_UP) || key == (id == 1 ? KeyEvent.VK_S : KeyEvent.VK_DOWN)) setYDirection(0);
            if (key == (id == 1 ? KeyEvent.VK_A : KeyEvent.VK_LEFT) || key == (id == 1 ? KeyEvent.VK_D : KeyEvent.VK_RIGHT)) setXDirection(0);
        }

        public void jump() {
            yVelocity = -jumpStrength;
            jumping = true;
        }
    }
