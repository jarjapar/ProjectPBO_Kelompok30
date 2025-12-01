public class PlayerInput {
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean jump;

    public PlayerInput() {
    }

    public PlayerInput(boolean up, boolean down, boolean left, boolean right, boolean jump) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.jump = jump;
    }

    public static PlayerInput fromMessage(String message) {
        // FORMAT: INPUT;up;down;left;right;jump  (0/1)
        String[] parts = message.split(";");
        if (parts.length < 6 || !"INPUT".equals(parts[0])) {
            return null;
        }
        PlayerInput input = new PlayerInput();
        input.up = "1".equals(parts[1]);
        input.down = "1".equals(parts[2]);
        input.left = "1".equals(parts[3]);
        input.right = "1".equals(parts[4]);
        input.jump = "1".equals(parts[5]);
        return input;
    }

    public String toMessage() {
        // OUTPUT: INPUT;up;down;left;right;jump
        return "INPUT;" +
                (up ? "1" : "0") + ";" +
                (down ? "1" : "0") + ";" +
                (left ? "1" : "0") + ";" +
                (right ? "1" : "0") + ";" +
                (jump ? "1" : "0");
    }

    public PlayerInput copy() {
        return new PlayerInput(up, down, left, right, jump);
    }
}
