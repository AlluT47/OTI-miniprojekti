package oma.mokkipro;

import java.sql.Connection;
import javafx.application.Application;
import javafx.stage.Stage;
import util.Tietokantayhteys;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {



        try {
            Connection conn = Tietokantayhteys.getConnection();
            System.out.println("Yhteys tietokantaan muodostettu!"); //Tietokantayhteyden testaamiseksi, voi poistaa myöhemmin
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
