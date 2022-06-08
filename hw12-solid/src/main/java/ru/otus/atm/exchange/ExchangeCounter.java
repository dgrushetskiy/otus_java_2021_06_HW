package ru.otus.atm.exchange;

import java.util.List;

public interface ExchangeCounter {

    List<Integer> exchange(int sum, List<Integer> changeValues);
}
