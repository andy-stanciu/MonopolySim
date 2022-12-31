package player;

import lot.Property;
import lot.impl.Neighborhood;
import util.Dice;

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
    void turn(int tiles);
    void arrest();
    String toString();
    void tick(Dice dice);
    boolean equals(Object other);
}
