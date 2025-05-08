package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Asiakas;
import util.Tietokantayhteys;

// DAO-luokka asiakkaille, hakee asiakkaiden tiedot tietokannasta ja palauttaa ne listana.
public class AsiakasDAO {
    public List<Asiakas> haeKaikkiAsiakkaat() {
        List<Asiakas> asiakkaat = new ArrayList<>();
        String sql = "SELECT * FROM asiakas";

        try (Connection conn = Tietokantayhteys.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Asiakas asiakas = new Asiakas(
                    rs.getInt("id"), 
                    rs.getString("nimi"), 
                    rs.getString("sposti"), 
                    rs.getString("puhelin"), 
                    rs.getString("asiakastyyppi")
                );
                asiakkaat.add(asiakas);
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asiakkaat;
    }
    
    // Lisää uuden asiakkaan tietokantaan
    public void lisaaAsiakas(Asiakas asiakas) {
        String sql = "INSERT INTO asiakas (nimi, sposti, puhelin, asiakastyyppi) VALUES (?, ?, ?, ?)";

        try (Connection conn = Tietokantayhteys.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, asiakas.getNimi());
            pstmt.setString(2, asiakas.getSposti());
            pstmt.setString(3, asiakas.getPuhelin());
            pstmt.setString(4, asiakas.getAsiakastyyppi());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Päivittää asiakkaan tiedot tietokantaan
    public void paivitaAsiakas(Asiakas asiakas) {
        String sql = "UPDATE asiakas SET nimi = ?, sposti = ?, puhelin = ?, asiakastyyppi = ? WHERE id = ?";

        try (Connection conn = Tietokantayhteys.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, asiakas.getNimi());
            pstmt.setString(2, asiakas.getSposti());
            pstmt.setString(3, asiakas.getPuhelin());
            pstmt.setString(4, asiakas.getAsiakastyyppi());
            pstmt.setInt(5, asiakas.getAsiakasId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Poistaa asiakkaan tietokannasta id:n perusteella
    public void poistaAsiakas(int asiakasId) {
        String sql = "DELETE FROM asiakas WHERE id = ?";

        try (Connection conn = Tietokantayhteys.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, asiakasId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hakee asiakkaita nimellä
    public List<Asiakas> haeAsiakkaatNimella(String nimi) {
        List<Asiakas> asiakkaat = new ArrayList<>();
        String sql = "SELECT * FROM asiakas WHERE nimi LIKE ?";

        try (Connection conn = Tietokantayhteys.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, "%" + nimi + "%");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Asiakas asiakas = new Asiakas(
                        rs.getInt("id"), 
                        rs.getString("nimi"), 
                        rs.getString("sposti"), 
                        rs.getString("puhelin"), 
                        rs.getString("asiakastyyppi"));
                    
                    asiakkaat.add(asiakas);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return asiakkaat;
    }

    // Hakee asiakkaan id:llä
    public Asiakas haeAsiakasIdlla(int asiakasId) {
        String sql = "SELECT * FROM asiakas WHERE id LIKE ?";

        try (Connection conn = Tietokantayhteys.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, asiakasId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return new Asiakas(
                        rs.getInt("id"),
                        rs.getString("nimi"),
                        rs.getString("sposti"),
                        rs.getString("puhelin"),
                        rs.getString("asiakastyyppi"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
    }
}
