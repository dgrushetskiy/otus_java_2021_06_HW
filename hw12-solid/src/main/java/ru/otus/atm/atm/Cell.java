package ru.otus.atm.atm;

import ru.otus.atm.exceptions.AtmException;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cell {
    private static final String ATM_NOT_WORK = "В банкомате закончились купюры данного номинала";
    private final EnumMap<Denomination, Integer> atmState;

    public Cell(EnumMap<Denomination, Integer> state) {
        this.atmState = state;
    }

    public void addBanknotes(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            atmState.merge(banknote.getDenomination(), 1, Integer::sum);
        }
    }

    public void removeBanknotes(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            if (!atmState.containsKey(banknote.getDenomination()) || atmState.get(banknote.getDenomination()) == 0) {
                throw new AtmException(ATM_NOT_WORK);
            }
            atmState.merge(banknote.getDenomination(), 0, (old, one) -> old - 1);
        }
    }

    public int getBalance() {
        return atmState.entrySet()
                .stream()
                .map(e -> e.getKey().getValue() * e.getValue())
                .mapToInt(Integer::intValue).sum();
    }

    public List<Integer> getBanknoteListForEach() {
        List<Integer> values = new ArrayList<>();
        atmState.forEach((key, value) -> {
            extracted(values, key, value);
        });
        return values;
    }

    public List<Integer> getBanknoteListForMap() {
        List<Integer> values = new ArrayList<>();
        atmState.entrySet()
                .stream()
                .map(e -> {
                    extracted(values, e.getKey(), e.getValue());
                    return values;
                });
        return values;
    }

    private void extracted(List<Integer> values, Denomination key, Integer value) {
        for (int i = 0; i < value; ++i) {
            values.add(key.getValue());
        }
    }
}
