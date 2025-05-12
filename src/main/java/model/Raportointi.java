package model;

public class Raportointi {
    private int varaustenMaara; // varausten kokonaislukumäärä
    private int uudetAsiakkaat; // uusien asiakkaiden lukumäärä
    private int palaavatAsiakkaat; // palaavien asiakkaiden lukumäärä
    private double keskimaarainenPituus; // varauksen keskimääräinen pituus
    private double kayttoaste; // mökkien käyttöaste prosentteina
    private double kokonaistulot; // kokonaistulot varauksista

    public Raportointi(int varaustenMaara, int uudetAsiakkaat, int palaavatAsiakkaat,
                        double keskimaarainenPituus, double kayttoaste, double kokonaistulot) {
            this.varaustenMaara = varaustenMaara;
            this.uudetAsiakkaat = uudetAsiakkaat;
            this.palaavatAsiakkaat = palaavatAsiakkaat;
            this.keskimaarainenPituus = keskimaarainenPituus;
            this.kayttoaste = kayttoaste;
            this.kokonaistulot = kokonaistulot;
    }

    // Getterit
    public int getVaraustenMaara() { 
        return varaustenMaara; 
    }
    public int getUudetAsiakkaat() { 
        return uudetAsiakkaat; 
    }
    public int getPalaavatAsiakkaat() { 
        return palaavatAsiakkaat; 
    }
    public double getKeskimaarainenPituus() {
        return keskimaarainenPituus; 
    }
    public double getKayttoasteProsentti() { 
        return kayttoaste; 
    }
    public double getKokonaistulot() { 
        return kokonaistulot; 
    }
}
