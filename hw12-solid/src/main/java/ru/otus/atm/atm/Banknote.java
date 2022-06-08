package ru.otus.atm.atm;

import lombok.Getter;

@Getter
public class Banknote {

    private final Denomination denomination;

    public Banknote(Denomination denomination) {
        this.denomination = denomination;
    }

    @Override
    public String toString() {
        return "Banknote{ denomination=" + denomination.getValue() + '}';
    }
}
