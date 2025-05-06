package model;

public class Mokki {
    private int mokkiId; // Mökin id
    private String nimi; // Mökin nimi
    private String osoite; // Mökin osoite
    private String kuvaus; // Kuvaus mökistä
    private double hintaPerYö; // Mökin hinta yöltä
    private int kapasiteetti; // Mökin asiakaskapasiteetti
    private boolean onVapaana; // Mökin varaustilanne

    public Mokki(int mokkiId, String nimi, String osoite, String kuvaus, double hintaPerYö, int kapasiteetti, boolean onVapaana) {
        this.mokkiId = mokkiId;
        this.nimi = nimi;
        this.osoite = osoite;
        this.kuvaus = kuvaus;
        this.hintaPerYö = hintaPerYö;
        this.kapasiteetti = kapasiteetti;
        this.onVapaana = onVapaana;
    }

    // Getterit
    public int getMokkiId(){
        return mokkiId;
    }
    public String getNimi(){
        return nimi;
    }
    public String getOsoite(){
        return osoite;
    }
    public String getKuvaus(){
        return kuvaus;
    }
    public double getHintaPerYö(){
        return hintaPerYö;
    }
    public int getKapasiteetti(){
        return kapasiteetti;
    }
    public boolean isOnVapaana(){
        return onVapaana;
    }

    // Setterit
    public void setNimi(String nimi){
        this.nimi = nimi;
    }
    public void setOsoite(String osoite){
        this.osoite = osoite;
    }
    public void setKuvaus(String kuvaus){
        this.kuvaus = kuvaus;
    }
    public void setHintaPerYö(double hintaPerYö){
        this.hintaPerYö = hintaPerYö;
    }
    public void setKapasiteetti(int kapasiteetti){
        this.kapasiteetti = kapasiteetti;
    }
    public void setOnVapaana(boolean onVapaana){
        this.onVapaana = onVapaana;
    }

}
