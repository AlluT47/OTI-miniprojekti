module oma.mokkipro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens oma.mokkipro to javafx.fxml;
    exports oma.mokkipro;
}