package ru.otus;

public class ImpossibleGiveMoneyException extends Exception {
    public ImpossibleGiveMoneyException(String message) {
        super(message);
    }
}
