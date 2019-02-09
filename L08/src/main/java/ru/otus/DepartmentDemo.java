package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDemo {
    public static void main(String[] args) throws ImpossibleCollectAmountException {
        Department department = new Department(3);

        Atm firstAtm = new Atm.Builder("Atm#1")
                .withFiveEuro(4)
                .withFiftyEuro(10)
                .withOneHundredEuro(2)
                .build();
        Atm secondAtm = new Atm.Builder("Atm#2")
                .withFiveEuro(4)
                .withTenEuro(15)
                .withTwentyEuro(6)
                .withFiftyEuro(10)
                .build();
        Atm thirdAtm = new Atm.Builder("Atm#3")
                .withFiveEuro(5)
                .withTenEuro(3)
                .withOneHundredEuro(5)
                .build();

        department.addAtm(firstAtm);
        department.addAtm(secondAtm);
        department.addAtm(thirdAtm);

        System.out.println(department.getBalance());

        firstAtm.acceptMoney(prepareMoney());
        firstAtm.printBalance();

        secondAtm.giveMoney(530);
        secondAtm.printBalance();

        department.resetAtm();
    }

    static private List<Banknote> prepareMoney() {
        return new ArrayList<>(List.of(
                Banknote.FiftyEuro,
                Banknote.TwentyEuro,
                Banknote.OneHundredEuro
        ));
    }
}
