package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Varaus;
import util.Tietokantayhteys;

public class VarausDAO {
    public List<Varaus> haeKaikkiVaraukset() {
        List<Varaus> varaukset = new ArrayList<>();
        String sql = "SELECT * FROM varaa";

        try (Connection conn = Tietokantayhteys.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Varaus varaus = new Varaus(
                    rs.getInt("id"),
                    rs.getInt("asiakas_id"),
                    rs.getInt("mökki_id"),
                    rs.getDate("aloitus_päivä").toLocalDate(),
                    rs.getDate("lopetus_päivä").toLocalDate(),
                    rs.getString("tila"),
                    rs.getTimestamp("varaus_aika").toLocalDateTime());
                
                varaukset.add(varaus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return varaukset;
    }

    // Lisää uuden varauksen
    public void lisaaVaraus(Varaus varaus) {
        String sql = "INSERT INTO varaa (asiakas_id, mökki_id, aloitus_päivä, lopetus_päivä, tila, varaus_aika) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Tietokantayhteys.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, varaus.getAsiakasId());
            pstmt.setInt(2, varaus.getMokkiId());
            pstmt.setDate(3, Date.valueOf(varaus.getVarauksenAlku()));
            pstmt.setDate(4, Date.valueOf(varaus.getVarauksenLoppu()));
            pstmt.setString(5, varaus.getTila());
            pstmt.setTimestamp(6, Timestamp.valueOf(varaus.getVarausAika()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Päivittää varauksen tiedot tietokantaan
    public void paivitaVaraus(Varaus varaus) {
        String sql = "UPDATE varaa SET aloitus_päivä = ?, lopetus_päivä = ?, tila = ? WHERE id = ?";

        try (Connection conn = Tietokantayhteys.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(varaus.getVarauksenAlku()));
            pstmt.setDate(2, Date.valueOf(varaus.getVarauksenLoppu()));
            pstmt.setString(3, varaus.getTila());
            pstmt.setInt(4, varaus.getVarausId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Poistaa varauksen tietokannasta id:n perusteella
    public void poistaVaraus(int varausId) {
        String sql = "DELETE FROM varaa WHERE id = ?";

        try (Connection conn = Tietokantayhteys.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, varausId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    // Hakee varaukset valitulle mökille
    public List<Varaus> haeVarauksetMokille(int mokkiId) {
        List<Varaus> varaukset = new ArrayList<>();
        String sql = "SELECT * FROM varaa WHERE mökki_id = ? ORDER BY aloitus_päivä)";

        try (Connection conn = Tietokantayhteys.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mokkiId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Varaus varaus = new Varaus(
                    rs.getInt("id"), 
                    rs.getInt("asiakas_id"), 
                    rs.getInt("mökki_id"), 
                    rs.getDate("aloitus_päivä").toLocalDate(), 
                    rs.getDate("lopetus_päivä").toLocalDate(), 
                    rs.getString("tila"), 
                    rs.getTimestamp("varaus_aika").toLocalDateTime());

                    varaukset.add(varaus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return varaukset;
    }
}


