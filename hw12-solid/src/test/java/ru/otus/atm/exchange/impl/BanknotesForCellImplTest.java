package ru.otus.atm.exchange.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.atm.ATM;
import ru.otus.atm.atm.Banknote;
import ru.otus.atm.atm.Cell;
import ru.otus.atm.atm.Denomination;
import ru.otus.atm.exceptions.AtmException;
import ru.otus.atm.exchange.BanknotesForCell;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BanknotesForCellImplTest {

    private static final EnumMap<Denomination, Integer> atmState = new EnumMap<>(Denomination.class);

    @BeforeEach
    public void setUp() {
        atmState.put(Denomination.TEN, 0);
        atmState.put(Denomination.FIFTY, 0);
        atmState.put(Denomination.HUNDRED, 0);
        atmState.put(Denomination.TWO_HUNDRED, 0);
        atmState.put(Denomination.FIVE_HUNDRED, 0);
        atmState.put(Denomination.THOUSAND, 0);
        atmState.put(Denomination.TWO_THOUSAND, 0);
        atmState.put(Denomination.FIVE_THOUSAND, 0);
    }
    @Test
    void testAllGiveOk() {
        ATM atm = getAtm();
        atm.take(prepareBanknotes());
        List<Banknote> banknotes = atm.give(2500);
        assertEquals(11, banknotes.size());
        assertEquals(0, atm.getBalance());
    }

    @Test
    void testGiveOk() {
        ATM atm = getAtm();
        atm.take(prepareBanknotes());
        List<Banknote> banknotes = atm.give(60);
        assertEquals(2, banknotes.size());
        assertEquals(2440, atm.getBalance());
    }

    @Test
    void testBanknoteOk() {
        ATM atm = getAtm();
        atm.take(prepareBanknotes());
        List<Banknote> banknotes = atm.give(50);
        assertEquals(1, banknotes.size());
        assertEquals(2450, atm.getBalance());
    }

    @Test
    void testException() {
        ATM atm = getAtm();
        atm.take(prepareBanknotes());
        assertThrows(AtmException.class, () -> atm.give(2600));
    }


    private ATM getAtm() {
        Cell cell = new Cell(atmState);
        BanknotesForCell banknotesForCell = new BanknotesForCellImpl(cell);
        return new ATM(cell, banknotesForCell);
    }

    private List<Banknote> prepareBanknotes() {
        Banknote banknote1 = new Banknote(Denomination.TEN);
        Banknote banknote2 = new Banknote(Denomination.TEN);
        Banknote banknote3 = new Banknote(Denomination.TEN);
        Banknote banknote4 = new Banknote(Denomination.TEN);
        Banknote banknote5 = new Banknote(Denomination.TEN);
        Banknote banknote6 = new Banknote(Denomination.FIFTY);
        Banknote banknote7 = new Banknote(Denomination.HUNDRED);
        Banknote banknote8 = new Banknote(Denomination.HUNDRED);
        Banknote banknote9 = new Banknote(Denomination.HUNDRED);
        Banknote banknote10 = new Banknote(Denomination.HUNDRED);
        Banknote banknote11 = new Banknote(Denomination.TWO_THOUSAND);
        return Arrays.asList(banknote1, banknote2, banknote3, banknote4,
                banknote5, banknote6, banknote7, banknote8, banknote9, banknote10, banknote11);
    }

}
