package dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import model.Lasku;
import util.Tietokantayhteys;

public class LaskuDAO {
    public List<Lasku> haeKaikkiLaskut() {
        List<Lasku> laskut = new ArrayList<>();
        String sql = "SELECT * FROM laskut";

        try (Connection conn = Tietokantayhteys.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lasku lasku = new Lasku(
                    rs.getInt("id"),
                    rs.getInt("varaus_id"),
                    rs.getDouble("määrä"),
                    rs.getDate("eräpäivä").toLocalDate(),
                    rs.getTimestamp("luontiaika").toLocalDateTime());
                
                laskut.add(lasku);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return laskut;
    }

    // Lisää uuden laskun tietokantaan
    public void lisaaLasku(Lasku lasku) {
        String sql = "INSERT INTO laskut (varaus_id, määrä, eräpäivä, luontiaika) VALUES (?, ?, ?, ?)";

        try (Connection conn = Tietokantayhteys.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, lasku.getVarausId());
            pstmt.setDouble(2, lasku.getSumma());
            pstmt.setDate(3, Date.valueOf(lasku.getErapaiva()));
            pstmt.setTimestamp(4, Timestamp.valueOf(lasku.getLuontiaika()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Poistaa laskun tietokannasta id:n perusteella
    public void poistaLasku(int laskuId) {
        String sql = "DELETE FROM laskut WHERE id = ?";

        try (Connection conn = Tietokantayhteys.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, laskuId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
