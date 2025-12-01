import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Host / Server:
 * - Menerima koneksi 1 client
 * - Menerima INPUT dari client (PlayerInput)
 * - Mengirim STATE (GameState) ke client
 */
public class NetworkHost {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private volatile PlayerInput latestInput = new PlayerInput();
    private volatile boolean running = false;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Menunggu client di port " + port + "...");
        clientSocket = serverSocket.accept();
        System.out.println("Client terhubung: " + clientSocket.getInetAddress());

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        running = true;
        Thread listener = new Thread(this::listenLoop, "Host-Input-Listener");
        listener.setDaemon(true);
        listener.start();
    }

    private void listenLoop() {
        try {
            String line;
            while (running && (line = in.readLine()) != null) {
                if (line.startsWith("INPUT")) {
                    PlayerInput input = PlayerInput.fromMessage(line);
                    if (input != null) {
                        latestInput = input;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Koneksi ke client terputus: " + e.getMessage());
        }
    }

    public PlayerInput getLatestInput() {
        // return copy supaya aman dari race-condition ringan
        return latestInput.copy();
    }

    public void sendState(GameState state) {
        if (out != null && state != null) {
            out.println(state.toMessage());
        }
    }

    public void stop() {
        running = false;
        try {
            if (in != null) in.close();
        } catch (IOException ignored) {
        }
        if (out != null) out.close();
        try {
            if (clientSocket != null) clientSocket.close();
        } catch (IOException ignored) {
        }
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException ignored) {
        }
    }
}
