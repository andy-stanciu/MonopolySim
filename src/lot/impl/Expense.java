package lot.impl;

import lot.IExpense;
import lot.Lot;

public class Expense extends Lot implements IExpense {
    private final int charge;

    public Expense(String name, int charge) {
        super(name);
        this.charge = charge;
    }

    public int getCharge() {
        return charge;
    }
}
