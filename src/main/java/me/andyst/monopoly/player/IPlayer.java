package me.andyst.monopoly.player;

import me.andyst.monopoly.lot.Property;
import me.andyst.monopoly.lot.impl.Neighborhood;
import me.andyst.monopoly.util.Dice;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public interface IPlayer {
    String getName();
    int getBalance();
    int getPosition();
    Collection<Neighborhood> getOwnedNeighborhoods();
    TreeSet<Property> getProperties();
    Set<Property> getMortgagedProperties();
    boolean isInJail();
    boolean decreaseBalance(int amount);
    void increaseBalance(int amount);
    int turn(int tiles);
    void arrest();
    String toString();
    int tick(Dice dice);
    boolean equals(Object other);
}
