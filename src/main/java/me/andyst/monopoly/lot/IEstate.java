package me.andyst.monopoly.lot;

import me.andyst.monopoly.lot.impl.Neighborhood;

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
