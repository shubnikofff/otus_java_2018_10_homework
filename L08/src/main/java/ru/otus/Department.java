package ru.otus;

import java.util.ArrayList;
import java.util.List;

class Department {
    private List<Atm> atmList;

    Department(int initialCapacity) {
        atmList = new ArrayList<>(initialCapacity);
    }

    void addAtm(Atm atm) {
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
