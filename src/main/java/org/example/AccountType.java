package org.example;

public enum AccountType {
    BASIC(1),
    SAVING(2),
    CURRENT(3);
    private int value;
    private AccountType(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}