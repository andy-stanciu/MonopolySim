package me.andyst.monopoly.lot.impl;

import me.andyst.monopoly.lot.IUtility;
import me.andyst.monopoly.lot.Property;

public class Utility extends Property implements IUtility {
    private final int oneUtilityMultiplier;
    private final int twoUtilityMultiplier;

    public Utility(String name, String formattedName, int value, int mortgageValue,
                   int oneUtilityMultiplier, int twoUtilityMultiplier) {
        super(name, formattedName, value, mortgageValue);
        this.oneUtilityMultiplier = oneUtilityMultiplier;
        this.twoUtilityMultiplier = twoUtilityMultiplier;
    }

    public int getRent(int utilities, int roll) {
        if (utilities > 1) {
            return twoUtilityMultiplier * roll;
        }
        return oneUtilityMultiplier * roll;
    }
}
