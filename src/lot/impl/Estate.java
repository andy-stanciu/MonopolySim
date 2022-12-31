package lot.impl;

import lot.IEstate;
import lot.Property;

public class Estate extends Property implements IEstate {
    private final int houseCost;
    private final int rent;
    private final int oneHouseRent;
    private final int twoHouseRent;
    private final int threeHouseRent;
    private final int fourHouseRent;
    private final int hotelRent;
    private final Neighborhood neighborhood;

    private int houses;

    public Estate(String name, String formattedName, int value, int mortgageValue, Neighborhood neighborhood, int houseCost, int rent,
                  int oneHouseRent, int twoHouseRent, int threeHouseRent, int fourHouseRent, int hotelRent) {
        super(name, formattedName, value, mortgageValue);
        this.neighborhood = neighborhood;
        this.houseCost = houseCost;
        this.rent = rent;
        this.oneHouseRent = oneHouseRent;
        this.twoHouseRent = twoHouseRent;
        this.threeHouseRent = threeHouseRent;
        this.fourHouseRent = fourHouseRent;
        this.hotelRent = hotelRent;

        neighborhood.setHouseCost(houseCost);
    }

    public int getHouseCost() {
        return houseCost;
    }

    public int getRent() {
        switch (houses) {
            case 1:
                return oneHouseRent;
            case 2:
                return twoHouseRent;
            case 3:
                return threeHouseRent;
            case 4:
                return fourHouseRent;
            case 5:
                return hotelRent;
            default:
                return rent;
        }
    }

    public int getHouses() {
        return houses;
    }

    public void addHouse() {
        houses++;
    }

    public boolean removeHouse() {
        if (houses > 0) {
            houses--;
            return true;
        }
        return false;
    }

    public boolean canAddHouse() {
        return houses < 5;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public int compareTo(IEstate other) {
        if (!neighborhood.equals(other.getNeighborhood())) {
            return neighborhood.compareTo(other.getNeighborhood());
        }
        int diff = other.getRent() - getRent();
        if (diff > 0) {
            return 1;
        }
        if (diff < 0) {
            return -1;
        }
        return getName().compareTo(other.getName());
    }

    public String toString() {
        String ret;
        switch (houses) {
            case 0:
                ret = "";
                break;
            case 1:
                ret = ", 1 house";
                break;
            case 5:
                ret = ", 1 hotel";
                break;
            default:
                ret = ", " + houses + " houses";
                break;
        }
        return super.toString() + ret;
    }
}
