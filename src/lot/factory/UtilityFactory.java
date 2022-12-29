package lot.factory;

import lot.impl.Utility;

public class UtilityFactory {
    public static Utility getElectricCompany() {
        return new Utility("Electric Company",150, 75, 4, 10);
    }

    public static Utility getWaterWorks() {
        return new Utility("Water Works",150, 75, 4, 10);
    }
}
