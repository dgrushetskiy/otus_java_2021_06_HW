package ru.otus.atm.atm;

import lombok.Getter;
import ru.otus.atm.exceptions.AtmException;

public enum Denomination {
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    @Getter
    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public static Denomination getByValue(int value) {
        for (Denomination denomination : Denomination.values()) {
            if (denomination.getValue() == value) {
                return denomination;
            }
        }
        throw new AtmException("Невозможно определить номинал купюры");
    }
}
