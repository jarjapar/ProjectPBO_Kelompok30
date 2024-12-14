import java.sql.*;
import java.util.ArrayList;

public class PlayerDB {
    public static void updatePlayerScore(String playerName, int score) {
        String sql = "UPDATE players SET score = ? WHERE name = ?";
        
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, score);
            pstmt.setString(2, playerName);
        
            int rowsUpdated = pstmt.executeUpdate();
        
            if (rowsUpdated > 0) {
                System.out.println("Player score updated successfully!");
            } else {
                System.out.println("Player not found, score update failed.");
            }
        
        } catch (SQLException e) {
            System.err.println("Error updating player score: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    

    public static void insertPlayer(String name, int score) {
        String sql = "INSERT INTO players (name, score) VALUES (?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();

            System.out.println("Player inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PlayerModel> getPlayers() {
        ArrayList<PlayerModel> players = new ArrayList<>();
        String sql = "SELECT * FROM players";
        try (Connection conn = ConnectionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                players.add(new PlayerModel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("score")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }
}