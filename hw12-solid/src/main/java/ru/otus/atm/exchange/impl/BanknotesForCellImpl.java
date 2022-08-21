package ru.otus.atm.exchange.impl;

import ru.otus.atm.atm.Banknote;
import ru.otus.atm.atm.Cell;
import ru.otus.atm.atm.Denomination;
import ru.otus.atm.exchange.BanknotesForCell;
import ru.otus.atm.exchange.ExchangeCounter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BanknotesForCellImpl implements BanknotesForCell {

    private final Cell cell;

    public BanknotesForCellImpl(Cell cell) {
        this.cell = cell;
    }

    @Override
    public List<Banknote> handle(int sum) {
        ExchangeCounter exchangeCounter = new ExchangeCounterImpl();
        List<Integer> state = stateСellBanknote();
        return exchangeCounter.exchange(sum, state).stream()
                .map(Denomination::getByValue)
                .map(Banknote::new)
                .collect(Collectors.toList());
    }

    private List<Integer> stateСellBanknote() {
        List<Integer> state = cell.getBanknoteList();
        state.sort(Comparator.reverseOrder());
        return state;
    }
}
