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

    // Tambahan baru:
    public boolean gameOver; // true kalau pertandingan sudah selesai
    public int winner;       // 0 = draw, 1 = player1, 2 = player2

    public GameState() {
    }

    public String toMessage() {
        // FORMAT BARU:
        // STATE;ballX;ballY;p1X;p1Y;p2X;p2Y;score1;score2;remainingTime;extraTime;gameOver;winner
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
                (extraTime ? "1" : "0") + ";" +
                (gameOver ? "1" : "0") + ";" +
                winner;
    }

    public static GameState fromMessage(String message) {
        String[] parts = message.split(";");
        // minimal 13 bagian
        if (parts.length < 13 || !"STATE".equals(parts[0])) {
            return null;
        }
        GameState state = new GameState();
        try {
            state.ballX        = Integer.parseInt(parts[1]);
            state.ballY        = Integer.parseInt(parts[2]);
            state.p1X          = Integer.parseInt(parts[3]);
            state.p1Y          = Integer.parseInt(parts[4]);
            state.p2X          = Integer.parseInt(parts[5]);
            state.p2Y          = Integer.parseInt(parts[6]);
            state.score1       = Integer.parseInt(parts[7]);
            state.score2       = Integer.parseInt(parts[8]);
            state.remainingTime = Integer.parseInt(parts[9]);
            state.extraTime    = "1".equals(parts[10]);
            state.gameOver     = "1".equals(parts[11]);
            state.winner       = Integer.parseInt(parts[12]);
        } catch (NumberFormatException e) {
            return null;
        }
        return state;
    }
}
