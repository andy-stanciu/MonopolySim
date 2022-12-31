package lot.impl;

import lot.IExpense;
import lot.Lot;

public class Expense extends Lot implements IExpense {
    private final int charge;

    public Expense(String name, String formattedName, int charge) {
        super(name, formattedName);
        this.charge = charge;
    }

    public int getCharge() {
        return charge;
    }
}
