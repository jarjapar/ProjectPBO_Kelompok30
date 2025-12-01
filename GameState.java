public class GameState {
    public int ballX;
    public int ballY;
    public int p1X;
    public int p1Y;
    public int p2X;
    public int p2Y;
    public int score1;
    public int score2;
    public int remainingTime;
    public boolean extraTime;

    public GameState() {
    }

    public String toMessage() {
        // FORMAT:
        // STATE;ballX;ballY;p1X;p1Y;p2X;p2Y;score1;score2;remainingTime;extraTime
        return "STATE;" +
                ballX + ";" +
                ballY + ";" +
                p1X + ";" +
                p1Y + ";" +
                p2X + ";" +
                p2Y + ";" +
                score1 + ";" +
                score2 + ";" +
                remainingTime + ";" +
                (extraTime ? "1" : "0");
    }

    public static GameState fromMessage(String message) {
        String[] parts = message.split(";");
        if (parts.length < 11 || !"STATE".equals(parts[0])) {
            return null;
        }
        GameState state = new GameState();
        try {
            state.ballX         = Integer.parseInt(parts[1]);
            state.ballY         = Integer.parseInt(parts[2]);
            state.p1X           = Integer.parseInt(parts[3]);
            state.p1Y           = Integer.parseInt(parts[4]);
            state.p2X           = Integer.parseInt(parts[5]);
            state.p2Y           = Integer.parseInt(parts[6]);
            state.score1        = Integer.parseInt(parts[7]);
            state.score2        = Integer.parseInt(parts[8]);
            state.remainingTime = Integer.parseInt(parts[9]);
            state.extraTime     = "1".equals(parts[10]);
        } catch (NumberFormatException e) {
            return null;
        }
        return state;
    }
}
