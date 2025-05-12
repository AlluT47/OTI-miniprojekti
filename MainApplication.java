package oma.mokkipro;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

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
        logIn.setVisible(true);


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

        cottagesButton.setOnAction(e -> mokinVuokrauksenHallinat());


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

        /*
        scenen alustus
        */
        Scene scene = new Scene(mainPane, 1024, 768);
        stage.setScene(scene);
        stage.setTitle("Mökki Manager Pro");
        stage.show();
    }

    //mökkien ja varausten hallinta
    public void mokinVuokrauksenHallinat() {
        Stage mokkiVarausStage = new Stage();

        Mokki m1 = new Mokki("Kesäkatu 5", 708, 1303, 315, 862, 8);
        Mokki m2 = new Mokki("Metsäkuja 7", 654, 1100, 205, 648, 6);
        Mokki m3 = new Mokki("Rantatie 13", 408, 671, 186, 378, 3);
        Mokki m4 = new Mokki("Lomatie 1", 315, 617, 150, 300, 2);
        Mokki m5 = new Mokki("Kesäkatu 15", 540, 956, 156, 556, 4);
        Mokki m6 = new Mokki("Kyläkatu 4", 589, 1000, 185, 614, 5);

        Text hpt = new Text("Hakupalkki:");
        Text hmt = new Text("Henkilomäärä:");

        Text mokki1 = new Text(m1.toString());
        Text mokki2 = new Text(m2.toString());
        Text mokki3 = new Text(m3.toString());
        Text mokki4 = new Text(m4.toString());
        Text mokki5 = new Text(m5.toString());
        Text mokki6 = new Text(m6.toString());


        TextField hakupalkki = new TextField();
        TextField henkilomaara = new TextField();

        Image image1 = new Image("file:src/main/java/kuvat/Kuva1.jpg");
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(120);
        iv1.setFitWidth(140);
        Image image2 = new Image("file:src/main/java/kuvat/Kuva2.jpg");
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(120);
        iv2.setFitWidth(140);
        Image image3 = new Image("file:src/main/java/kuvat/Kuva3.jpg");
        ImageView iv3 = new ImageView(image3);
        iv3.setFitHeight(120);
        iv3.setFitWidth(140);
        Image image4 = new Image("file:src/main/java/kuvat/Kuva4.jpg");
        ImageView iv4 = new ImageView(image4);
        iv4.setFitHeight(120);
        iv4.setFitWidth(140);
        Image image5 = new Image("file:src/main/java/kuvat/Kuva5.jpg");
        ImageView iv5 = new ImageView(image5);
        iv5.setFitHeight(120);
        iv5.setFitWidth(140);
        Image image6 = new Image("file:src/main/java/kuvat/Kuva6.jpg");
        ImageView iv6 = new ImageView(image6);
        iv6.setFitHeight(120);
        iv6.setFitWidth(140);

        Button haku = new Button("Hae");
        Button varaus1 = new Button("Varaa");
        Button varaus2 = new Button("Varaa");
        Button varaus3 = new Button("Varaa");
        Button varaus4 = new Button("Varaa");
        Button varaus5 = new Button("Varaa");
        Button varaus6 = new Button("Varaa");

        VBox vBox1 = new VBox(5);
        vBox1.getChildren().addAll(iv1, mokki1, varaus1);
        VBox vBox2 = new VBox(5);
        vBox2.getChildren().addAll(iv2, mokki2, varaus2);
        VBox vBox3 = new VBox(5);
        vBox3.getChildren().addAll(iv3, mokki3, varaus3);
        VBox vBox4 = new VBox(5);
        vBox4.getChildren().addAll(iv4, mokki4, varaus4);
        VBox vBox5 = new VBox(5);
        vBox5.getChildren().addAll(iv5, mokki5, varaus5);
        VBox vBox6 = new VBox(5);
        vBox6.getChildren().addAll(iv6, mokki6, varaus6);

        GridPane gridPane1 = new GridPane();
        gridPane1.setVgap(5);
        gridPane1.setHgap(10);
        gridPane1.add(hpt, 0, 0);
        gridPane1.add(hmt, 1, 0);
        gridPane1.add(hakupalkki, 0, 1);
        gridPane1.add(henkilomaara, 1, 1);
        gridPane1.add(haku, 2, 1);

        GridPane gridPane2 = new GridPane();
        gridPane2.setAlignment(Pos.CENTER);
        gridPane2.setVgap(10);
        gridPane2.setHgap(10);
        gridPane2.add(vBox1, 0, 2);
        gridPane2.add(vBox2, 1, 2);
        gridPane2.add(vBox3, 2, 2);
        gridPane2.add(vBox4, 0, 3);
        gridPane2.add(vBox5, 1, 3);
        gridPane2.add(vBox6, 2, 3);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(gridPane1, gridPane2);

        varaus1.setOnAction(e -> mokinTiedot(m1, iv1.getImage()));
        varaus2.setOnAction(e -> mokinTiedot(m2, iv2.getImage()));
        varaus3.setOnAction(e -> mokinTiedot(m3, iv3.getImage()));
        varaus4.setOnAction(e -> mokinTiedot(m4, iv4.getImage()));
        varaus5.setOnAction(e -> mokinTiedot(m5, iv5.getImage()));
        varaus6.setOnAction(e -> mokinTiedot(m6, iv6.getImage()));

        haku.setOnAction(e -> {
            String hakusana = hakupalkki.getText().toLowerCase();
            String maaraTeksti = henkilomaara.getText().trim();
            int maara = 0;
            boolean kaytaMaaraa = false;

            if (!maaraTeksti.isEmpty()) {
                try {
                    maara = Integer.parseInt(maaraTeksti);
                    kaytaMaaraa = true;
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Syötä kelvollinen henkilömäärä");
                    alert.show();
                    return;
                }
            }
            
            vBox1.setVisible(mokkiSopii(m1, hakusana, maara, kaytaMaaraa));
            vBox2.setVisible(mokkiSopii(m2, hakusana, maara, kaytaMaaraa));
            vBox3.setVisible(mokkiSopii(m3, hakusana, maara, kaytaMaaraa));
            vBox4.setVisible(mokkiSopii(m4, hakusana, maara, kaytaMaaraa));
            vBox5.setVisible(mokkiSopii(m5, hakusana, maara, kaytaMaaraa));
            vBox6.setVisible(mokkiSopii(m6, hakusana, maara, kaytaMaaraa));
        });

        Scene scene = new Scene(vBox, 600, 550);
        mokkiVarausStage.setTitle("Varausohjelma");
        mokkiVarausStage.setScene(scene);
        mokkiVarausStage.show();
    }

    private boolean mokkiSopii(Mokki mokki, String hakusana, int maara, boolean kaytaMaaraa) {
        boolean osoiteOsumat = hakusana.isEmpty() || mokki.getOsoite().toLowerCase().contains(hakusana);
        boolean maaraOsumat = !kaytaMaaraa || mokki.getKoko() >= maara;
        return osoiteOsumat && maaraOsumat;
    }


    //mökin tiedot
    private void mokinTiedot(Mokki mokki, Image image) {
        Stage varausStage = new Stage();

        TextArea tiedot = new TextArea(mokki.toString());
        tiedot.setPrefSize(250, 100);
        tiedot.setEditable(false);

        ImageView imageView = new ImageView(image);

        ObservableList<Varaus> varaukset = FXCollections.observableArrayList();
        ListView<Varaus> listView = new ListView<>(varaukset);

        Button lv = new Button("luo varaus");
        Button pv = new Button("poista varaus");
        Button mmt = new Button("muuta mökin tietoja");
        Button peruuta = new Button("peruuta");

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(lv, pv, mmt);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(tiedot, 0, 0);
        gridPane.add(imageView, 1, 0);
        gridPane.add(listView, 0, 1);
        gridPane.add(vBox, 1, 1);
        gridPane.add(peruuta, 0, 2);

        lv.setOnAction(e -> varauksenHallinta(mokki, varaukset));

        pv.setOnAction(e -> {
            Varaus valittu = listView.getSelectionModel().getSelectedItem();
            if (valittu != null) {
                varaukset.remove(valittu);
            }
        });

        peruuta.setOnAction(e -> varausStage.close());

        Scene scene = new Scene(gridPane, 600, 550);
        varausStage.setTitle("Mökin tiedot");
        varausStage.setScene(scene);
        varausStage.show();
    }

    //varauksen hallinta
    private void varauksenHallinta(Mokki mokki, ObservableList<Varaus> varaukset) {
        Stage vhStage = new Stage();

        TextField mokkiOsoite = new TextField();
        mokkiOsoite.setEditable(false);
        mokkiOsoite.setText(mokki.getOsoite());

        Text alt = new Text("asiakas lista");

        ListView asiakasLista = new ListView();

        DatePicker saapumisPaiva = new DatePicker();
        DatePicker lahtoPaiva = new DatePicker();

        Button uusiAsiakas = new Button("uusi asiakas");
        Button hyväksy = new Button("hyväksy varaus");
        Button peruuta = new Button("peruuta");

        VBox vBox1 = new VBox(5);
        vBox1.getChildren().addAll(alt, asiakasLista);

        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(peruuta, hyväksy);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(mokkiOsoite, vBox1, saapumisPaiva, lahtoPaiva, hBox);

        hyväksy.setOnAction(e -> {
            String osoite = mokkiOsoite.getText();
            LocalDate meno = saapumisPaiva.getValue();
            LocalDate tulo = lahtoPaiva.getValue();

            if (osoite.isEmpty() || meno == null || tulo == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Tietoja puuttuu");
                alert.show();
            }
            else {
                Varaus uusi = new Varaus(osoite, meno, tulo, mokki);
                varaukset.add(uusi);
                vhStage.close();
            }
        });

        peruuta.setOnAction(e -> vhStage.close());

        Scene scene = new Scene(vBox, 600, 550);
        vhStage.setTitle("Varauksen hallinta");
        vhStage.setScene(scene);
        vhStage.show();
    }
}

