package me.andyst.monopoly.lot.factory;

import me.andyst.monopoly.lot.impl.Expense;

public class ExpenseFactory {
    public static Expense getIncomeTax() {
        return new Expense("Income Tax", "Income\nTax", 200);
    }

    public static Expense getLuxuryTax() {
        return new Expense("Luxury Tax", "Luxury\nTax", 75);
    }
}
