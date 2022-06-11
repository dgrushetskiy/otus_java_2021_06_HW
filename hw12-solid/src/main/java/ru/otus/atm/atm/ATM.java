package ru.otus.atm.atm;

import ru.otus.atm.exchange.BanknotesForCell;

import java.util.List;

public class ATM {

    private final Cell cell;
    private final BanknotesForCell banknotesForCell;

    public ATM(Cell cell, BanknotesForCell banknotesForCell) {
        this.cell = cell;
        this.banknotesForCell = banknotesForCell;
    }

    public List<Banknote> give(int sum) {
        List<Banknote> banknotes = banknotesForCell.handle(sum);
        cell.removeBanknotes(banknotes);
        return banknotes;
    }

    public void take(List<Banknote> banknotes) {
        cell.addBanknotes(banknotes);
    }

    public int getBalance() {
        return cell.getBalance();
    }
}
