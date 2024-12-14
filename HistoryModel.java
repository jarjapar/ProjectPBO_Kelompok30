public class HistoryModel {
    private int idHistori;
    private String namaPlayer1;
    private String namaPlayer2;
    private int skorPlayer1;
    private int skorPlayer2;

    public HistoryModel(int idHistori, int idPlayer1, int idPlayer2, String namaPlayer1, String namaPlayer2, int skorPlayer1, int skorPlayer2) {
        this.idHistori = idHistori;
        this.namaPlayer1 = namaPlayer1;
        this.namaPlayer2 = namaPlayer2;
        this.skorPlayer1 = skorPlayer1;
        this.skorPlayer2 = skorPlayer2;
    }

    public int getIdHistori() {
        return idHistori;
    }

    public void setIdHistori(int idHistori) {
        this.idHistori = idHistori;
    }

    public String getNamaPlayer1() {
        return namaPlayer1;
    }

    public void setNamaPlayer1(String namaPlayer1) {
        this.namaPlayer1 = namaPlayer1;
    }

    public String getNamaPlayer2() {
        return namaPlayer2;
    }

    public void setNamaPlayer2(String namaPlayer2) {
        this.namaPlayer2 = namaPlayer2;
    }

    public int getSkorPlayer1() {
        return skorPlayer1;
    }

    public void setSkorPlayer1(int skorPlayer1) {
        this.skorPlayer1 = skorPlayer1;
    }

    public int getSkorPlayer2() {
        return skorPlayer2;
    }

    public void setSkorPlayer2(int skorPlayer2) {
        this.skorPlayer2 = skorPlayer2;
    }
}
