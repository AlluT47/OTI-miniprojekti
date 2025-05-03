package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Tietokantayhteys {
    private static final String URL = "jdbc:mysql://localhost:3306/mokkimanagerpro";
    private static final String USER = "root"; //tähän oma käyttäjänimi
    private static final String PASSWORD = "0705"; //tähän oma salasana

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
