package org.example.constant;

public enum AccountType {
    BASIC(1),
    SAVING(2),
    CURRENT(3);

    private int value;

     AccountType(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int values) {
        this.value = values;
    }
}