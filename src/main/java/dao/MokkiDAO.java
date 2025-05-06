package dao;

import java.sql.Connection;
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
    
}
