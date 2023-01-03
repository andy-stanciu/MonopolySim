package me.andyst.monopoly.lot;

import me.andyst.monopoly.player.impl.Player;

public interface IProperty extends ILot, Comparable<IProperty> {
    int getValue();
    int getMortgageValue();
    String toString();
    Player getOwner();
    void setOwner(Player owner);
    boolean isOwned();
    int compareTo(IProperty other);
}
