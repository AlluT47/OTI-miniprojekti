package model;

public class Asiakas {
    private int asiakasId; //Asiakkaan id
    private String nimi; // Asiakkaan nimi
    private String sposti; // Sähköposti
    private String puhelin; // Puhelinnumero
    private String asiakastyyppi; // Asiakastyyppi on joko "yksityinen" tai "yritys"

    public Asiakas(int asiakasId, String nimi, String sposti, String puhelin, String asiakastyyppi) {
        this.asiakasId = asiakasId;
        this.nimi = nimi; 
        this.sposti = sposti; 
        this.puhelin = puhelin; 
        this.asiakastyyppi = asiakastyyppi; 
    }

    // Getterit
    public int getAsiakasId(){
        return asiakasId;
    }
    public String getNimi(){
        return nimi;
    }
    public String getSposti(){
        return sposti;
    }
    public String getPuhelin(){
        return puhelin;
    }
    public String getAsiakastyyppi(){
        return asiakastyyppi;
    }

    // Setterit
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    public void setSposti(String sposti) {
        this.sposti = sposti;
    }
    public void setPuhelin(String puhelin) {
        this.puhelin = puhelin;
    }
    public void setAsiakastyyppi(String asiakastyyppi){
        this.asiakastyyppi = asiakastyyppi;
    }
}
