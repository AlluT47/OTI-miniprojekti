package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Mokki;
import util.Tietokantayhteys;

public class MokkiDAO {

    public List<Mokki> haeKaikkiMokit() {
        List<Mokki> mokit = new ArrayList<>();
        String sql = "SELECT * FROM mokki";

        try (Connection conn = Tietokantayhteys.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mokki mokki = new Mokki(
                    rs.getInt("id"),
                    rs.getString("nimi"),
                    rs.getString("osoite"),
                    rs.getString("kuvaus"),
                    rs.getDouble("hinta_per_yö"),
                    rs.getInt("kapasiteetti"),
                    rs.getBoolean("on_vapaana"));
                
                mokit.add(mokki);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mokit;
    }

    // Lisää uuden mökin tietokantaan
    public void lisaaMokki(Mokki mokki) {
        String sql = "INSERT INTO mokki (nimi, osoite, kuvaus, hinta_per_yö, kapasiteetti, on_vapaana) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Tietokantayhteys.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mokki.getNimi());
            pstmt.setString(2, mokki.getOsoite());
            pstmt.setString(3, mokki.getKuvaus());
            pstmt.setDouble(4, mokki.getHintaPerYö());
            pstmt.setInt(5, mokki.getKapasiteetti());
            pstmt.setBoolean(6, mokki.isOnVapaana());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Päivittää mökin tiedot tietokantaan
    public void paivitaMokki(Mokki mokki) {
        String sql = "UPDATE mokki SET nimi = ?, osoite = ?, kuvaus = ?, hinta_per_yö = ?, kapasiteetti = ?, on_vapaana = ? WHERE id = ?";

        try (Connection conn = Tietokantayhteys.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mokki.getNimi());
            pstmt.setString(2, mokki.getOsoite());
            pstmt.setString(3, mokki.getKuvaus());
            pstmt.setDouble(4, mokki.getHintaPerYö());
            pstmt.setInt(5, mokki.getKapasiteetti());
            pstmt.setBoolean(6, mokki.isOnVapaana());
            pstmt.setInt(7, mokki.getMokkiId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Poistaa mökin tietokannasta id:n perusteella
    public void poistaMokki(int mokkiId) {
        String sql = "DELETE FROM mokki WHERE id = ?";

        try (Connection conn = Tietokantayhteys.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mokkiId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
