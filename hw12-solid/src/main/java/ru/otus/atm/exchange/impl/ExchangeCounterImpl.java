package ru.otus.atm.exchange.impl;

import ru.otus.atm.exceptions.AtmException;
import ru.otus.atm.exchange.ExchangeCounter;

import java.util.ArrayList;
import java.util.List;

public class ExchangeCounterImpl implements ExchangeCounter {
    private static final String NOT_BALANCE = "Невозможно выдать требуемую сумму";

    @Override
    public List<Integer> exchange(int sum, List<Integer> changeValues) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < changeValues.size() && sum > 0; ++i) {
            int value = changeValues.get(i);
            if (sum >= value) {
                list.add(value);
                sum -= value;
            }
        }

        if (sum != 0) {
            throw new AtmException(NOT_BALANCE);
        }
        return list;
    }
}
