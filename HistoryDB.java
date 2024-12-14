import java.sql.*;

public class HistoryDB {
    public static void insertHistory(String namePlayer1, String namePlayer2, int scorePlayer1, int scorePlayer2) {
        String sql = "INSERT INTO history (namaplayer1, namaplayer2, skorplayer1, skorplayer2) VALUES (?, ?, ?, ?);";
        
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, namePlayer1);
            pstmt.setString(2, namePlayer2);
            pstmt.setInt(3, scorePlayer1);
            pstmt.setInt(4, scorePlayer2);
    
            pstmt.executeUpdate();
    
            System.out.println("History inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
