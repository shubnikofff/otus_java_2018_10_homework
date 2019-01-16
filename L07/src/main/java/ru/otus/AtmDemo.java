package ru.otus;

import ru.otus.money.*;

import java.util.ArrayList;

public class AtmDemo {
    public static void main(String[] args) throws Exception {
        Atm atm = createAtm();
        ArrayList<Money> money = prepareMoney();
        atm.putMoney(money);
        System.out.println("Balance: " + atm.getBalance() + "\u20ac");
        atm.giveMoney(30);
        System.out.println("Balance: " + atm.getBalance() + "\u20ac");
    }

    private static Atm createAtm() {
        return new Atm(new short[]{
                Money.FIVE_EURO,
                Money.TWENTY_EURO,
                Money.ONE_HUNDRED_EURO,
        });
    }

    private static ArrayList<Money> prepareMoney() {
        ArrayList<Money> money = new ArrayList<>(8);
        money.add(new OneHundredEuro());
        money.add(new FiftyEuro());
        money.add(new TwentyEuro());
        money.add(new FiveEuro());
        money.add(new TenEuro());
        money.add(new TwentyEuro());
        money.add(new OneHundredEuro());
        money.add(new FiveEuro());
        return money;
    }
}
