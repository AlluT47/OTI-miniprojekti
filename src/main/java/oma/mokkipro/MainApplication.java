package oma.mokkipro;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Tietokantayhteys;

import java.sql.Connection;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void ConnectToSQL(){
        try {
            Connection conn = Tietokantayhteys.getConnection();
            System.out.println("Yhteys tietokantaan muodostettu!"); //Tietokantayhteyden testaamiseksi, voi poistaa myöhemmin
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage stage) {

        ConnectToSQL();

        Pane mainPane = new Pane();

        /*
        Kirjautuminen
         */
        Pane logIn = new Pane();
        GridPane logInGridPane = new GridPane();
        BorderPane logInBorderPane = new BorderPane();

        //elementit
        Text titleText = new Text("Mökki Manager Pro");
        Label userLabel = new Label("Käyttäjä:");
        Label passwordLabel = new Label("Salasana:");
        TextField userTextField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button logInButton = new Button("Kirjaudu sisään");

        //isompi fontti
        titleText.setStyle("-fx-font: 32 arial;");

        //lisää elementit kirjautumis- gridpaneen
        logInGridPane.add(userLabel, 0, 1);
        logInGridPane.add(userTextField, 1, 1);
        logInGridPane.add(passwordLabel, 0, 2);
        logInGridPane.add(passwordField, 1, 2);
        logInGridPane.add(logInButton, 1, 3);

        GridPane.setHalignment(userLabel, HPos.RIGHT);
        GridPane.setHalignment(passwordLabel, HPos.RIGHT);

        logInGridPane.setHgap(5);
        logInGridPane.setVgap(15);

        //lisää elementit borderpaneen
        logInBorderPane.setTop(titleText);
        logInBorderPane.setCenter(logInGridPane);

        logIn.getChildren().add(logInBorderPane);
        logInBorderPane.relocate(30, 50);

        //kirjautumis -pane pääpaneen
        mainPane.getChildren().add(logIn);
        logIn.setVisible(false);


        /*
        Päävalikko
         */

        Pane mainMenu = new Pane();
        GridPane mainMenuGridPane = new GridPane();

        //elementit
        Button logOutButton = new Button("Kirjaudu ulos");
        Button cottagesButton = new Button("Mökit ja varaukset");
        Button customersButton = new Button("Asiakkaat");
        Button invoicesButton = new Button("Laskut");
        Button reportsButton = new Button("Raportit");

        cottagesButton.setPrefWidth(200);
        customersButton.setPrefWidth(200);
        invoicesButton.setPrefWidth(200);
        reportsButton.setPrefWidth(200);

        mainMenuGridPane.add(cottagesButton, 0, 0);
        mainMenuGridPane.add(customersButton, 0, 1);
        mainMenuGridPane.add(invoicesButton, 0, 2);
        mainMenuGridPane.add(reportsButton, 0, 3);

        mainMenuGridPane.setHgap(15);
        mainMenuGridPane.setVgap(15);

        mainMenu.getChildren().addAll(mainMenuGridPane, logOutButton);
        mainMenuGridPane.relocate(30, 50);
        logOutButton.relocate(10,10);

        mainPane.getChildren().add(mainMenu);
        mainMenu.setVisible(false);


        /*
        Asiakkaat -näkymä
         */
        Pane customers = new Pane();
        BorderPane customersBorderPane = new BorderPane();
        GridPane customerSearchGridPane = new GridPane();
        GridPane customersGridPane = new GridPane();

        Pane customer1Pane = new Pane();
        Pane customer2Pane = new Pane();
        Pane customer3Pane = new Pane();
        Pane customer4Pane = new Pane();
        Pane customer5Pane = new Pane();
        Pane customer6Pane = new Pane();
        Pane customer7Pane = new Pane();
        Pane customer8Pane = new Pane();
        Pane customer9Pane = new Pane();
        GridPane customer1GridPane = new GridPane();
        GridPane customer2GridPane = new GridPane();
        GridPane customer3GridPane = new GridPane();
        GridPane customer4GridPane = new GridPane();
        GridPane customer5GridPane = new GridPane();
        GridPane customer6GridPane = new GridPane();
        GridPane customer7GridPane = new GridPane();
        GridPane customer8GridPane = new GridPane();
        GridPane customer9GridPane = new GridPane();

        //asiakas 1

        Text customer1NameText = new Text("Matti Meikäläinen 1");
        Button customer1Button = new Button("Katso tietoja");

        Rectangle customer1BGRectangle = new Rectangle(140, 80);
        customer1BGRectangle.setFill(Color.WHITE);
        customer1BGRectangle.setStroke(Color.GREY);
        customer1GridPane.add(customer1NameText, 0, 0);
        customer1GridPane.add(customer1Button, 0, 1);

        customer1GridPane.setVgap(10);

        customer1Pane.getChildren().addAll(customer1BGRectangle, customer1GridPane);
        customer1GridPane.relocate(15, 15);
        customersGridPane.add(customer1Pane, 0, 0);

        //asiakas 2

        Text customer2NameText = new Text("Matti Meikäläinen 2");
        Button customer2Button = new Button("Katso tietoja");

        Rectangle customer2BGRectangle = new Rectangle(140, 80);
        customer2BGRectangle.setFill(Color.WHITE);
        customer2BGRectangle.setStroke(Color.GREY);
        customer2GridPane.add(customer2NameText, 0, 0);
        customer2GridPane.add(customer2Button, 0, 1);

        customer2GridPane.setVgap(10);

        customer2Pane.getChildren().addAll(customer2BGRectangle, customer2GridPane);
        customer2GridPane.relocate(15, 15);
        customersGridPane.add(customer2Pane, 1, 0);

        //asiakas 3

        Text customer3NameText = new Text("Matti Meikäläinen 3");
        Button customer3Button = new Button("Katso tietoja");

        Rectangle customer3BGRectangle = new Rectangle(140, 80);
        customer3BGRectangle.setFill(Color.WHITE);
        customer3BGRectangle.setStroke(Color.GREY);
        customer3GridPane.add(customer3NameText, 0, 0);
        customer3GridPane.add(customer3Button, 0, 1);

        customer3GridPane.setVgap(10);

        customer3Pane.getChildren().addAll(customer3BGRectangle, customer3GridPane);
        customer3GridPane.relocate(15, 15);
        customersGridPane.add(customer3Pane, 2, 0);

        //asiakas 4

        Text customer4NameText = new Text("Matti Meikäläinen 4");
        Button customer4Button = new Button("Katso tietoja");

        Rectangle customer4BGRectangle = new Rectangle(140, 80);
        customer4BGRectangle.setFill(Color.WHITE);
        customer4BGRectangle.setStroke(Color.GREY);
        customer4GridPane.add(customer4NameText, 0, 0);
        customer4GridPane.add(customer4Button, 0, 1);

        customer4GridPane.setVgap(10);

        customer4Pane.getChildren().addAll(customer4BGRectangle, customer4GridPane);
        customer4GridPane.relocate(15, 15);
        customersGridPane.add(customer4Pane, 0, 1);

        //asiakas 5

        Text customer5NameText = new Text("Matti Meikäläinen 5");
        Button customer5Button = new Button("Katso tietoja");

        Rectangle customer5BGRectangle = new Rectangle(140, 80);
        customer5BGRectangle.setFill(Color.WHITE);
        customer5BGRectangle.setStroke(Color.GREY);
        customer5GridPane.add(customer5NameText, 0, 0);
        customer5GridPane.add(customer5Button, 0, 1);

        customer5GridPane.setVgap(10);

        customer5Pane.getChildren().addAll(customer5BGRectangle, customer5GridPane);
        customer5GridPane.relocate(15, 15);
        customersGridPane.add(customer5Pane, 1, 1);

        //asiakas 6

        Text customer6NameText = new Text("Matti Meikäläinen 6");
        Button customer6Button = new Button("Katso tietoja");

        Rectangle customer6BGRectangle = new Rectangle(140, 80);
        customer6BGRectangle.setFill(Color.WHITE);
        customer6BGRectangle.setStroke(Color.GREY);
        customer6GridPane.add(customer6NameText, 0, 0);
        customer6GridPane.add(customer6Button, 0, 1);

        customer6GridPane.setVgap(10);

        customer6Pane.getChildren().addAll(customer6BGRectangle, customer6GridPane);
        customer6GridPane.relocate(15, 15);
        customersGridPane.add(customer6Pane, 2, 1);

        //asiakas 7

        Text customer7NameText = new Text("Matti Meikäläinen 7");
        Button customer7Button = new Button("Katso tietoja");

        Rectangle customer7BGRectangle = new Rectangle(140, 80);
        customer7BGRectangle.setFill(Color.WHITE);
        customer7BGRectangle.setStroke(Color.GREY);
        customer7GridPane.add(customer7NameText, 0, 0);
        customer7GridPane.add(customer7Button, 0, 1);

        customer7GridPane.setVgap(10);

        customer7Pane.getChildren().addAll(customer7BGRectangle, customer7GridPane);
        customer7GridPane.relocate(15, 15);
        customersGridPane.add(customer7Pane, 0, 2);

        //asiakas 8

        Text customer8NameText = new Text("Matti Meikäläinen 8");
        Button customer8Button = new Button("Katso tietoja");

        Rectangle customer8BGRectangle = new Rectangle(140, 80);
        customer8BGRectangle.setFill(Color.WHITE);
        customer8BGRectangle.setStroke(Color.GREY);
        customer8GridPane.add(customer8NameText, 0, 0);
        customer8GridPane.add(customer8Button, 0, 1);

        customer8GridPane.setVgap(10);

        customer8Pane.getChildren().addAll(customer8BGRectangle, customer8GridPane);
        customer8GridPane.relocate(15, 15);
        customersGridPane.add(customer8Pane, 1, 2);

        //asiakas 9

        Text customer9NameText = new Text("Matti Meikäläinen 9");
        Button customer9Button = new Button("Katso tietoja");

        Rectangle customer9BGRectangle = new Rectangle(140, 80);
        customer9BGRectangle.setFill(Color.WHITE);
        customer9BGRectangle.setStroke(Color.GREY);
        customer9GridPane.add(customer9NameText, 0, 0);
        customer9GridPane.add(customer9Button, 0, 1);

        customer9GridPane.setVgap(10);

        customer9Pane.getChildren().addAll(customer9BGRectangle, customer9GridPane);
        customer9GridPane.relocate(15, 15);
        customersGridPane.add(customer9Pane, 2, 2);



        customersGridPane.setVgap(15);
        customersGridPane.setHgap(15);

        TextField searchTextField = new TextField();
        Text customerCountText = new Text("{henkilömäärä}");
        Button customerSearchButton = new Button("Hae");

        Button previousPageButton = new Button("Edellinen sivu");
        Button nextPageButton = new Button("Seuraava sivu");

        Button customersBackButton = new Button("Takaisin");

        customerSearchGridPane.add(searchTextField, 0, 0);
        customerSearchGridPane.add(customerCountText, 1, 0);
        customerSearchGridPane.add(customerSearchButton, 2, 0);

        customerSearchGridPane.setHgap(15);
        BorderPane.setMargin(customersGridPane, new Insets(15));

        customersBorderPane.setTop(customerSearchGridPane);
        customersBorderPane.setCenter(customersGridPane);

        customers.getChildren().addAll(customersBorderPane, customersBackButton,
                previousPageButton, nextPageButton);
        customersBorderPane.relocate(30, 50);
        previousPageButton.relocate(30, 400);
        nextPageButton.relocate(450, 400);
        customersBackButton.relocate(10,10);

        mainPane.getChildren().add(customers);
        customers.setVisible(false);



        /*
        Asiakkaan tiedot -näkymä
        */
        Pane customerInfo = new Pane();
        FlowPane customerInfoFlowPane = new FlowPane();

        //elementit
        Button customerInfoBackButton = new Button("Takaisin");
        Text customerNameText = new Text("Asiakkaan nimi");
        TextArea customerInfoTextArea = new TextArea("Asiakkaan sähköposti: " +
                "\nPuhelinnumero: \nOsoite: ");
        customerInfoTextArea.setEditable(false);
        Button changeCustomerInfoButton = new Button("Muuta tietoja");
        Button removeCustomerButton = new Button("Poista asiakas");

        //lisää elementit flowpaneen
        customerInfoFlowPane.getChildren().addAll(customerNameText, customerInfoTextArea,
                changeCustomerInfoButton, removeCustomerButton);

        customerInfoFlowPane.setHgap(15);
        customerInfoFlowPane.setVgap(15);

        //flowpane ja takaisin -nappi paneen
        customerInfo.getChildren().addAll(customerInfoFlowPane, customerInfoBackButton);
        customerInfoFlowPane.relocate(30, 50);
        customerInfoBackButton.relocate(10, 10);

        //asiakasinfo -pane pääpaneen
        mainPane.getChildren().add(customerInfo);
        customerInfo.setVisible(false);


        /*
        Asiakkaan lisääminen/tietojen muuttaminen
        */
        Pane customerEdit = new Pane();
        GridPane customerEditGridPane = new GridPane();

        //elementit
        Button customerEditBackButton = new Button("Takaisin");
        Label customerNameLabel = new Label("Nimi:");
        Label customerPhoneLabel = new Label("Puhelinnumero:");
        Label customerEmailLabel = new Label("Sähköposti:");
        Label customerAddressLabel = new Label("Osoite:");
        TextField customerNameTextField = new TextField();
        TextField customerPhoneTextField = new TextField();
        TextField customerEmailTextField = new TextField();
        TextArea customerAddressTextArea = new TextArea();
        Button confirmCustomerEditButton = new Button("Hyväksy");

        //lisää elementit gridpaneen
        customerEditGridPane.add(customerNameLabel, 0, 0);
        customerEditGridPane.add(customerNameTextField, 1, 0);
        customerEditGridPane.add(customerPhoneLabel, 0, 1);
        customerEditGridPane.add(customerPhoneTextField, 1, 1);
        customerEditGridPane.add(customerEmailLabel, 0, 2);
        customerEditGridPane.add(customerEmailTextField, 1,2);
        customerEditGridPane.add(customerAddressLabel, 0, 3);
        customerEditGridPane.add(customerAddressTextArea, 1, 3);

        customerEditGridPane.setHgap(15);
        customerEditGridPane.setVgap(15);

        //gridpane ja takaisin -nappi paneen
        customerEdit.getChildren().addAll(customerEditGridPane, customerEditBackButton,
                confirmCustomerEditButton);
        customerEditGridPane.relocate(30, 50);
        customerEditBackButton.relocate(10, 10);
        confirmCustomerEditButton.relocate(450,400);

        //muokkaa asiakasinfo -pane pääpaneen
        mainPane.getChildren().add(customerEdit);
        customerEdit.setVisible(false);


        /*
        Mökin tiedot
         */
        Pane cottageInfo = new Pane();
        GridPane cottageInfoButtonsGridPane = new GridPane();
        GridPane cottageInfoGridPane = new GridPane();

        Button createReservationButton = new Button("Luo varaus");
        Button changeReservationButton = new Button("Muuta varausta");
        Button removeReservationButton = new Button("Poista varaus");
        Button changeCottageInfoButton = new Button("Muuta mökin tietoja");

        cottageInfoButtonsGridPane.add(createReservationButton, 0,0);
        cottageInfoButtonsGridPane.add(changeReservationButton, 0,1);
        cottageInfoButtonsGridPane.add(removeReservationButton, 0,2);
        cottageInfoButtonsGridPane.add(changeCottageInfoButton, 0,3);

        cottageInfoButtonsGridPane.setHgap(15);
        cottageInfoButtonsGridPane.setVgap(15);

        Image placeholderImage = new Image(getClass().getResource("300x200.png").toString());

        ImageView cottageImageView = new ImageView(placeholderImage);

        TextArea cottageInfoTextArea = new TextArea("Mökin nimi: \nOsoite: \nKuvaus: \nHinta per yö: \nKapasiteetti: ");
        cottageInfoTextArea.setEditable(false);

        ObservableList<String> reservationsList = FXCollections.observableArrayList("varaus1", "varaus2");
        ListView<String> reservationsListView = new ListView<String>(reservationsList);

        Button cottageInfoBackButton = new Button("Takaisin");


        cottageInfoGridPane.add(cottageInfoTextArea, 0, 0);
        cottageInfoGridPane.add(cottageImageView, 1, 0);
        cottageInfoGridPane.add(reservationsListView, 0, 1);
        cottageInfoGridPane.add(cottageInfoButtonsGridPane, 1, 1);

        cottageInfoGridPane.setVgap(15);
        cottageInfoGridPane.setHgap(15);

        cottageInfo.getChildren().addAll(cottageInfoGridPane, cottageInfoBackButton);
        cottageInfoBackButton.relocate(10,10);
        cottageInfoGridPane.relocate(30, 50);

        mainPane.getChildren().add(cottageInfo);
        cottageInfo.setVisible(false);

        


        //painikkeet

        logInButton.setOnAction(e->{
            logIn.setVisible(false);
            mainMenu.setVisible(true);
        });

        logOutButton.setOnAction(e->{
            mainMenu.setVisible(false);
            logIn.setVisible(true);
        });

        customersButton.setOnAction(e->{
            mainMenu.setVisible(false);
            customers.setVisible(true);
        });

        customer1Button.setOnAction(e->{
            customers.setVisible(false);
            customerInfo.setVisible(true);
        });

        customerInfoBackButton.setOnAction(e->{
            customerInfo.setVisible(false);
            customers.setVisible(true);
        });

        changeCustomerInfoButton.setOnAction(e->{
            customerInfo.setVisible(false);
            customerEdit.setVisible(true);
        });

        customerEditBackButton.setOnAction(e->{
            customerEdit.setVisible(false);
            customerInfo.setVisible(true);
        });

        customersBackButton.setOnAction(e->{
            customers.setVisible(false);
            mainMenu.setVisible(true);
        });

        cottageInfoBackButton.setOnAction(e->{
            cottageInfo.setVisible(false);
            mainMenu.setVisible(true);
        });

        cottagesButton.setOnAction(e->{
            mainMenu.setVisible(false);
            cottageInfo.setVisible(true);
        });

        /*
        scenen alustus
        */
        Scene scene = new Scene(mainPane, 1024, 768);
        stage.setScene(scene);
        stage.setTitle("Mökki Manager Pro");
        stage.show();
    }
}
