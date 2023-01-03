package me.andyst.monopoly.lot;

import me.andyst.monopoly.board.Board;
import me.andyst.monopoly.lot.impl.Estate;
import me.andyst.monopoly.lot.impl.NeighborhoodType;

import java.util.Set;

public interface INeighborhood extends Comparable<INeighborhood> {
    void setHouseCost(int houseCost);
    int getHouseCost();
    int getSize();
    NeighborhoodType getType();
    int getOwned();
    void setOwned(int owned);
    void setEstates(Board board);
    Set<Estate> getEstates();
    boolean equals(Object obj);
    int compareTo(INeighborhood other);
    String toString();
}
