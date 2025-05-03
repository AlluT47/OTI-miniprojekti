module oma.mokkipro {
    requires javafx.controls;
    requires javafx.fxml;


    opens oma.mokkipro to javafx.fxml;
    exports oma.mokkipro;
}