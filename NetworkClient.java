import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client:
 * - Connect ke host
 * - Mengirim INPUT (PlayerInput) ke host
 * - Menerima STATE (GameState) dari host
 */
public class NetworkClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private volatile GameState latestState = new GameState();
    private volatile boolean running = false;

    public void connect(String hostIp, int port) throws IOException {
        socket = new Socket(hostIp, port);
        System.out.println("Terhubung ke host " + hostIp + ":" + port);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        running = true;
        Thread listener = new Thread(this::listenLoop, "Client-State-Listener");
        listener.setDaemon(true);
        listener.start();
    }

    private void listenLoop() {
        try {
            String line;
            while (running && (line = in.readLine()) != null) {
                if (line.startsWith("STATE")) {
                    GameState state = GameState.fromMessage(line);
                    if (state != null) {
                        latestState = state;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Koneksi ke host terputus: " + e.getMessage());
        }
    }

    public void sendInput(PlayerInput input) {
        if (out != null && input != null) {
            out.println(input.toMessage());
        }
    }

    public GameState getLatestState() {
        return latestState;
    }

    public void stop() {
        running = false;
        try {
            if (in != null) in.close();
        } catch (IOException ignored) {
        }
        if (out != null) out.close();
        try {
            if (socket != null) socket.close();
        } catch (IOException ignored) {
        }
    }
}
