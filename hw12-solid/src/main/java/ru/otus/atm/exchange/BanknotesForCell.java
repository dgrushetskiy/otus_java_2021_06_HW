package ru.otus.atm.exchange;

import ru.otus.atm.atm.Banknote;

import java.util.List;

public interface BanknotesForCell {

    List<Banknote> handle(int sum);
}
