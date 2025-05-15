package oma.mokkipro;
import dao.AsiakasDAO;
import dao.LaskuDAO;
import dao.MokkiDAO;
import dao.VarausDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //listat varauksista ja asiakkaista käyttöliittymää varten
    private ObservableList<String> invoicesList = FXCollections.observableArrayList();
    private ObservableList<String> customersList = FXCollections.observableArrayList();
    private ObservableList<String> cottageList = FXCollections.observableArrayList();
    private ObservableList<String> reservationsList = FXCollections.observableArrayList();
    private ArrayList<String> customersIDList = new ArrayList<>();
    private ArrayList<String> cottageIDList = new ArrayList<>();
    private ArrayList<String> reservationIDList = new ArrayList<>();

    private int allReservationsCount = 0;


    //käyttöliittymän elementtejä, asiakas- ja mökki -näkymää varten
    private GridPane customer1GridPane, customer2GridPane, customer3GridPane, customer4GridPane, customer5GridPane,
            customer6GridPane, customer7GridPane, customer8GridPane, customer9GridPane,
            cottage1GridPane, cottage2GridPane, cottage3GridPane, cottage4GridPane, cottage5GridPane,
            cottage6GridPane, cottage7GridPane, cottage8GridPane, cottage9GridPane;

    private Text customer1NameText, customer2NameText, customer3NameText, customer4NameText, customer5NameText,
            customer6NameText, customer7NameText, customer8NameText, customer9NameText,
            cottage1NameText, cottage2NameText, cottage3NameText, cottage4NameText, cottage5NameText,
            cottage6NameText, cottage7NameText, cottage8NameText, cottage9NameText, cottageNameText;

    //tämänhetkinen sivu, jolla asiakas -näkymässä ollaan
    private int currentCustomerPage, currentCottagePage = 0;

    private ListView<String> customersListView, reservationsListView;

    //asiakkaan tietojen tarkastelu-näkymää varten
    private Text customerNameText;
    private TextArea customerInfoTextArea, cottageInfoTextArea, descriptionTextArea, addressTextArea;

    private TextField customerNameTextField, customerPhoneTextField, customerEmailTextField, customerTypeTextField,
    cottageNameTextField, pricePerNightTextField, capacityTextField, allReservationsTextField, customerAmountTextField,
    returningCustomersTextField, reservationLengthTextField, cottageUsageTextField, incomeTextField,
    cottageReservationsTextField, cottageReservationLengthTextField, cottageChosenUsageTextField, cottageIncomeTextField;

    private DatePicker arrivalDayDatePicker, departureDayDatePicker, reportStartDatePicker, reportEndDatePicker;

    private RadioButton reportAllRadioButton;

    private ComboBox<String> cottagesComboBox;

    private TextField invoiceIDTextField;
    private TextField invoiceCottageNameTextField;
    private TextField invoiceCustomerNameTextField;
    private TextField invoiceCustomerPhoneTextField;
    private TextField invoiceArrivalDayTextField;
    private TextField invoiceDepartureDayTextField;
    private TextField invoiceDayAmountTextField;
    private TextField invoiceCostPerNightTextField;
    private TextField invoiceCostTextField;
    private TextField invoiceDueDayTextField;

    private boolean editCustomer, editCottage, editReservation, createCustomerFromReservations;

    //tällähetkellä valittu asiakas/mökki/lasku, jota tarkastellaan ja muokataan
    private Asiakas currentCustomer;
    private Mokki currentCottage;
    private Lasku currentInvoice;
    private Varaus currentReservation;


    /**
     * Asettaa muuta asiakkaan tietoja -näkymän elementit nykyisen asiakkaan tietojen mukaisiksi
     */
    private void setCustomerToBeChanged(){
        customerNameTextField.setText(currentCustomer.getNimi());
        customerPhoneTextField.setText(currentCustomer.getPuhelin());
        customerEmailTextField.setText(currentCustomer.getSposti());
        customerTypeTextField.setText(currentCustomer.getAsiakastyyppi());
    }


    /**
     * Asettaa muuta mökin tietoja -näkymän elementit nykyisen mökin tietojen mukaisiksi
     */
    private void setCottageToBeChanged(){
        cottageNameTextField.setText(currentCottage.getNimi());
        addressTextArea.setText(currentCottage.getOsoite());
        descriptionTextArea.setText(currentCottage.getKuvaus());
        pricePerNightTextField.setText(String.valueOf(currentCottage.getHintaPerYö()));
        capacityTextField.setText(String.valueOf(currentCottage.getKapasiteetti()));
    }

    /**
     * Asettaa muuta varauksen tietoja -näkymän elementit nykyisen varauksen tietojen mukaisiksi
     */
    private void setReservationToBeChanged(){
        arrivalDayDatePicker.setValue(currentReservation.getVarauksenAlku());
        departureDayDatePicker.setValue(currentReservation.getVarauksenLoppu());
        int customerIndex = customersIDList.indexOf(String.valueOf(currentReservation.getAsiakasId()));
        System.out.println("asiakas: " + customersList.get(customerIndex));
        customersListView.getSelectionModel().select(customerIndex);
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
     * Asettaa mökin tiedot-näkymän elementit tyhjiksi.
     */
    private void resetCottageInfoTextFields(){
        cottageNameTextField.setText("");
        addressTextArea.setText("");
        descriptionTextArea.setText("");
        pricePerNightTextField.setText("");
        capacityTextField.setText("");
    }


    /**
     * Asettaa varauksen tiedot-näkymän elementit tyhjiksi.
     */
    private void resetReservationInfoTextFields(){
        arrivalDayDatePicker.getEditor().clear();
        departureDayDatePicker.getEditor().clear();
        if (!customersListView.getItems().isEmpty()) {
            customersListView.getSelectionModel().select(0);
        }
    }

    /**
     * Päivittää mökin varausten listan mökin tiedot -näkymässä
     */
    private void refreshReservationsList(){
        //tyhjennä lista
        reservationsList.clear();
        reservationIDList.clear();
        VarausDAO varausDAO = new VarausDAO();
        //hae varaukset
        List<Varaus> cottagereservationsList = varausDAO.haeVarauksetMokille(currentCottage.getMokkiId());
        //lisää varaukset listoihin
        for(Varaus r:cottagereservationsList){
            reservationsList.add(r.getVarauksenAlku() + " - " + r.getVarauksenLoppu());
            reservationIDList.add(String.valueOf(r.getVarausId()));
        }
    }


    /**
     * Hakee tietokannasta laskujen ID:t ja eräpäivät listaan
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
            cottageIDList.add(String.valueOf(m.getMokkiId()));
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
     * Hakee tietyn ID:n omaavan mökin tiedot tietokannasta, ja asettaa ne mökin tiedot -näkymän elementteihin.
     * @param cottageID haettavan mökin ID
     */
    private void fetchCottageInfoFromID(int cottageID){
        MokkiDAO mokkiDAO = new MokkiDAO();
        Mokki m = mokkiDAO.haeMokkiIdlla(cottageID);
        //asettaa nykyisen mökin
        currentCottage = m;
        cottageNameText.setText("Mökki: " + m.getNimi());
        //muuttaa käyttöliittymän elementtejä
        cottageInfoTextArea.setText("Mökin nimi: " + m.getNimi() +  "\nOsoite: " + m.getOsoite() + "\nKuvaus: " +
                m.getKuvaus() + "\nHinta per yö: " + m.getHintaPerYö() + "\nKapasiteetti: " + m.getKapasiteetti());
    }

    /**
     * Hakee tietyn ID:n omaavan varauksen tiedot tietokannasta, ja asettaa ne varauksen tiedot -näkymän elementteihin.
     * @param reservationID haettavan varauksen ID
     */
    private void fetchReservationInfoFromID(int reservationID){
        VarausDAO varausDAO = new VarausDAO();
        Varaus r = varausDAO.haeVarausIdlla(reservationID);
        //asettaa nykyisen varauksen
        System.out.println(r.getAsiakasId());
        currentReservation = r;
        //muuttaa käyttöliittymän elementtejä
        setReservationToBeChanged();
    }

    /**
     * Hakee mökit -näkymässä tietyn indeksin mökin ID:n. Palauttaa ID:n kokonaislukuna.
     * @param selectedCottageIndex valitun mökin indeksi
     * @return mökin ID kokonaislukuna
     */
    private int fetchSelectedCottageId(int selectedCottageIndex){
        String ID = cottageIDList.get(selectedCottageIndex + 9*currentCottagePage - 1);
        return Integer.parseInt(ID);
    }


    private void fetchAllReservationsCount(){
        VarausDAO varausDAO = new VarausDAO();
        List<Varaus> reservations = varausDAO.haeKaikkiVaraukset();
        for(Varaus r:reservations){
            allReservationsCount++;
        }
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


    private void getReportInfo(){
        VarausDAO varausDAO = new VarausDAO();
        if(reportAllRadioButton.isSelected()){
            //hae raportin tiedot
            Raportointi report = varausDAO.haeYhteenvetoKaikista(reportStartDatePicker.getValue(), reportEndDatePicker.getValue());
            //asetetaan käyttöliittymän elementteihin
            allReservationsTextField.setText(String.valueOf(report.getVaraustenMaara()));
            customerAmountTextField.setText(String.valueOf(report.getPalaavatAsiakkaat()));
            returningCustomersTextField.setText(String.valueOf(report.getPalaavatAsiakkaat()));
            reservationLengthTextField.setText(String.valueOf(report.getKeskimaarainenPituus()));
            cottageUsageTextField.setText(report.getKayttoasteProsentti() + "%");
            incomeTextField.setText(String.valueOf(report.getKokonaistulot()));
        } else {
            //hae raportin tiedot
            int cottageID = Integer.parseInt(cottageIDList.get(cottagesComboBox.getSelectionModel().getSelectedIndex()));
            Raportointi report = varausDAO.haeYhteenvetoMokille(cottageID, reportStartDatePicker.getValue(), reportEndDatePicker.getValue());
            //asetetaan käyttöliittymän elementteihin
            cottageReservationsTextField.setText(String.valueOf(report.getVaraustenMaara()));
            cottageReservationLengthTextField.setText(String.valueOf(report.getKeskimaarainenPituus()));
            cottageChosenUsageTextField.setText(report.getKayttoasteProsentti() + "%");
            cottageIncomeTextField.setText(String.valueOf(report.getKokonaistulot()));
        }
    }


    /**
     * Asettaa laskujen tarkastelun ListView-elementin valitun laskun tarkasteltavaksi laskuksi.
     * @param listViewItem ListView:n valittu String
     */
    private void setCurrentInvoice(String listViewItem){
        //laskun id listview alkioista
        String invoiceID = listViewItem.substring(0,listViewItem.indexOf(","));

        LaskuDAO laskuDAO = new LaskuDAO();
        VarausDAO varausDAO = new VarausDAO();
        MokkiDAO mokkiDAO = new MokkiDAO();
        AsiakasDAO asiakasDAO = new AsiakasDAO();

        //hae lasku
        currentInvoice = laskuDAO.haeLaskuIdlla(Integer.parseInt(invoiceID));

        //hae varaus, mökki ja asiakas
        Varaus reservation = varausDAO.haeVarausIdlla(currentInvoice.getVarausId());
        Mokki cottage = mokkiDAO.haeMokkiIdlla(reservation.getMokkiId());
        Asiakas customer = asiakasDAO.haeAsiakasIdlla(reservation.getAsiakasId());

        //aseta tiedot
        invoiceIDTextField.setText(String.valueOf(currentInvoice.getLaskuId()));
        invoiceCottageNameTextField.setText(String.valueOf(cottage.getNimi()));
        invoiceCustomerNameTextField.setText(customer.getNimi());
        invoiceCustomerPhoneTextField.setText(customer.getPuhelin());
        invoiceArrivalDayTextField.setText(reservation.getVarauksenAlku().toString());
        invoiceDepartureDayTextField.setText(reservation.getVarauksenLoppu().toString());
        invoiceDayAmountTextField.setText(String.valueOf(Math.abs(ChronoUnit.DAYS.between(reservation.getVarauksenAlku(),
                reservation.getVarauksenLoppu()) + 1)));
        invoiceCostPerNightTextField.setText(String.valueOf(cottage.getHintaPerYö()));
        invoiceCostTextField.setText(String.valueOf(Math.abs(ChronoUnit.DAYS.between(reservation.getVarauksenAlku(),
                reservation.getVarauksenLoppu()) + 1)*cottage.getHintaPerYö()));
        invoiceDueDayTextField.setText(String.valueOf(currentInvoice.getErapaiva()));
    }

    private void confirmReservationInfo(){
        VarausDAO varausDAO = new VarausDAO();

        //jos ei muokata varausta, niin luodaan uusi varaus
        if(!editReservation){

            //hae asiakkaan id
            int customerID = Integer.parseInt(customersIDList.get(customersListView.getSelectionModel().getSelectedIndex()));

            //luo varaus
            Varaus reservation = new Varaus(allReservationsCount + 1, customerID, currentCottage.getMokkiId(),
                    arrivalDayDatePicker.getValue(), departureDayDatePicker.getValue(), "", LocalDateTime.now());
            //lisää varaus tietokantaan
            varausDAO.lisaaVaraus(reservation);

            //lisätään lasku tietokantaan
            Lasku invoice = new Lasku(invoicesList.size()+1, reservation.getVarausId(),
                    currentCottage.getHintaPerYö()*ChronoUnit.DAYS.between(reservation.getVarauksenAlku(), reservation.getVarauksenLoppu()),
                    LocalDate.now().plusWeeks(2), LocalDateTime.now());
            LaskuDAO laskuDAO = new LaskuDAO();
            laskuDAO.lisaaLasku(invoice);
            invoicesList.add(invoice.getLaskuId() + ", " + invoice.getErapaiva());

        } else {
            currentReservation.setAsiakasId(Integer.parseInt(customersIDList.get(customersListView.getSelectionModel().getSelectedIndex())));
            currentReservation.setVarauksenAlku(arrivalDayDatePicker.getValue());
            currentReservation.setVarauksenLoppu(departureDayDatePicker.getValue());
            currentReservation.setVarausAika(LocalDateTime.now());
            varausDAO.paivitaVaraus(currentReservation);
        }

        refreshReservationsList();
    }

    /**
     * Muuttaa asiakkaan tietoja tietokannassa. Ottaa uudet arvot käyttöliittymästä.
     */
    private void confirmCustomerInfo(){

        if(!editCustomer){
            currentCustomer = new Asiakas(customersIDList.size()+1, "", "", "", "");
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
     * Muuttaa mökin tietoja tietokannassa. Ottaa uudet arvot käyttöliittymästä.
     */
    private void confirmCottageInfo(){

        if(!editCottage){
            currentCottage = new Mokki(cottageIDList.size()+1, "", "", "", 0, 0, true);
        }

        //nykyisen mökin tietoja muutetaan
        currentCottage.setNimi(cottageNameTextField.getText());
        currentCottage.setOsoite(addressTextArea.getText());
        currentCottage.setKuvaus(descriptionTextArea.getText());
        currentCottage.setHintaPerYö(Double.parseDouble(pricePerNightTextField.getText()));
        currentCottage.setKapasiteetti(Integer.parseInt(capacityTextField.getText()));


        //uusi mokkiDAO
        MokkiDAO mokkiDAO = new MokkiDAO();

        if(editCottage){
            //päivitetään mökki
            mokkiDAO.paivitaMokki(currentCottage);

            //päivitetään mökkien nimilista
            int cottageListIndex = cottageIDList.indexOf(String.valueOf(currentCottage.getMokkiId()));
            cottageList.remove(cottageListIndex);
            cottageList.add(cottageListIndex, currentCottage.getNimi());

        } else {
            //lisätään mökki
            mokkiDAO.lisaaMokki(currentCottage);
            cottageIDList.add(String.valueOf(currentCottage.getMokkiId()));
            cottageList.add(currentCottage.getNimi());
        }

        //päivitetään mökki -näkymä
        setCottagePage(currentCottagePage);

        //päivitetään mökin tiedot -näkymän elementit

        cottageInfoTextArea.setText("Mökin nimi: " + currentCottage.getNimi() +  "\nOsoite: "
                + currentCottage.getOsoite() + "\nKuvaus: " + currentCottage.getKuvaus() + "\nHinta per yö: "
                + currentCottage.getHintaPerYö() + "\nKapasiteetti: " + currentCottage.getKapasiteetti());
        //System.out.println("mökki päivitetty!");
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
     * Poistaa nykyisen mökin tietokannasta.
     */
    private void removeCottage(){
        int cottageListIndex = cottageIDList.indexOf(String.valueOf(currentCottage.getMokkiId()));
        cottageList.remove(cottageListIndex);
        cottageIDList.remove(cottageListIndex);
        MokkiDAO mokkiDAO = new MokkiDAO();
        mokkiDAO.poistaMokki(currentCottage.getMokkiId());
        setCottagePage(currentCottagePage);
        System.out.println("mökki poistettu...");
    }


    /**
     * Poistaa nykyisen varauksen tietokannasta.
     */
    private void removeReservation(){
        allReservationsCount--;
        VarausDAO varausDAO = new VarausDAO();
        varausDAO.poistaVaraus(Integer.parseInt(reservationIDList.get(reservationsListView.getSelectionModel().getSelectedIndex())));
        refreshReservationsList();
        System.out.println("varaus poistettu...");
    }

    /**
     * Asettaa asiakas -näkymän asiakkaiden sivun annettuun lukuun.
     * @param page uusi sivunumero kokonaislukuna
     */
    private void setCustomersPage(int page) {
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
        for (int i = page * 9 + 1; i < (page + 1) * 9 + 1; i++) {
            //jos indeksin jakojäännös on 1, ja listassa on tarpeeksi asiakkaita, laitetaan sivun ensimmäinen
            //asiakas täksi asiakkaaksi, ja näytetään asiakaspane. sama muille kahdeksalle
            if (i % 9 == 1 && customersList.size() >= i) {
                customer1NameText.setText(customersList.get(i - 1));
                customer1GridPane.setVisible(true);
            }
            if (i % 9 == 2 && customersList.size() >= i) {
                customer2NameText.setText(customersList.get(i - 1));
                customer2GridPane.setVisible(true);
            }
            if (i % 9 == 3 && customersList.size() >= i) {
                customer3NameText.setText(customersList.get(i - 1));
                customer3GridPane.setVisible(true);
            }
            if (i % 9 == 4 && customersList.size() >= i) {
                customer4NameText.setText(customersList.get(i - 1));
                customer4GridPane.setVisible(true);
            }
            if (i % 9 == 5 && customersList.size() >= i) {
                customer5NameText.setText(customersList.get(i - 1));
                customer5GridPane.setVisible(true);
            }
            if (i % 9 == 6 && customersList.size() >= i) {
                customer6NameText.setText(customersList.get(i - 1));
                customer6GridPane.setVisible(true);
            }
            if (i % 9 == 7 && customersList.size() >= i) {
                customer7NameText.setText(customersList.get(i - 1));
                customer7GridPane.setVisible(true);
            }
            if (i % 9 == 8 && customersList.size() >= i) {
                customer8NameText.setText(customersList.get(i - 1));
                customer8GridPane.setVisible(true);
            }
            if (i % 9 == 0 && customersList.size() >= i) {
                customer9NameText.setText(customersList.get(i - 1));
                customer9GridPane.setVisible(true);
            }
        }
    }



        /**
         * Asettaa asiakas -näkymän asiakkaiden sivun annettuun lukuun.
         * @param cottagePage uusi sivunumero kokonaislukuna
         */
        private void setCottagePage(int cottagePage){
            currentCottagePage = cottagePage;
            //ensin kaikki asikaspanet piiloon
            cottage1GridPane.setVisible(false);
            cottage2GridPane.setVisible(false);
            cottage3GridPane.setVisible(false);
            cottage4GridPane.setVisible(false);
            cottage5GridPane.setVisible(false);
            cottage6GridPane.setVisible(false);
            cottage7GridPane.setVisible(false);
            cottage8GridPane.setVisible(false);
            cottage9GridPane.setVisible(false);

            //käydään läpi sivun mökit, indeksistä sivunnumero * 6 + 1 indeksiin (sivunnumero + 1) * 6 + 1 asti
            for (int i = cottagePage * 9 + 1; i < (cottagePage + 1) * 9 + 1; i++) {
                //jos indeksin jakojäännös on 1, ja listassa on tarpeeksi mökkejä, laitetaan sivun ensimmäinen
                //mökki täksi mökiksi, ja näytetään mökitpane. sama muille kuudelle
                if (i % 9 == 1 && cottageList.size() >= i) {
                    cottage1NameText.setText(cottageList.get(i - 1));
                    cottage1GridPane.setVisible(true);
                }
                if (i % 9 == 2 && cottageList.size() >= i) {
                    cottage2NameText.setText(cottageList.get(i - 1));
                    cottage2GridPane.setVisible(true);
                }
                if (i % 9 == 3 && cottageList.size() >= i) {
                    cottage3NameText.setText(cottageList.get(i - 1));
                    cottage3GridPane.setVisible(true);
                }
                if (i % 9 == 4 && cottageList.size() >= i) {
                    cottage4NameText.setText(cottageList.get(i - 1));
                    cottage4GridPane.setVisible(true);
                }
                if (i % 9 == 5 && cottageList.size() >= i) {
                    cottage5NameText.setText(cottageList.get(i - 1));
                    cottage5GridPane.setVisible(true);
                }
                if (i % 9 == 6 && cottageList.size() >= i) {
                    cottage6NameText.setText(cottageList.get(i - 1));
                    cottage6GridPane.setVisible(true);
                }
                if (i % 9 == 7 && cottageList.size() >= i) {
                    cottage7NameText.setText(cottageList.get(i - 1));
                    cottage7GridPane.setVisible(true);
                }
                if (i % 9 == 8 && cottageList.size() >= i) {
                    cottage8NameText.setText(cottageList.get(i - 1));
                    cottage8GridPane.setVisible(true);
                }
                if (i % 9 == 0 && cottageList.size() >= i) {
                    cottage9NameText.setText(cottageList.get(i - 1));
                    cottage9GridPane.setVisible(true);
                }

            }
        }


    @Override
    public void start(Stage stage) {

        //hakee ensin tietokannasta varaukset ja asiakkaat
        fetchInvoiceIDs();
        fetchCustomerNames();
        fetchCottageNames();
        fetchAllReservationsCount();

        //pääpane
        Pane mainPane = new Pane();


        /*
        Päävalikko
         */

        Pane mainMenu = new Pane();
        GridPane mainMenuGridPane = new GridPane();

        //elementit
        Button cottagesButton = new Button("Mökit ja varaukset");
        Button customersButton = new Button("Asiakkaat");
        Button invoicesButton = new Button("Laskut");
        Button reportsButton = new Button("Raportit");
        Text titleText = new Text("Mökki Manager Pro");

        //isompi fontti
        titleText.setStyle("-fx-font: 32 arial;");

        cottagesButton.setPrefWidth(200);
        customersButton.setPrefWidth(200);
        invoicesButton.setPrefWidth(200);
        reportsButton.setPrefWidth(200);

        mainMenuGridPane.add(titleText, 0, 0);
        mainMenuGridPane.add(cottagesButton, 0, 1);
        mainMenuGridPane.add(customersButton, 0, 2);
        mainMenuGridPane.add(invoicesButton, 0, 3);
        mainMenuGridPane.add(reportsButton, 0, 4);

        mainMenuGridPane.setHgap(15);
        mainMenuGridPane.setVgap(15);

        mainMenu.getChildren().addAll(mainMenuGridPane);
        mainMenuGridPane.relocate(30, 50);

        mainPane.getChildren().add(mainMenu);
        mainMenu.setVisible(true);


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
        GridPane confirmRemoveCustomerGridPane = new GridPane();

        Text removeInfoText = new Text("Asiakkaan poistaminen on lopullista. \nHaluatko poistaa asiakkaan järjestelmästä?");

        Button cancelCustomerRemoveButton = new Button("Peruuta");
        Button acceptCustomerRemoveButton = new Button("Kyllä, poista asiakas");

        confirmRemoveCustomerGridPane.setHgap(30);
        confirmRemoveCustomerGridPane.setVgap(30);

        confirmRemoveCustomerGridPane.add(removeInfoText, 1, 0);
        confirmRemoveCustomerGridPane.add(cancelCustomerRemoveButton, 0,1);
        confirmRemoveCustomerGridPane.add(acceptCustomerRemoveButton, 2,1);

        confirmRemoveCustomer.getChildren().add(confirmRemoveCustomerGridPane);
        confirmRemoveCustomerGridPane.relocate(200,200);

        mainPane.getChildren().add(confirmRemoveCustomer);
        confirmRemoveCustomer.setVisible(false);

        /*
        Mökit
         */
        Pane cottages = new Pane();

        BorderPane cottagesBorderPane = new BorderPane();
        GridPane cottageSearchGridPane = new GridPane();
        GridPane cottagesGridPane = new GridPane();

        Pane cottage1Pane = new Pane();
        Pane cottage2Pane = new Pane();
        Pane cottage3Pane = new Pane();
        Pane cottage4Pane = new Pane();
        Pane cottage5Pane = new Pane();
        Pane cottage6Pane = new Pane();
        Pane cottage7Pane = new Pane();
        Pane cottage8Pane = new Pane();
        Pane cottage9Pane = new Pane();
        cottage1GridPane = new GridPane();
        cottage2GridPane = new GridPane();
        cottage3GridPane = new GridPane();
        cottage4GridPane = new GridPane();
        cottage5GridPane = new GridPane();
        cottage6GridPane = new GridPane();
        cottage7GridPane = new GridPane();
        cottage8GridPane = new GridPane();
        cottage9GridPane = new GridPane();


        //mökki 1

        cottage1NameText = new Text("Mökki 1");
        Button cottage1Button = new Button("Katso tietoja/varaa");

        Rectangle cottage1BGRectangle = new Rectangle(140, 80);
        cottage1BGRectangle.setFill(Color.WHITE);
        cottage1BGRectangle.setStroke(Color.GREY);
        cottage1GridPane.add(cottage1NameText, 0, 1);
        cottage1GridPane.add(cottage1Button, 0, 2);

        cottage1GridPane.setVgap(10);

        cottage1Pane.getChildren().addAll(cottage1BGRectangle, cottage1GridPane);
        cottage1GridPane.relocate(15, 0);
        cottagesGridPane.add(cottage1Pane, 0, 0);

       //mökki 2

        cottage2NameText = new Text("Mökki 2");
        Button cottage2Button = new Button("Katso tietoja/varaa");

        Rectangle cottage2BGRectangle = new Rectangle(140, 80);
        cottage2BGRectangle.setFill(Color.WHITE);
        cottage2BGRectangle.setStroke(Color.GREY);
        cottage2GridPane.add(cottage2NameText, 0, 1);
        cottage2GridPane.add(cottage2Button, 0, 2);

        cottage2GridPane.setVgap(10);

        cottage2Pane.getChildren().addAll(cottage2BGRectangle, cottage2GridPane);
        cottage2GridPane.relocate(15, 0);
        cottagesGridPane.add(cottage2Pane, 1, 0);


        //mökki 3

        cottage3NameText = new Text("Mökki 3");
        Button cottage3Button = new Button("Katso tietoja/varaa");

        Rectangle cottage3BGRectangle = new Rectangle(140, 80);
        cottage3BGRectangle.setFill(Color.WHITE);
        cottage3BGRectangle.setStroke(Color.GREY);
        cottage3GridPane.add(cottage3NameText, 0, 1);
        cottage3GridPane.add(cottage3Button, 0, 2);

        cottage3GridPane.setVgap(10);

        cottage3Pane.getChildren().addAll(cottage3BGRectangle, cottage3GridPane);
        cottage3GridPane.relocate(15, 0);
        cottagesGridPane.add(cottage3Pane, 2, 0);

        //mökki 4

        cottage4NameText = new Text("Mökki 4");
        Button cottage4Button = new Button("Katso tietoja/varaa");

        Rectangle cottage4BGRectangle = new Rectangle(140, 80);
        cottage4BGRectangle.setFill(Color.WHITE);
        cottage4BGRectangle.setStroke(Color.GREY);
        cottage4GridPane.add(cottage4NameText, 0, 1);
        cottage4GridPane.add(cottage4Button, 0, 2);

        cottage4GridPane.setVgap(10);

        cottage4Pane.getChildren().addAll(cottage4BGRectangle, cottage4GridPane);
        cottage4GridPane.relocate(15, 0);
        cottagesGridPane.add(cottage4Pane, 0, 1);

        //mökki 5

        cottage5NameText = new Text("Mökki 5");
        Button cottage5Button = new Button("Katso tietoja/varaa");

        Rectangle cottage5BGRectangle = new Rectangle(140, 80);
        cottage5BGRectangle.setFill(Color.WHITE);
        cottage5BGRectangle.setStroke(Color.GREY);
        cottage5GridPane.add(cottage5NameText, 0, 1);
        cottage5GridPane.add(cottage5Button, 0, 2);

        cottage5GridPane.setVgap(10);

        cottage5Pane.getChildren().addAll(cottage5BGRectangle, cottage5GridPane);
        cottage5GridPane.relocate(15, 0);
        cottagesGridPane.add(cottage5Pane, 1, 1);

        cottagesGridPane.setHgap(15);
        cottagesGridPane.setVgap(15);

        //mökki 6

        cottage6NameText = new Text("Mökki 6");
        Button cottage6Button = new Button("Katso tietoja/varaa");

        Rectangle cottage6BGRectangle = new Rectangle(140, 80);
        cottage6BGRectangle.setFill(Color.WHITE);
        cottage6BGRectangle.setStroke(Color.GREY);
        cottage6GridPane.add(cottage6NameText, 0, 1);
        cottage6GridPane.add(cottage6Button, 0, 2);

        cottage6GridPane.setVgap(10);

        cottage6Pane.getChildren().addAll(cottage6BGRectangle, cottage6GridPane);
        cottage6GridPane.relocate(15, 0);
        cottagesGridPane.add(cottage6Pane, 2, 1);

        //mökki 7

        cottage7NameText = new Text("Mökki 7");
        Button cottage7Button = new Button("Katso tietoja/varaa");

        Rectangle cottage7BGRectangle = new Rectangle(140, 80);
        cottage7BGRectangle.setFill(Color.WHITE);
        cottage7BGRectangle.setStroke(Color.GREY);
        cottage7GridPane.add(cottage7NameText, 0, 1);
        cottage7GridPane.add(cottage7Button, 0, 2);

        cottage7GridPane.setVgap(10);

        cottage7Pane.getChildren().addAll(cottage7BGRectangle, cottage7GridPane);
        cottage7GridPane.relocate(15, 0);
        cottagesGridPane.add(cottage7Pane, 0, 2);

        //mökki 8

        cottage8NameText = new Text("Mökki 8");
        Button cottage8Button = new Button("Katso tietoja/varaa");

        Rectangle cottage8BGRectangle = new Rectangle(140, 80);
        cottage8BGRectangle.setFill(Color.WHITE);
        cottage8BGRectangle.setStroke(Color.GREY);
        cottage8GridPane.add(cottage8NameText, 0, 1);
        cottage8GridPane.add(cottage8Button, 0, 2);

        cottage8GridPane.setVgap(10);

        cottage8Pane.getChildren().addAll(cottage8BGRectangle, cottage8GridPane);
        cottage8GridPane.relocate(15, 0);
        cottagesGridPane.add(cottage8Pane, 1, 2);

        //mökki 9

        cottage9NameText = new Text("Mökki 9");
        Button cottage9Button = new Button("Katso tietoja/varaa");

        Rectangle cottage9BGRectangle = new Rectangle(140, 80);
        cottage9BGRectangle.setFill(Color.WHITE);
        cottage9BGRectangle.setStroke(Color.GREY);
        cottage9GridPane.add(cottage9NameText, 0, 1);
        cottage9GridPane.add(cottage9Button, 0, 2);

        cottage9GridPane.setVgap(10);

        cottage9Pane.getChildren().addAll(cottage9BGRectangle, cottage9GridPane);
        cottage9GridPane.relocate(15, 0);
        cottagesGridPane.add(cottage9Pane, 2, 2);

        //mökkien haku
        TextField cottageSearchTextField = new TextField();
        Text cottageCountText = new Text("{henkilömäärä}");
        Button cottageSearchButton = new Button("Hae");

        Button previousCottagePageButton = new Button("Edellinen sivu");
        Button nextCottagePageButton = new Button("Seuraava sivu");

        previousCottagePageButton.setOnAction(e->{
            if(currentCottagePage>= 1){
                setCottagePage(currentCottagePage-1);
            }
        });

        nextCottagePageButton.setOnAction(e->{
            if(currentCottagePage + 1 <= cottageList.size()/6){
                setCottagePage(currentCottagePage + 1);
            }
        });

        Button cottagesBackButton = new Button("Takaisin");

        Button addCottageButton = new Button("Lisää mökki");

        cottageSearchGridPane.add(cottageSearchTextField, 0, 0);
        cottageSearchGridPane.add(cottageCountText, 1, 0);
        cottageSearchGridPane.add(cottageSearchButton, 2, 0);

        cottageSearchGridPane.setHgap(15);
        BorderPane.setMargin(cottagesGridPane, new Insets(15));

        cottagesBorderPane.setTop(cottageSearchGridPane);
        cottagesBorderPane.setCenter(cottagesGridPane);
        cottages.getChildren().addAll(cottagesBorderPane, cottagesBackButton,
        previousCottagePageButton, nextCottagePageButton, addCottageButton);
        cottagesBorderPane.relocate(30, 50);
        previousCottagePageButton.relocate(30, 400);
        nextCottagePageButton.relocate(450, 400);
        cottagesBackButton.relocate(10,10);
        addCottageButton.relocate(230, 400);

        mainPane.getChildren().add(cottages);
        cottages.setVisible(false);


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
        Button removeCottageButton = new Button("Poista mökki");

        cottageInfoButtonsGridPane.add(createReservationButton, 0,0);
        cottageInfoButtonsGridPane.add(editReservationButton, 0,1);
        cottageInfoButtonsGridPane.add(removeReservationButton, 0,2);
        cottageInfoButtonsGridPane.add(editCottageInfoButton, 0,3);
        cottageInfoButtonsGridPane.add(removeCottageButton, 0,4);

        cottageInfoButtonsGridPane.setHgap(15);
        cottageInfoButtonsGridPane.setVgap(15);


        cottageInfoTextArea = new TextArea("Mökin nimi: \nOsoite: \nKuvaus: \nHinta per yö: \nKapasiteetti: ");
        cottageInfoTextArea.setEditable(false);

        reservationsListView = new ListView<String>(reservationsList);

        Button cottageInfoBackButton = new Button("Takaisin");


        cottageInfoGridPane.add(cottageInfoTextArea, 0, 0);
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
        Mökin poiston varmistaminen
         */
        Pane confirmRemoveCottage = new Pane();
        GridPane confirmRemoveCottageGridPane = new GridPane();

        Text removeCottageInfoText = new Text("Mökin poistaminen on lopullista. \nHaluatko poistaa mökin järjestelmästä?");

        Button cancelCottageRemoveButton = new Button("Peruuta");
        Button acceptCottageRemoveButton = new Button("Kyllä, poista mökki");

        confirmRemoveCottageGridPane.setHgap(30);
        confirmRemoveCottageGridPane.setVgap(30);

        confirmRemoveCottageGridPane.add(removeCottageInfoText, 1, 0);
        confirmRemoveCottageGridPane.add(cancelCottageRemoveButton, 0,1);
        confirmRemoveCottageGridPane.add(acceptCottageRemoveButton, 2,1);

        confirmRemoveCottage.getChildren().add(confirmRemoveCottageGridPane);
        confirmRemoveCottageGridPane.relocate(200,200);

        mainPane.getChildren().add(confirmRemoveCottage);
        confirmRemoveCottage.setVisible(false);



        /*
        Varauksen hallinta
         */
        Pane reservationEdit = new Pane();
        GridPane reservationEditGridPane = new GridPane();

        cottageNameText = new Text("Mökin nimi");

        Text reservationEditInfoText = new Text("Valitse asiakas listasta tai luo uusi asiakastieto:");

        customersListView = new ListView<>(customersList);

        Button newCustomerButton = new Button("Uusi asiakas");

        Label arrivalDayLabel = new Label("Saapumispäivä:");
        Label departureDayLabel = new Label("Lähtöpäivä:");
        arrivalDayDatePicker = new DatePicker();
        departureDayDatePicker = new DatePicker();
        Button reservationEditBackButton = new Button("Takaisin");
        Button confirmReservationEditButton = new Button("Hyväksy");

        reservationEditGridPane.add(cottageNameText, 0, 0);
        reservationEditGridPane.add(reservationEditInfoText, 0, 1);
        reservationEditGridPane.add(customersListView, 0, 2);
        reservationEditGridPane.add(newCustomerButton, 0, 3);
        reservationEditGridPane.add(arrivalDayLabel, 0, 4);
        reservationEditGridPane.add(arrivalDayDatePicker, 1, 4);
        reservationEditGridPane.add(departureDayLabel, 0, 5);
        reservationEditGridPane.add(departureDayDatePicker, 1, 5);
        reservationEditGridPane.add(confirmReservationEditButton, 1, 6);

        reservationEditGridPane.setVgap(15);

        reservationEdit.getChildren().addAll(reservationEditGridPane, reservationEditBackButton);
        reservationEditGridPane.relocate(30,50);
        reservationEditBackButton.relocate(10,10);

        mainPane.getChildren().add(reservationEdit);
        reservationEdit.setVisible(false);


        /*
        Varauksen poiston varmistaminen
         */
        Pane confirmRemoveReservation = new Pane();
        GridPane confirmRemoveReservationGridPane = new GridPane();

        Text removeReservationInfoText = new Text("Varauksen poistaminen on lopullista. \nHaluatko poistaa varauksen järjestelmästä?");

        Button cancelReservationRemoveButton = new Button("Peruuta");
        Button acceptReservationRemoveButton = new Button("Kyllä, poista varaus");

        confirmRemoveReservationGridPane.setHgap(30);
        confirmRemoveReservationGridPane.setVgap(30);

        confirmRemoveReservationGridPane.add(removeReservationInfoText, 1, 0);
        confirmRemoveReservationGridPane.add(cancelReservationRemoveButton, 0,1);
        confirmRemoveReservationGridPane.add(acceptReservationRemoveButton, 2,1);

        confirmRemoveReservation.getChildren().add(confirmRemoveReservationGridPane);
        confirmRemoveReservationGridPane.relocate(200,200);

        mainPane.getChildren().add(confirmRemoveReservation);
        confirmRemoveReservation.setVisible(false);



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

        cottageNameTextField = new TextField();
        addressTextArea = new TextArea();
        descriptionTextArea = new TextArea();
        pricePerNightTextField = new TextField();
        capacityTextField = new TextField();

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

        if(!invoicesListView.getItems().isEmpty()){
            invoicesListView.getSelectionModel().select(0);
        }

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

        invoiceIDTextField = new TextField();
        invoiceCottageNameTextField = new TextField();
        invoiceCustomerNameTextField = new TextField();
        invoiceCustomerPhoneTextField = new TextField();
        invoiceArrivalDayTextField = new TextField();
        invoiceDepartureDayTextField = new TextField();
        invoiceDayAmountTextField = new TextField();
        invoiceCostPerNightTextField = new TextField();
        invoiceCostTextField = new TextField();
        invoiceDueDayTextField = new TextField();

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

        allReservationsTextField = new TextField();
        customerAmountTextField = new TextField();
        returningCustomersTextField = new TextField();
        reservationLengthTextField = new TextField();
        cottageUsageTextField = new TextField();
        incomeTextField = new TextField();

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

        cottageReservationsTextField = new TextField();
        cottageReservationLengthTextField = new TextField();
        cottageChosenUsageTextField = new TextField();
        cottageIncomeTextField = new TextField();

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
        reportStartDatePicker = new DatePicker();
        reportEndDatePicker = new DatePicker();

        Label reportStartLabel = new Label("Valitse ajanjakson alkupäivä:");
        Label reportEndLabel = new Label("Valitse ajanjakson loppupäivä:");

        ToggleGroup reportOptionToggleGroup = new ToggleGroup();

        Button reportBackButton = new Button("Takaisin");
        Button confirmReportButton = new Button("Tee raportti");

        cottagesComboBox = new ComboBox<>(cottageList);

        reportAllRadioButton = new RadioButton();
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
        reportOptionGridPane.add(confirmReportButton, 0, 4);

        reports.getChildren().addAll(reportOptionGridPane, allCottagesReportGridPane, chosenCottageReportGridPane, reportBackButton);
        reportOptionGridPane.relocate(30,50);
        reportBackButton.relocate(10,10);
        chosenCottageReportGridPane.relocate(500,50);
        allCottagesReportGridPane.relocate(500,50);

        mainPane.getChildren().add(reports);
        reports.setVisible(false);



        //painikkeet


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
            createCustomerFromReservations = false;
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
            if(!createCustomerFromReservations){
                if(!editCustomer){
                    customers.setVisible(true);
                    setCustomersPage(currentCustomerPage);
                } else {
                    customerInfo.setVisible(true);
                }
            } else {
                reservationEdit.setVisible(true);
            }
            confirmCustomerInfo();
        });

        changeCustomerInfoButton.setOnAction(e->{
            customerInfo.setVisible(false);
            customerEdit.setVisible(true);
            editCustomer = true;
            setCustomerToBeChanged();
        });

        acceptCustomerRemoveButton.setOnAction(e->{
            confirmRemoveCustomer.setVisible(false);
            customers.setVisible(true);
            removeCustomer();
        });

        cancelCustomerRemoveButton.setOnAction(e->{
            confirmRemoveCustomer.setVisible(false);
            customerInfo.setVisible(true);
        });

        removeCustomerButton.setOnAction(e->{
            customerInfo.setVisible(false);
            confirmRemoveCustomer.setVisible(true);
        });

        removeCottageButton.setOnAction(e->{
            cottageInfo.setVisible(false);
            confirmRemoveCottage.setVisible(true);
        });

        cancelCottageRemoveButton.setOnAction(e->{
            confirmRemoveCottage.setVisible(false);
            cottageInfo.setVisible(true);
        });

        acceptCottageRemoveButton.setOnAction(e->{
            confirmRemoveCottage.setVisible(false);
            cottages.setVisible(true);
            removeCottage();
            setCottagePage(currentCottagePage);
        });

        customerEditBackButton.setOnAction(e->{
            customerEdit.setVisible(false);
            if(!createCustomerFromReservations){
                if(!editCustomer){
                    customers.setVisible(true);
                } else {
                    customerInfo.setVisible(true);
                }
            } else{
                reservationEdit.setVisible(true);
            }
        });

        customersBackButton.setOnAction(e->{
            customers.setVisible(false);
            mainMenu.setVisible(true);
        });

        cottageInfoBackButton.setOnAction(e->{
            cottageInfo.setVisible(false);
            cottages.setVisible(true);
        });

        addCottageButton.setOnAction(e->{
            cottages.setVisible(false);
            addCottage.setVisible(true);
            editCottage = false;
            resetCottageInfoTextFields();
        });

        cottagesButton.setOnAction(e->{
            mainMenu.setVisible(false);
            cottages.setVisible(true);
        });

        cottagesBackButton.setOnAction(e->{
            cottages.setVisible(false);
            mainMenu.setVisible(true);
        });

        cottage1Button.setOnAction(e->{
            cottages.setVisible(false);
            cottageInfo.setVisible(true);
            fetchCottageInfoFromID(fetchSelectedCottageId(1));
            refreshReservationsList();
        });
        cottage2Button.setOnAction(e->{
            cottages.setVisible(false);
            cottageInfo.setVisible(true);
            fetchCottageInfoFromID(fetchSelectedCottageId(2));
            refreshReservationsList();
        });
        cottage3Button.setOnAction(e->{
            cottages.setVisible(false);
            cottageInfo.setVisible(true);
            fetchCottageInfoFromID(fetchSelectedCottageId(3));
            refreshReservationsList();
        });
        cottage4Button.setOnAction(e->{
            cottages.setVisible(false);
            cottageInfo.setVisible(true);
            fetchCottageInfoFromID(fetchSelectedCottageId(4));
            refreshReservationsList();
        });
        cottage5Button.setOnAction(e->{
            cottages.setVisible(false);
            cottageInfo.setVisible(true);
            fetchCottageInfoFromID(fetchSelectedCottageId(5));
            refreshReservationsList();
        });
        cottage6Button.setOnAction(e->{
            cottages.setVisible(false);
            cottageInfo.setVisible(true);
            fetchCottageInfoFromID(fetchSelectedCottageId(6));
            refreshReservationsList();
        });

        createReservationButton.setOnAction(e->{
            cottageInfo.setVisible(false);
            reservationEdit.setVisible(true);
            resetReservationInfoTextFields();
            editReservation = false;
        });

        confirmReservationEditButton.setOnAction(e->{
            confirmReservationInfo();
            reservationEdit.setVisible(false);
            cottageInfo.setVisible(true);
        });

        editReservationButton.setOnAction(e->{
            editReservation = true;
            cottageInfo.setVisible(false);
            reservationEdit.setVisible(true);
            fetchReservationInfoFromID(Integer.parseInt(reservationIDList.get(reservationsListView.getSelectionModel().getSelectedIndex())));
        });

        newCustomerButton.setOnAction(e->{
            createCustomerFromReservations = true;
            reservationEdit.setVisible(false);
            customerEdit.setVisible(true);
        });

        reservationEditBackButton.setOnAction(e->{
            reservationEdit.setVisible(false);
            cottageInfo.setVisible(true);
        });

        removeReservationButton.setOnAction(e->{
            cottageInfo.setVisible(false);
            confirmRemoveReservation.setVisible(true);
        });

        acceptReservationRemoveButton.setOnAction(e->{
            removeReservation();
            confirmRemoveReservation.setVisible(false);
            cottageInfo.setVisible(true);
        });

        cancelReservationRemoveButton.setOnAction(e->{
            confirmRemoveReservation.setVisible(false);
            cottageInfo.setVisible(true);
        });

        editCottageInfoButton.setOnAction(e->{
            cottageInfo.setVisible(false);
            addCottage.setVisible(true);
            editCottage = true;
            setCottageToBeChanged();
        });

        addCottageBackButton.setOnAction(e->{
            addCottage.setVisible(false);
            if(!editCottage){
                cottages.setVisible(true);
            } else {
                cottageInfo.setVisible(true);
            }
        });

        confirmAddCottageButton.setOnAction(e->{
            if(!cottageNameTextField.getText().equals("") && !addressTextArea.getText().equals("")
            && !descriptionTextArea.getText().equals("") && !pricePerNightTextField.getText().equals("")
            && !capacityTextField.getText().equals("")){
                addCottage.setVisible(false);
                if(!editCottage){
                    cottages.setVisible(true);
                } else {
                    cottageInfo.setVisible(true);
                }
                confirmCottageInfo();
                setCottagePage(currentCottagePage);
            }
        });

        invoiceBackButton.setOnAction(e->{
            invoices.setVisible(false);
            mainMenu.setVisible(true);
        });

        invoiceInfoButton.setOnAction(e->{
            invoices.setVisible(false);
            invoiceInfo.setVisible(true);
            if(invoicesListView.getSelectionModel().getSelectedItem() != null){
                setCurrentInvoice(invoicesListView.getSelectionModel().getSelectedItem());
            }
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

        confirmReportButton.setOnAction(e->{
            getReportInfo();
        });


        setCustomersPage(0);
        setCottagePage(0);

        /*
        scenen alustus
        */
        Scene scene = new Scene(mainPane, 1024, 768);
        stage.setScene(scene);
        stage.setTitle("Mökki Manager Pro");
        stage.show();
    }

}


