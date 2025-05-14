package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Varaus {
    private int varausId; // Varauksen id
    private int asiakasId; // Asiakkaan id
    private int mokkiId; // Mökin id
    private LocalDate varauksenAlku; // Varauksen alkupvm
    private LocalDate varauksenLoppu; // Varauksen loppupvm
    private String tila;
    private LocalDateTime varausAika;

    public Varaus(int varausId, int asiakasId, int mokkiId, LocalDate varauksenAlku, LocalDate varauksenLoppu, String tila, LocalDateTime varausAika) {
        this.varausId = varausId;
        this.asiakasId = asiakasId;
        this.mokkiId = mokkiId;
        this.varauksenAlku = varauksenAlku;
        this.varauksenLoppu = varauksenLoppu;
        this.tila = tila;
        this.varausAika = varausAika;
    }

    // Getterit
    public int getVarausId(){
        return varausId;
    }
    public int getAsiakasId(){
        return asiakasId;
    }
    public int getMokkiId(){
        return mokkiId;
    }
    public LocalDate getVarauksenAlku(){
        return varauksenAlku;
    }
    public LocalDate getVarauksenLoppu(){
        return varauksenLoppu;
    }
    public String getTila(){
        return tila;
    }
    public LocalDateTime getVarausAika(){
        return varausAika;
    }

    // Setterit
    public void setAsiakasId(int asiakasId) {
        this.asiakasId = asiakasId;
    }
    public void setVarausId(int varausId) {
        this.varausId = varausId;
    }
    public void setMokkiId(int mokkiId) {
        this.mokkiId = mokkiId;
    }
    public void setVarauksenAlku(LocalDate varauksenAlku){
        this.varauksenAlku = varauksenAlku;
    }
    public void setVarauksenLoppu(LocalDate varauksenLoppu){
        this.varauksenLoppu = varauksenLoppu;
    }
    public void setTila(String tila){
        this.tila = tila;
    }
    public void setVarausAika(LocalDateTime varausAika){
        this.varausAika = varausAika;
    }

    @Override
    public String toString() {
        return "Varaus #" + varausId + " (" + varauksenAlku + " - " + varauksenLoppu + ")";
    }
}

