package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Lasku {
    private int laskuId; // Laskun id
    private int varausId; // Varauksen id
    private double summa; // Laskun summa
    private LocalDate erapaiva; // Laskun eräpäivä
    private LocalDateTime luontiaika; // Laskun luontiajankohta

    public Lasku(int laskuId, int varausId, double summa, LocalDate erapaiva, LocalDateTime luontiaika) {
        this.laskuId = laskuId;
        this.varausId = varausId; 
        this.summa = summa; 
        this.erapaiva = erapaiva; 
        this.luontiaika = luontiaika; 
    }

    // Getterit
    public int getLaskuId(){
        return laskuId;
    }
    public int getVarausId(){
        return varausId;
    }
    public double getSumma(){
        return summa;
    }
    public LocalDate getErapaiva(){
        return erapaiva;
    }
    public LocalDateTime getLuontiaika(){
        return luontiaika;
    }

    // Setterit
    public void setSumma(double summa) {
        this.summa = summa;
    }
    public void setErapaiva(LocalDate erapaiva) {
        this.erapaiva = erapaiva;
    }
}