class Mokki {
    private String osoite;
    private double vhMin;
    private double vhMax;
    private double vlhMin;
    private double vlhMax;
    private int koko;

    public Mokki(String osoite, double vhMin, double vhMax,
                 double vlhMin, double vlhMax, int koko) {
        this.osoite = osoite;
        this.vhMin = vhMin;
        this.vhMax = vhMax;
        this.vlhMin = vlhMin;
        this.vlhMax = vlhMax;
        this.koko = koko;
    }

    public String getOsoite() {
        return osoite;
    }
    public void setOsoite(String osoite) {
        this.osoite = osoite;
    }

    public double getVhMin() {
        return vhMin;
    }
    public void setVhMin(double vhMin) {
        this.vhMin = vhMin;
    }

    public double getVhMax() {
        return vhMax;
    }
    public void setVhMax(double vhMax) {
        this.vhMax = vhMax;
    }

    public double getVlhMin() {
        return vlhMin;
    }
    public void setVlhMin(double vlhMin) {
        this.vlhMin = vlhMin;
    }

    public double getVlhMax() {
        return vlhMax;
    }
    public void setVlhMax() {
        this.vlhMax = vlhMax;
    }
    public int getKoko() {
        return koko;
    }
    public void setKoko(int koko) {
        this.koko = koko;
    }

    @Override
    public String toString() {
        return "Osoite: " + osoite + "\n" + "Viikkohinta: " + vhMin + " - " + vhMax + "€\n" +
                "Viikonloppuhinta: " + vlhMin + " - " + vlhMax + "€\n" + "Henkilömäärä: " + koko + " Henkilöä";
    }
}

class Varaus {
    private String nimi;
    private LocalDate meno;
    private LocalDate tulo;
    private Mokki mokki;

    public Varaus(String nimi, LocalDate meno, LocalDate tulo, Mokki mokki) {
        this.nimi = nimi;
        this.meno = meno;
        this.tulo = tulo;
        this.mokki = mokki;
    }

    public String getNimi() {
        return nimi;
    }
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public LocalDate getMeno() {
        return meno;
    }
    public void setMeno(LocalDate meno) {
        this.meno = meno;
    }

    public LocalDate getTulo() {
        return tulo;
    }
    public void setTulo(LocalDate tulo) {
        this.tulo = tulo;
    }

    public Mokki getMokki() {
        return mokki;
    }
    public void setMokki(Mokki mokki) {
        this.mokki = mokki;
    }

    @Override
    public String toString() {
        return nimi + ": " + meno + " - " + tulo;
    }
}
