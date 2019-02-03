package ru.otus;

import java.util.Random;

public class DepartmentDemo {
    public static void main(String[] args) {
        Department department = new Department(3);
        Random random = new Random();

        Atm firstAtm = new Atm.Builder()
                .withFiveEuro(4)
                .withFiftyEuro(10)
                .withOneHundredEuro(2)
                .build();
        Atm secondAtm = new Atm.Builder()
                .withFiveEuro(4)
                .withTenEuro(15)
                .withTwentyEuro(6)
                .withFiftyEuro(10)
                .build();
        Atm thirdAtm = new Atm.Builder()
                .withFiveEuro(5)
                .withTenEuro(3)
                .withOneHundredEuro(5)
                .build();

        department.addAtm(firstAtm);
        department.addAtm(secondAtm);
        department.addAtm(thirdAtm);

        System.out.println(firstAtm.getBalance());
    }
}
