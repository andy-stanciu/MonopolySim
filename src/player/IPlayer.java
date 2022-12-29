package player;

import lot.Property;
import util.Dice;

import java.util.Set;
import java.util.TreeSet;

public interface IPlayer {
    String getName();
    int getBalance();
    int getPosition();
    TreeSet<Property> getProperties();
    Set<Property> getMortgagedProperties();
    boolean isInJail();
    boolean decreaseBalance(int amount);
    void increaseBalance(int amount);
    void turn(int tiles);
    void arrest();
    String toString();
    void tick(Dice dice);
}
