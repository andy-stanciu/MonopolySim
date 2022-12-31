package lot.impl;

import lot.IRailroad;
import lot.Property;

public class Railroad extends Property implements IRailroad {
    private final int oneRailroadRent;
    private final int twoRailroadRent;
    private final int threeRailroadRent;
    private final int fourRailroadRent;

    public Railroad(String name, String formattedName, int value, int mortgageValue, int oneRailroadRent,
                    int twoRailroadRent, int threeRailroadRent, int fourRailroadRent) {
        super(name, formattedName, value, mortgageValue);
        this.oneRailroadRent = oneRailroadRent;
        this.twoRailroadRent = twoRailroadRent;
        this.threeRailroadRent = threeRailroadRent;
        this.fourRailroadRent = fourRailroadRent;
    }

    public int getRent(int railroads) {
        switch (railroads) {
            case 2:
                return twoRailroadRent;
            case 3:
                return threeRailroadRent;
            case 4:
                return fourRailroadRent;
            default:
                return oneRailroadRent;
        }
    }
}