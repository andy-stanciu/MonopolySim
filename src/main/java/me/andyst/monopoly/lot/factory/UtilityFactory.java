package me.andyst.monopoly.lot.factory;

import me.andyst.monopoly.lot.impl.Utility;

public class UtilityFactory {
    public static Utility getElectricCompany() {
        return new Utility("Electric Company","Electric\nCompany", 150, 75, 4, 10);
    }

    public static Utility getWaterWorks() {
        return new Utility("Water Works","Water\nWorks", 150, 75, 4, 10);
    }
}
