package oma.mokkipro;
import dao.AsiakasDAO;
import dao.LaskuDAO;
import dao.MokkiDAO;
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
import model.Asiakas;
import model.Lasku;
import model.Mokki;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //listat varauksista ja asiakkaista käyttöliittymää varten
    private ObservableList<String> invoicesList = FXCollections.observableArrayList();
    private ObservableList<String> customersList = FXCollections.observableArrayList();
    private ObservableList<String> cottageList = FXCollections.observableArrayList();
    private ArrayList<String> customersIDList = new ArrayList<>();

    //käyttöliittymän elementtejä, asiakas -näkymää varten
    private GridPane customer1GridPane, customer2GridPane, customer3GridPane, customer4GridPane, customer5GridPane,
            customer6GridPane, customer7GridPane, customer8GridPane, customer9GridPane;

    private Text customer1NameText, customer2NameText, customer3NameText, customer4NameText, customer5NameText,
            customer6NameText, customer7NameText, customer8NameText, customer9NameText;

    //tämänhetkinen sivu, jolla asiakas -näkymässä ollaan
    private int currentCustomerPage = 0;

    //asiakkaan tietojen tarkastelu-näkymää varten
    private Text customerNameText;
    private TextArea customerInfoTextArea;

    private TextField customerNameTextField;
    private TextField customerPhoneTextField;
    private TextField customerEmailTextField;
    private TextField customerTypeTextField;

    private boolean editCustomer;

    //tällähetkellä valittu asiakas, jota tarkastellaan ja muokataan
    private Asiakas currentCustomer;


    private void setCustomerToBeChanged(){
        customerNameTextField.setText(currentCustomer.getNimi());
        customerPhoneTextField.setText(currentCustomer.getPuhelin());
        customerEmailTextField.setText(currentCustomer.getSposti());
        customerTypeTextField.setText(currentCustomer.getAsiakastyyppi());
    }


    /**
     * Hakee tietokannasta varausten ID:t ja eräpäivät listaan
     */
    private void fetchInvoiceIDs(){
        LaskuDAO laskuDAO = new LaskuDAO();
        List<Lasku> laskuList = laskuDAO.haeKaikkiLaskut();
        for(Lasku l:laskuList){
            invoicesList.add(l.getLaskuId() + ", " + l.getErapaiva());
        }
    }


    /**
     * Hakee tietokannasta asiakkaiden nimet ja ID:t listoihin.
     */
    private void fetchCustomerNames(){
        customersList.clear();
        customersIDList.clear();
        AsiakasDAO asiakasDAO = new AsiakasDAO();
        List<Asiakas> asiakasList = asiakasDAO.haeKaikkiAsiakkaat();
        for(Asiakas a:asiakasList){
            customersIDList.add(String.valueOf(a.getAsiakasId()));
            customersList.add(a.getNimi());
            System.out.println(a.getAsiakasId());
        }
    }


    /**
     * Hakee tietokannasta mökkien nimet listaan
     */
    private void fetchCottageNames(){
        MokkiDAO mokkiDAO = new MokkiDAO();
        List<Mokki> mokkiList = mokkiDAO.haeKaikkiMokit();
        for(Mokki m:mokkiList){
            cottageList.add(m.getNimi());
            System.out.println(m.getMokkiId());
        }
    }

    /**
     * Hakee tietyn ID:n omaavan asiakkaan tiedot tietokannasta, ja asettaa ne asiakkaan tiedot -näkymän elementteihin.
     * @param id haettavan asiakkaan ID
     */
    private void fetchCustomerInfoFromID(int id){
        AsiakasDAO asiakasDAO = new AsiakasDAO();
        Asiakas a = asiakasDAO.haeAsiakasIdlla(id);
        //asettaa nykyisen asiakkaan
        currentCustomer = a;
        //muuttaa käyttöliittymän elementtejä
        customerNameText.setText(a.getNimi());
        customerInfoTextArea.setText("Asiakkaan sähköposti: " + a.getSposti() +
                "\nPuhelinnumero: " + a.getPuhelin() + "\nAsiakastyyppi: " + a.getAsiakastyyppi());
    }


    /**
     * Hakee asiakas -näkymässä tietyn indeksin asiakkaan ID:n. Palauttaa ID:n kokonaislukuna.
     * @param selectedIndex valitun asiakkaan indeksi
     * @return asiakkaan ID kokonaislukuna
     */
    private int fetchSelectedCustomerId(int selectedIndex){
        String ID = customersIDList.get(selectedIndex + 9*currentCustomerPage - 1);
        return Integer.parseInt(ID);

    }


    /**
     * Asettaa asiakkaan tiedot-näkymän elementit tyhjiksi.
     */
    private void resetCustomerInfoTextFields(){
        customerNameTextField.setText("");
        customerPhoneTextField.setText("");
        customerEmailTextField.setText("");
        customerTypeTextField.setText("");
    }

    /**
     * Muuttaa asiakkaan tietoja tietokannassa. Ottaa uudet arvot käyttöliittymästä.
     */
    private void confirmCustomerInfo(){

        if(!editCustomer){
            Random rnd = new Random();
            currentCustomer = new Asiakas(rnd.nextInt(), "", "", "", "");
        }

        //nykyisen asiakkaan tietoja muutetaan
        currentCustomer.setNimi(customerNameTextField.getText());
        currentCustomer.setSposti(customerEmailTextField.getText());
        currentCustomer.setPuhelin(customerPhoneTextField.getText());
        currentCustomer.setAsiakastyyppi(customerTypeTextField.getText());

        //uusi asiakasDAO
        AsiakasDAO asiakasDAO = new AsiakasDAO();

        if(editCustomer){
            //päivitetään asiakas
            asiakasDAO.paivitaAsiakas(currentCustomer);

            //päivitetään asiakkaiden nimilista
            int customerListIndex = customersIDList.indexOf(String.valueOf(currentCustomer.getAsiakasId()));
            customersList.remove(customerListIndex);
            customersList.add(customerListIndex, currentCustomer.getNimi());

        } else {
            //lisätään asiakas
            asiakasDAO.lisaaAsiakas(currentCustomer);
            customersIDList.add(String.valueOf(currentCustomer.getAsiakasId()));
            customersList.add(currentCustomer.getNimi());
        }

        //päivitetään asiakas -näkymä
        setCustomersPage(currentCustomerPage);

        //päivitetään asiakkaan tiedot -näkymän elementit
        customerNameText.setText(currentCustomer.getNimi());
        customerInfoTextArea.setText("Asiakkaan sähköposti: " + currentCustomer.getSposti() +
                "\nPuhelinnumero: " + currentCustomer.getPuhelin() + "\nAsiakastyyppi: "
                + currentCustomer.getAsiakastyyppi());

        //System.out.println("asiakas päivitetty!");
    }


    /**
     * Poistaa nykyisen asiakkaan tietokannasta.
     */
    private void removeCustomer(){
        int customerListIndex = customersIDList.indexOf(String.valueOf(currentCustomer.getAsiakasId()));
        customersList.remove(customerListIndex);
        customersIDList.remove(customerListIndex);
        AsiakasDAO asiakasDAO = new AsiakasDAO();
        asiakasDAO.poistaAsiakas(currentCustomer.getAsiakasId());
        setCustomersPage(currentCustomerPage);
        System.out.println("asiakas poistettu...");
    }

    /**
     * Asettaa asiakas -näkymän asiakkaiden sivun annettuun lukuun.
     * @param page uusi sivunumero kokonaislukuna
     */
    private void setCustomersPage(int page){
        currentCustomerPage = page;
        //ensin kaikki asikaspanet piiloon
        customer1GridPane.setVisible(false);
        customer2GridPane.setVisible(false);
        customer3GridPane.setVisible(false);
        customer4GridPane.setVisible(false);
        customer5GridPane.setVisible(false);
        customer6GridPane.setVisible(false);
        customer7GridPane.setVisible(false);
        customer8GridPane.setVisible(false);
        customer9GridPane.setVisible(false);

        //käydään läpi sivun asiakkaat, indeksistä sivunnumero * 9 + 1 indeksiin (sivunnumero + 1) * 9 + 1 asti
        for (int i = page*9 + 1; i <(page+1)*9 +1 ; i++) {
            //jos indeksin jakojäännös on 1, ja listassa on tarpeeksi asiakkaita, laitetaan sivun ensimmäinen
            //asiakas täksi asiakkaaksi, ja näytetään asiakaspane. sama muille kahdeksalle
            if(i%9 == 1 && customersList.size() >= i){
                customer1NameText.setText(customersList.get(i-1));
                customer1GridPane.setVisible(true);
            }if(i%9 == 2 && customersList.size() >= i){
                customer2NameText.setText(customersList.get(i-1));
                customer2GridPane.setVisible(true);
            }if(i%9 == 3 && customersList.size() >= i){
                customer3NameText.setText(customersList.get(i-1));
                customer3GridPane.setVisible(true);
            }if(i%9 == 4 && customersList.size() >= i){
                customer4NameText.setText(customersList.get(i-1));
                customer4GridPane.setVisible(true);
            }if(i%9 == 5 && customersList.size() >= i){
                customer5NameText.setText(customersList.get(i-1));
                customer5GridPane.setVisible(true);
            }if(i%9 == 6 && customersList.size() >= i){
                customer6NameText.setText(customersList.get(i-1));
                customer6GridPane.setVisible(true);
            }if(i%9 == 7 && customersList.size() >= i){
                customer7NameText.setText(customersList.get(i-1));
                customer7GridPane.setVisible(true);
            }if(i%9 == 8 && customersList.size() >= i){
                customer8NameText.setText(customersList.get(i-1));
                customer8GridPane.setVisible(true);
            }if(i%9 == 0 && customersList.size() >= i){
                customer9NameText.setText(customersList.get(i-1));
                customer9GridPane.setVisible(true);
            }
        }
    }


    @Override
    public void start(Stage stage) {

        //hakee ensin tietokannasta varaukset ja asiakkaat
        fetchInvoiceIDs();
        fetchCustomerNames();
        fetchCottageNames();

        //pääpane
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
        customer1GridPane = new GridPane();
        customer2GridPane = new GridPane();
        customer3GridPane = new GridPane();
        customer4GridPane = new GridPane();
        customer5GridPane = new GridPane();
        customer6GridPane = new GridPane();
        customer7GridPane = new GridPane();
        customer8GridPane = new GridPane();
        customer9GridPane = new GridPane();

        //asiakas 1

        customer1NameText = new Text("Matti Meikäläinen 1");
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

        customer2NameText = new Text("Matti Meikäläinen 2");
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

        customer3NameText = new Text("Matti Meikäläinen 3");
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

        customer4NameText = new Text("Matti Meikäläinen 4");
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

        customer5NameText = new Text("Matti Meikäläinen 5");
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

        customer6NameText = new Text("Matti Meikäläinen 6");
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

        customer7NameText = new Text("Matti Meikäläinen 7");
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

        customer8NameText = new Text("Matti Meikäläinen 8");
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

        customer9NameText = new Text("Matti Meikäläinen 9");
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

        previousPageButton.setOnAction(e->{
            if(currentCustomerPage>= 1){
                setCustomersPage(currentCustomerPage-1);
            }
        });

        nextPageButton.setOnAction(e->{
            if(currentCustomerPage + 1 <= customersList.size()/9){
                setCustomersPage(currentCustomerPage + 1);
            }
        });

        Button customersBackButton = new Button("Takaisin");

        Button addCustomerButton = new Button("Lisää asiakas");

        customerSearchGridPane.add(searchTextField, 0, 0);
        customerSearchGridPane.add(customerCountText, 1, 0);
        customerSearchGridPane.add(customerSearchButton, 2, 0);

        customerSearchGridPane.setHgap(15);
        BorderPane.setMargin(customersGridPane, new Insets(15));

        customersBorderPane.setTop(customerSearchGridPane);
        customersBorderPane.setCenter(customersGridPane);

        customers.getChildren().addAll(customersBorderPane, customersBackButton,
                previousPageButton, nextPageButton, addCustomerButton);
        customersBorderPane.relocate(30, 50);
        previousPageButton.relocate(30, 400);
        nextPageButton.relocate(450, 400);
        customersBackButton.relocate(10,10);
        addCustomerButton.relocate(230, 400);

        mainPane.getChildren().add(customers);
        customers.setVisible(false);



        /*
        Asiakkaan tiedot -näkymä
        */
        Pane customerInfo = new Pane();
        FlowPane customerInfoFlowPane = new FlowPane();

        //elementit
        Button customerInfoBackButton = new Button("Takaisin");
        customerNameText = new Text("Asiakkaan nimi");
        customerInfoTextArea = new TextArea("Asiakkaan sähköposti: " +
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
        Label customerTypeLabel = new Label("Asiakastyyppi:");
        customerNameTextField = new TextField();
        customerPhoneTextField = new TextField();
        customerEmailTextField = new TextField();
        customerTypeTextField = new TextField();
        customerNameTextField.setPrefWidth(200);
        customerPhoneTextField.setPrefWidth(200);
        customerEmailTextField.setPrefWidth(200);
        customerTypeTextField.setPrefWidth(200);
        Button confirmCustomerEditButton = new Button("Hyväksy");

        //lisää elementit gridpaneen
        customerEditGridPane.add(customerNameLabel, 0, 0);
        customerEditGridPane.add(customerNameTextField, 1, 0);
        customerEditGridPane.add(customerPhoneLabel, 0, 1);
        customerEditGridPane.add(customerPhoneTextField, 1, 1);
        customerEditGridPane.add(customerEmailLabel, 0, 2);
        customerEditGridPane.add(customerEmailTextField, 1,2);
        customerEditGridPane.add(customerTypeLabel, 0, 3);
        customerEditGridPane.add(customerTypeTextField, 1, 3);

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
        Asiakkaan poiston varmistaminen
         */
        Pane confirmRemoveCustomer = new Pane();
        GridPane confirmRemoveGridPane = new GridPane();

        Text removeInfoText = new Text("Asiakkaan poistaminen on lopullista. \nHaluatko poistaa asiakkaan järjestelmästä?");

        Button cancelRemoveButton = new Button("Peruuta");
        Button acceptRemoveButton = new Button("Kyllä, poista asiakas");

        confirmRemoveGridPane.setHgap(30);
        confirmRemoveGridPane.setVgap(30);

        confirmRemoveGridPane.add(removeInfoText, 1, 0);
        confirmRemoveGridPane.add(cancelRemoveButton, 0,1);
        confirmRemoveGridPane.add(acceptRemoveButton, 2,1);

        confirmRemoveCustomer.getChildren().add(confirmRemoveGridPane);
        confirmRemoveGridPane.relocate(200,200);

        mainPane.getChildren().add(confirmRemoveCustomer);
        confirmRemoveCustomer.setVisible(false);

        /*
        Mökin tiedot
         */
        Pane cottageInfo = new Pane();
        GridPane cottageInfoButtonsGridPane = new GridPane();
        GridPane cottageInfoGridPane = new GridPane();

        Button createReservationButton = new Button("Luo varaus");
        Button editReservationButton = new Button("Muuta varausta");
        Button removeReservationButton = new Button("Poista varaus");
        Button editCottageInfoButton = new Button("Muuta mökin tietoja");

        cottageInfoButtonsGridPane.add(createReservationButton, 0,0);
        cottageInfoButtonsGridPane.add(editReservationButton, 0,1);
        cottageInfoButtonsGridPane.add(removeReservationButton, 0,2);
        cottageInfoButtonsGridPane.add(editCottageInfoButton, 0,3);

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

        /*
        Varauksen hallinta
         */
        Pane reservationEdit = new Pane();
        GridPane reservationEditGridPane = new GridPane();

        Text cottageNameText = new Text("Mökin nimi");

        Text reservationEditInfoText = new Text("Valitse asiakas listasta tai luo uusi asiakastieto:");

        ListView<String> customersListView = new ListView<>(customersList);

        Button newCustomerButton = new Button("Uusi asiakas");

        Label arrivalDayLabel = new Label("Saapumispäivä:");
        Label departureDayLabel = new Label("Lähtöpäivä:");
        DatePicker arrivalDayDatePicker = new DatePicker();
        DatePicker departureDayDatePicker = new DatePicker();
        Button reservationEditBackButton = new Button("Takaisin");
        Label numberOfPeopleStayingLabel = new Label("Yöpyjien määrä:");
        TextField numberOfPeopleStayingTextField = new TextField();
        Button confirmReservationEditButton = new Button("Hyväksy");

        reservationEditGridPane.add(cottageNameText, 0, 0);
        reservationEditGridPane.add(reservationEditInfoText, 0, 1);
        reservationEditGridPane.add(customersListView, 0, 2);
        reservationEditGridPane.add(newCustomerButton, 0, 3);
        reservationEditGridPane.add(arrivalDayLabel, 0, 4);
        reservationEditGridPane.add(arrivalDayDatePicker, 1, 4);
        reservationEditGridPane.add(departureDayLabel, 0, 5);
        reservationEditGridPane.add(departureDayDatePicker, 1, 5);
        reservationEditGridPane.add(numberOfPeopleStayingLabel, 0, 6);
        reservationEditGridPane.add(numberOfPeopleStayingTextField, 1, 6);
        reservationEditGridPane.add(confirmReservationEditButton, 1, 7);

        reservationEditGridPane.setVgap(15);

        reservationEdit.getChildren().addAll(reservationEditGridPane, reservationEditBackButton);
        reservationEditGridPane.relocate(30,50);
        reservationEditBackButton.relocate(10,10);

        mainPane.getChildren().add(reservationEdit);
        reservationEdit.setVisible(false);


        /*
        Mökin lisääminen
         */
        Pane addCottage = new Pane();
        GridPane addCottageGridPane = new GridPane();

        Label cottageNameLabel = new Label("Mökin nimi:");
        Label addressLabel = new Label("Osoite:");
        Label descriptionLabel = new Label("Kuvaus:");
        Label pricePerNightLabel = new Label("Hinta yöltä:");
        Label capacityLabel = new Label("Kapasiteetti:");

        TextField cottageNameTextField = new TextField();
        TextArea addressTextArea = new TextArea();
        TextArea descriptionTextArea = new TextArea();
        TextField pricePerNightTextField = new TextField();
        TextField capacityTextField = new TextField();

        Button confirmAddCottageButton = new Button("Hyväksy");
        Button addCottageBackButton = new Button("Takaisin");

        addCottageGridPane.add(cottageNameLabel, 0, 0);
        addCottageGridPane.add(cottageNameTextField, 1, 0);
        addCottageGridPane.add(addressLabel, 0, 1);
        addCottageGridPane.add(addressTextArea, 1, 1);
        addCottageGridPane.add(descriptionLabel, 0, 2);
        addCottageGridPane.add(descriptionTextArea, 1, 2);
        addCottageGridPane.add(pricePerNightLabel, 0, 3);
        addCottageGridPane.add(pricePerNightTextField, 1, 3);
        addCottageGridPane.add(capacityLabel, 0, 4);
        addCottageGridPane.add(capacityTextField, 1, 4);
        addCottageGridPane.add(confirmAddCottageButton, 1, 5);

        addCottageGridPane.setVgap(15);
        addCottageGridPane.setHgap(15);

        addCottage.getChildren().addAll(addCottageGridPane, addCottageBackButton);
        addCottageGridPane.relocate(30,50);
        addCottageBackButton.relocate(10,10);

        mainPane.getChildren().add(addCottage);
        addCottage.setVisible(false);


        /*
        Laskujen tarkastelu
         */
        Pane invoices = new Pane();
        BorderPane invoicesBorderPane = new BorderPane();

        Button invoiceInfoButton = new Button("Laskun tiedot");

        ListView<String> invoicesListView = new ListView<>(invoicesList);
        Button invoiceBackButton = new Button("Takaisin");

        invoicesBorderPane.setLeft(invoicesListView);
        invoicesBorderPane.setRight(invoiceInfoButton);
        BorderPane.setMargin(invoicesListView, new Insets(10));
        BorderPane.setMargin(invoiceInfoButton, new Insets(10));

        invoices.getChildren().addAll(invoicesBorderPane, invoiceBackButton);
        invoicesBorderPane.relocate(30,50);
        invoiceBackButton.relocate(10,10);

        mainPane.getChildren().add(invoices);
        invoices.setVisible(false);


        /*
        Laskun tiedot
         */
        Pane invoiceInfo = new Pane();
        GridPane invoiceInfoGridPane = new GridPane();

        invoiceInfoGridPane.setVgap(15);
        invoiceInfoGridPane.setHgap(15);

        Button invoiceInfoBackButton = new Button("Takaisin");

        Label invoiceIDLabel = new Label("Laskun ID:");
        Label invoiceCottageNameLabel = new Label("Mökin nimi:");
        Label invoiceCustomerNameLabel = new Label("Asiakkaan nimi");
        Label invoiceCustomerPhoneLabel = new Label("Asiakkaan puhelinnumero:");
        Label invoiceArrivalDayLabel = new Label("Tulopäivä:");
        Label invoiceDepartureDayLabel = new Label("Lähtöpäivä:");
        Label invoiceDayAmountLabel = new Label("Päivien määrä:");
        Label invoiceCostPerNightLabel = new Label("Hinta/yö:");
        Label invoiceCostLabel = new Label("Kokonaishinta:");
        Label invoiceDueDayLabel = new Label("Laskun eräpäivä:");

        TextField invoiceIDTextField = new TextField();
        TextField invoiceCottageNameTextField = new TextField();
        TextField invoiceCustomerNameTextField = new TextField();
        TextField invoiceCustomerPhoneTextField = new TextField();
        TextField invoiceArrivalDayTextField = new TextField();
        TextField invoiceDepartureDayTextField = new TextField();
        TextField invoiceDayAmountTextField = new TextField();
        TextField invoiceCostPerNightTextField = new TextField();
        TextField invoiceCostTextField = new TextField();
        TextField invoiceDueDayTextField = new TextField();

        invoiceIDTextField.setEditable(false);
        invoiceCottageNameTextField.setEditable(false);
        invoiceCustomerNameTextField.setEditable(false);
        invoiceCustomerPhoneTextField.setEditable(false);
        invoiceArrivalDayTextField.setEditable(false);
        invoiceDepartureDayTextField.setEditable(false);
        invoiceDayAmountTextField.setEditable(false);
        invoiceCostPerNightTextField.setEditable(false);
        invoiceCostTextField.setEditable(false);
        invoiceDueDayTextField.setEditable(false);

        invoiceInfoGridPane.add(invoiceIDLabel, 0, 0);
        invoiceInfoGridPane.add(invoiceIDTextField, 1, 0);
        invoiceInfoGridPane.add(invoiceCottageNameLabel, 0, 1);
        invoiceInfoGridPane.add(invoiceCottageNameTextField, 1, 1);
        invoiceInfoGridPane.add(invoiceCustomerNameLabel, 0, 2);
        invoiceInfoGridPane.add(invoiceCustomerNameTextField, 1, 2);
        invoiceInfoGridPane.add(invoiceCustomerPhoneLabel, 0, 3);
        invoiceInfoGridPane.add(invoiceCustomerPhoneTextField, 1, 3);
        invoiceInfoGridPane.add(invoiceArrivalDayLabel, 0, 4);
        invoiceInfoGridPane.add(invoiceArrivalDayTextField, 1, 4);
        invoiceInfoGridPane.add(invoiceDepartureDayLabel, 0, 5);
        invoiceInfoGridPane.add(invoiceDepartureDayTextField, 1, 5);
        invoiceInfoGridPane.add(invoiceDayAmountLabel, 0, 6);
        invoiceInfoGridPane.add(invoiceDayAmountTextField, 1, 6);
        invoiceInfoGridPane.add(invoiceCostPerNightLabel, 2, 6);
        invoiceInfoGridPane.add(invoiceCostPerNightTextField, 3, 6);
        invoiceInfoGridPane.add(invoiceCostLabel, 0, 7);
        invoiceInfoGridPane.add(invoiceCostTextField, 1, 7);
        invoiceInfoGridPane.add(invoiceDueDayLabel, 0, 8);
        invoiceInfoGridPane.add(invoiceDueDayTextField, 1, 8);

        invoiceInfo.getChildren().addAll(invoiceInfoBackButton, invoiceInfoGridPane);
        invoiceInfoGridPane.relocate(30,50);
        invoiceInfoBackButton.relocate(10,10);
        mainPane.getChildren().add(invoiceInfo);
        invoiceInfo.setVisible(false);


        /*
        Raportit
         */
        Pane reports = new Pane();
        GridPane reportOptionGridPane = new GridPane();
        GridPane allCottagesReportGridPane = new GridPane();
        GridPane chosenCottageReportGridPane = new GridPane();

        reportOptionGridPane.setVgap(15);
        reportOptionGridPane.setHgap(15);

        allCottagesReportGridPane.setVgap(10);
        allCottagesReportGridPane.setHgap(10);

        chosenCottageReportGridPane.setVgap(10);
        chosenCottageReportGridPane.setHgap(10);

        //raportin elementit, kun valittuna kaikki mökit
        Label allReservationsLabel = new Label("Kokonaisvaraukset:");
        Label customerAmountLabel = new Label("Uusien asiakkaiden määrä:");
        Label returningCustomersLabel = new Label("Palaavien asiakkaiden määrä:");
        Label reservationLengthLabel = new Label("Keskimääräinen varauksen pituus:");
        Label cottageUsageLabel = new Label("Mökkien käyttöaste:");
        Label incomeLabel = new Label("Kokonaistulot:");

        TextField allReservationsTextField = new TextField();
        TextField customerAmountTextField = new TextField();
        TextField returningCustomersTextField = new TextField();
        TextField reservationLengthTextField = new TextField();
        TextField cottageUsageTextField = new TextField();
        TextField incomeTextField = new TextField();

        //tekstikentät eivät ole muokattavissa
        allReservationsTextField.setEditable(false);
        customerAmountTextField.setEditable(false);
        returningCustomersTextField.setEditable(false);
        reservationLengthTextField.setEditable(false);
        cottageUsageTextField.setEditable(false);
        incomeTextField.setEditable(false);

        allCottagesReportGridPane.add(allReservationsLabel, 0, 0);
        allCottagesReportGridPane.add(allReservationsTextField, 1, 0);
        allCottagesReportGridPane.add(customerAmountLabel, 0, 1);
        allCottagesReportGridPane.add(customerAmountTextField, 1, 1);
        allCottagesReportGridPane.add(returningCustomersLabel, 0, 2);
        allCottagesReportGridPane.add(returningCustomersTextField, 1, 2);
        allCottagesReportGridPane.add(reservationLengthLabel, 0, 3);
        allCottagesReportGridPane.add(reservationLengthTextField, 1, 3);
        allCottagesReportGridPane.add(cottageUsageLabel, 0, 4);
        allCottagesReportGridPane.add(cottageUsageTextField, 1, 4);
        allCottagesReportGridPane.add(incomeLabel, 0, 5);
        allCottagesReportGridPane.add(incomeTextField, 1, 5);

        //raportin elementit, kun valittuna tietty mökki
        Label cottageReservationsLabel = new Label("Varausten määrä:");
        Label cottageReservationLengthLabel = new Label("Keskimääräinen varauksen pituus:");
        Label cottageChosenUsageLabel = new Label("Mökin käyttöaste:");
        Label cottageIncomeLabel = new Label("Kokonaistulot:");

        TextField cottageReservationsTextField = new TextField();
        TextField cottageReservationLengthTextField = new TextField();
        TextField cottageChosenUsageTextField = new TextField();
        TextField cottageIncomeTextField = new TextField();

        cottageReservationsTextField.setEditable(false);
        cottageReservationLengthTextField.setEditable(false);
        cottageChosenUsageTextField.setEditable(false);
        cottageIncomeTextField.setEditable(false);

        chosenCottageReportGridPane.add(cottageReservationsLabel, 0, 0);
        chosenCottageReportGridPane.add(cottageReservationsTextField, 1, 0);
        chosenCottageReportGridPane.add(cottageReservationLengthLabel, 0, 1);
        chosenCottageReportGridPane.add(cottageReservationLengthTextField, 1, 1);
        chosenCottageReportGridPane.add(cottageChosenUsageLabel, 0, 2);
        chosenCottageReportGridPane.add(cottageChosenUsageTextField, 1, 2);
        chosenCottageReportGridPane.add(cottageIncomeLabel, 0, 3);
        chosenCottageReportGridPane.add(cottageIncomeTextField, 1, 3);

        chosenCottageReportGridPane.setVisible(false);

        //muut elementit
        DatePicker reportStartDatePicker = new DatePicker();
        DatePicker reportEndDatePicker = new DatePicker();

        Label reportStartLabel = new Label("Valitse ajanjakson alkupäivä:");
        Label reportEndLabel = new Label("Valitse ajanjakson loppupäivä:");

        ToggleGroup reportOptionToggleGroup = new ToggleGroup();

        Button reportBackButton = new Button("Takaisin");

        ComboBox<String> cottagesComboBox = new ComboBox<>(cottageList);

        RadioButton reportAllRadioButton = new RadioButton();
        reportAllRadioButton.setToggleGroup(reportOptionToggleGroup);
        reportAllRadioButton.setSelected(true);
        reportAllRadioButton.setText("Kaikki");
        RadioButton reportCottageRadioButton = new RadioButton();
        reportCottageRadioButton.setToggleGroup(reportOptionToggleGroup);
        reportCottageRadioButton.setText("Tietty mökki");

        //riippuen kumpi vaihtoehto on valittuna, näytetään joko raportti kaikista mökeistä tai tietystä mökistä
        reportAllRadioButton.setOnAction(e->{
            if(reportAllRadioButton.isSelected()){
                chosenCottageReportGridPane.setVisible(false);
                allCottagesReportGridPane.setVisible(true);
            }
        });

        reportCottageRadioButton.setOnAction(e->{
            if(reportCottageRadioButton.isSelected()){
                chosenCottageReportGridPane.setVisible(true);
                allCottagesReportGridPane.setVisible(false);
            }
        });


        reportOptionGridPane.add(reportStartLabel, 0, 0);
        reportOptionGridPane.add(reportStartDatePicker, 1, 0);
        reportOptionGridPane.add(reportEndLabel, 0, 1);
        reportOptionGridPane.add(reportEndDatePicker, 1, 1);
        reportOptionGridPane.add(reportAllRadioButton, 0, 2);
        reportOptionGridPane.add(reportCottageRadioButton, 1, 2);
        reportOptionGridPane.add(cottagesComboBox, 1, 3);

        reports.getChildren().addAll(reportOptionGridPane, allCottagesReportGridPane, chosenCottageReportGridPane, reportBackButton);
        reportOptionGridPane.relocate(30,50);
        reportBackButton.relocate(10,10);
        chosenCottageReportGridPane.relocate(500,50);
        allCottagesReportGridPane.relocate(500,50);

        mainPane.getChildren().add(reports);
        reports.setVisible(false);



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
            fetchCustomerInfoFromID(fetchSelectedCustomerId(1));
        });
        customer2Button.setOnAction(e->{
            customers.setVisible(false);
            customerInfo.setVisible(true);
            fetchCustomerInfoFromID(fetchSelectedCustomerId(2));
        });
        customer3Button.setOnAction(e->{
            customers.setVisible(false);
            customerInfo.setVisible(true);
            fetchCustomerInfoFromID(fetchSelectedCustomerId(3));
        });
        customer4Button.setOnAction(e->{
            customers.setVisible(false);
            customerInfo.setVisible(true);
            fetchCustomerInfoFromID(fetchSelectedCustomerId(4));
        });
        customer5Button.setOnAction(e->{
            customers.setVisible(false);
            customerInfo.setVisible(true);
            fetchCustomerInfoFromID(fetchSelectedCustomerId(5));
        });
        customer6Button.setOnAction(e->{
            customers.setVisible(false);
            customerInfo.setVisible(true);
            fetchCustomerInfoFromID(fetchSelectedCustomerId(6));
        });
        customer7Button.setOnAction(e->{
            customers.setVisible(false);
            customerInfo.setVisible(true);
            fetchCustomerInfoFromID(fetchSelectedCustomerId(7));
        });
        customer8Button.setOnAction(e->{
            customers.setVisible(false);
            customerInfo.setVisible(true);
            fetchCustomerInfoFromID(fetchSelectedCustomerId(8));
        });
        customer9Button.setOnAction(e->{
            customers.setVisible(false);
            customerInfo.setVisible(true);
            fetchCustomerInfoFromID(fetchSelectedCustomerId(9));
        });

        addCustomerButton.setOnAction(e->{
            resetCustomerInfoTextFields();
            customers.setVisible(false);
            customerEdit.setVisible(true);
            editCustomer = false;
        });

        customerInfoBackButton.setOnAction(e->{
            customerInfo.setVisible(false);
            customers.setVisible(true);
        });

        confirmCustomerEditButton.setOnAction(e->{
            customerEdit.setVisible(false);
            customerInfo.setVisible(true);
            confirmCustomerInfo();
        });

        changeCustomerInfoButton.setOnAction(e->{
            customerInfo.setVisible(false);
            customerEdit.setVisible(true);
            editCustomer = true;
            setCustomerToBeChanged();
        });

        acceptRemoveButton.setOnAction(e->{
            confirmRemoveCustomer.setVisible(false);
            customers.setVisible(true);
            removeCustomer();
        });

        cancelRemoveButton.setOnAction(e->{
            confirmRemoveCustomer.setVisible(false);
            customerInfo.setVisible(true);
        });

        removeCustomerButton.setOnAction(e->{
            customerInfo.setVisible(false);
            confirmRemoveCustomer.setVisible(true);
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

        createReservationButton.setOnAction(e->{
            cottageInfo.setVisible(false);
            reservationEdit.setVisible(true);
        });

        editReservationButton.setOnAction(e->{
            cottageInfo.setVisible(false);
            reservationEdit.setVisible(true);
        });

        reservationEditBackButton.setOnAction(e->{
            reservationEdit.setVisible(false);
            cottageInfo.setVisible(true);
        });

        editCottageInfoButton.setOnAction(e->{
            cottageInfo.setVisible(false);
            addCottage.setVisible(true);
        });

        addCottageBackButton.setOnAction(e->{
            addCottage.setVisible(false);
            cottageInfo.setVisible(true);
        });

        invoiceBackButton.setOnAction(e->{
            invoices.setVisible(false);
            mainMenu.setVisible(true);
        });

        invoiceInfoButton.setOnAction(e->{
            invoices.setVisible(false);
            invoiceInfo.setVisible(true);
        });

        invoiceInfoBackButton.setOnAction(e->{
            invoiceInfo.setVisible(false);
            invoices.setVisible(true);
        });

        invoicesButton.setOnAction(e->{
            mainMenu.setVisible(false);
            invoices.setVisible(true);
        });

        reportBackButton.setOnAction(e->{
            reports.setVisible(false);
            mainMenu.setVisible(true);
        });

        reportsButton.setOnAction(e->{
            mainMenu.setVisible(false);
            reports.setVisible(true);
        });


        setCustomersPage(0);

        /*
        scenen alustus
        */
        Scene scene = new Scene(mainPane, 1024, 768);
        stage.setScene(scene);
        stage.setTitle("Mökki Manager Pro");
        stage.show();
    }
}
