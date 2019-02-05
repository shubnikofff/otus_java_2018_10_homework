package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private List<Atm> atmList;

    public Department(int initialCapacity) {
        atmList = new ArrayList<>(initialCapacity);
    }

    public void addAtm(Atm atm) {
        if(!atmList.isEmpty()) {
            atmList.get(atmList.size() - 1).setNext(atm);
        }
        atmList.add(atm);
    }

    Balance getBalance() {
        Balance balance = new Balance(atmList.size());
        atmList.get(0).putBalance(balance);
        return  balance;
    }
}
