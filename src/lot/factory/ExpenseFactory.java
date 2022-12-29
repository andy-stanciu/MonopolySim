package lot.factory;

import lot.impl.Expense;

public class ExpenseFactory {
    public static Expense getIncomeTax() {
        return new Expense("Income Tax", 200);
    }

    public static Expense getLuxuryTax() {
        return new Expense("Luxury Tax", 75);
    }
}
