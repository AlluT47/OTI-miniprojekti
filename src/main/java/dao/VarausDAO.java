package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import model.Lasku;
import model.Raportointi;
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
        String sql = "SELECT * FROM varaa WHERE mökki_id = ? ORDER BY aloitus_päivä";

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

    // Hakee yhteenvedon raportointiin kaikkien mökkien osalta
    public Raportointi haeYhteenvetoKaikista(LocalDate alkupvm, LocalDate loppupvm) {
        int varaustenMaara = 0;
        int uudetAsiakkaat = 0;
        int palaavatAsiakkaat = 0;
        double kokonaistulot = 0;
        double keskimaarainenPituus = 0;
        double kayttoaste = 0;

        String varauksetSql = "SELECT COUNT(*) FROM varaa WHERE aloitus_päivä >= ? AND lopetus_päivä <= ?";
        String pituusSql = """
                SELECT AVG(DATEDIFF(lopetus_päivä, aloitus_päivä))
                FROM varaa WHERE aloitus_päivä >= ? AND lopetus_päivä <= ?;
                """;
        String tulotSql = """
                SELECT SUM(m.hinta_per_yö * DATEDIFF(v.lopetus_päivä, v.aloitus_päivä))
                FROM varaa v JOIN mokki m ON v.mökki_id = m.id
                WHERE v.aloitus_päivä >= ? AND v.lopetus_päivä <= ?
                """;
        String asiakasSql = """
                SELECT asiakas_id, COUNT(*) as kpl
                FROM varaa WHERE aloitus_päivä >= ? AND lopetus_päivä <= ? GROUP BY asiakas_id
                """;

        try (Connection conn = Tietokantayhteys.getConnection()) {

            // Varausten lukumäärä
            try (PreparedStatement pstmt = conn.prepareStatement(varauksetSql)) {
                pstmt.setDate(1, Date.valueOf(alkupvm));
                pstmt.setDate(2, Date.valueOf(loppupvm));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    varaustenMaara = rs.getInt(1);
                }
            }

            // Varausten keskimääräinen pituus
            try (PreparedStatement pstmt = conn.prepareStatement(pituusSql)) {
                pstmt.setDate(1, Date.valueOf(alkupvm));
                pstmt.setDate(2, Date.valueOf(loppupvm));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    keskimaarainenPituus = rs.getDouble(1);
                }
            }

            // Kokonaistulot
            try (PreparedStatement pstmt = conn.prepareStatement(tulotSql)) {
                pstmt.setDate(1, Date.valueOf(alkupvm));
                pstmt.setDate(2, Date.valueOf(loppupvm));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    kokonaistulot = rs.getDouble(1);
                }
            }

            // Uudet ja palaavat asiakkaat
            try (PreparedStatement pstmt = conn.prepareStatement(asiakasSql)) {
                pstmt.setDate(1, Date.valueOf(alkupvm));
                pstmt.setDate(2, Date.valueOf(loppupvm));
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int kpl = rs.getInt("kpl");
                    if (kpl == 1) {
                        uudetAsiakkaat++;
                    } else
                        palaavatAsiakkaat++;
                }
            }

            // Mökkien käyttöaste
            int paiviaYhteensa = (int) ChronoUnit.DAYS.between(alkupvm, alkupvm);
            int mokkienMaara = new MokkiDAO().haeKaikkiMokit().size();

            if (paiviaYhteensa > 0 && mokkienMaara > 0) {
                String kayttoasteSql = """
                        SELECT SUM(DATEDIFF(lopetus_päivä, aloitus_päivä))
                        FROM varaa WHERE aloitus_päivä >= ? AND lopetus_päivä <= ?
                        """;
                try (PreparedStatement pstmt = conn.prepareStatement(kayttoasteSql)) {
                    pstmt.setDate(1, Date.valueOf(alkupvm));
                    pstmt.setDate(2, Date.valueOf(loppupvm));
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        double kaytetyt = rs.getDouble(1);
                        kayttoaste = (kaytetyt / (paiviaYhteensa * mokkienMaara)) * 100;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Raportointi(varaustenMaara, uudetAsiakkaat, palaavatAsiakkaat,
                keskimaarainenPituus, kayttoaste, kokonaistulot);

    }

    // Hakee yhteenvedon raportointiin valitun mökin osalta
    public Raportointi haeYhteenvetoMokille(int mokkiId, LocalDate alkupvm, LocalDate loppupvm) {
        int varaustenMaara = 0;
        double kokonaistulot = 0;
        double keskimaarainenPituus = 0;
        double kayttoaste = 0;

        String varauksetSql = "SELECT COUNT(*) FROM varaa WHERE mökki_id = ? AND aloitus_päivä >= ? AND lopetus_päivä <= ?";
        String pituusSql = """
                SELECT AVG(DATEDIFF(lopetus_päivä, aloitus_päivä))
                FROM varaa WHERE mökki_id AND aloitus_päivä >= ? AND lopetus_päivä <= ?";
                """;
        String tulotSql = """
                SELECT SUM(m.hinta_per_yö * DATEDIFF(v.lopetus_päivä, v.aloitus_päivä))
                FROM varaa v JOIN mokki m ON v.mökki_id = m.id
                WHERE v.mökki_id = ? AND v.aloitus_päivä >= ? AND v.lopetus_päivä <= ?
                """;
        String kayttoasteSql = """
                SELECT SUM(DATEDIFF(lopetus_päivä, aloitus_päivä))
                FROM varaa WHERE mökki_id = ? AND aloitus_päivä >= ? AND lopetus_päivä <= ?
                """;

        try (Connection conn = Tietokantayhteys.getConnection()) {

            // Varausten lukumäärä
            try (PreparedStatement pstmt = conn.prepareStatement(varauksetSql)) {
                pstmt.setInt(1, mokkiId);
                pstmt.setDate(2, Date.valueOf(alkupvm));
                pstmt.setDate(3, Date.valueOf(loppupvm));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    varaustenMaara = rs.getInt(1);
                }
            }

            // Varausten keskimääräinen pituus
            try (PreparedStatement pstmt = conn.prepareStatement(pituusSql)) {
                pstmt.setInt(1, mokkiId);
                pstmt.setDate(2, Date.valueOf(alkupvm));
                pstmt.setDate(3, Date.valueOf(loppupvm));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    keskimaarainenPituus = rs.getDouble(1);
                }
            }

            // Kokonaistulot
            try (PreparedStatement pstmt = conn.prepareStatement(tulotSql)) {
                pstmt.setInt(1, mokkiId);
                pstmt.setDate(2, Date.valueOf(alkupvm));
                pstmt.setDate(3, Date.valueOf(loppupvm));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    kokonaistulot = rs.getDouble(1);
                }
            }

            // Mökin käyttöaste
            int paiviaYhteensa = (int) ChronoUnit.DAYS.between(alkupvm, alkupvm);
            if (paiviaYhteensa > 0) {
                try (PreparedStatement pstmt = conn.prepareStatement(kayttoasteSql)) {
                    pstmt.setInt(1, mokkiId);
                    pstmt.setDate(2, Date.valueOf(alkupvm));
                    pstmt.setDate(3, Date.valueOf(loppupvm));
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        double kaytetyt = rs.getDouble(1);
                        kayttoaste = (kaytetyt / paiviaYhteensa) * 100;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Raportointi(varaustenMaara, 0, 0, keskimaarainenPituus, kayttoaste, kokonaistulot);

    }


    // Hakee varauksem id:llä
    public Varaus haeVarausIdlla(int varausId) {
        String sql = "SELECT * FROM varaa WHERE id = ?";

        try (Connection conn = Tietokantayhteys.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, varausId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Varaus(
                        rs.getInt("id"),
                        rs.getInt("asiakas_id"),
                        rs.getInt("mökki_id"),
                        rs.getDate("aloitus_päivä").toLocalDate(),
                        rs.getDate("lopetus_päivä").toLocalDate(),
                        rs.getString("tila"),
                        rs.getTimestamp("varaus_aika").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}


