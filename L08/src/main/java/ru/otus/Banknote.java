package ru.otus;

public enum Banknote {
    FiveEuro(5),
    TenEuro(10),
    TwentyEuro(20),
    FiftyEuro(50),
    OneHundredEuro(100);

    private int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
