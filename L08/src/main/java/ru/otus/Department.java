package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private List<Atm> atmList;

    public Department(int initialCapacity) {
        atmList = new ArrayList<>(initialCapacity);
    }
}
