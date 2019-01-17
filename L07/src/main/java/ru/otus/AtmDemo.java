package ru.otus;

import ru.otus.money.*;

import java.util.ArrayList;

public class AtmDemo {
    public static void main(String[] args) {
        Atm atm = createAtm();
        ArrayList<Money> money = prepareMoney();

        atm.putMoney(money);
        atm.printBalance();

        try {
            atm.giveMoney(105);
            atm.printBalance();

            atm.giveMoney(120);
            atm.printBalance();

            atm.giveMoney(15);
        } catch (ImpossibleGiveMoneyException e) {
            System.out.println(e.toString());
            atm.printBalance();
        }
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
