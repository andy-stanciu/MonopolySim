package lot;

import lot.impl.Neighborhood;

public interface IEstate extends IProperty {
    int getHouseCost();
    int getRent();
    int getHouses();
    void addHouse();
    boolean removeHouse();
    boolean canAddHouse();
    Neighborhood getNeighborhood();
    int compareTo(IEstate other);
    String toString();
}
