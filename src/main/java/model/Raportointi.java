package model;

import java.time.LocalDate;

public class Raportointi {
    private final String asiakas; // asiakkaan nimi
    private final String mokki; // mökin nimi
    private final LocalDate varauksenAlku; // varauksen alkupvm
    private final LocalDate varauksenLoppu; // varauksen loppupvm

    public Raportointi(String asiakas, String mokki, LocalDate varauksenAlku, LocalDate varauksenLoppu) {
        this.asiakas = asiakas;
        this.mokki = mokki;
        this.varauksenAlku = varauksenAlku;
        this.varauksenLoppu = varauksenLoppu;
    }

    public String getAsiakas() {
        return asiakas;
    }
    public String getMokki() {
        return mokki;
    }
    public LocalDate getVarauksenAlku() {
        return varauksenAlku;
    }
    public LocalDate getVarauksenLoppu() {
        return varauksenLoppu;
    }
}
