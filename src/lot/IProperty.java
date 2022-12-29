package lot;

import player.impl.Player;

public interface IProperty extends ILot, Comparable<IProperty> {
    int getValue();
    int getMortgageValue();
    String toString();
    Player getOwner();
    void setOwner(Player owner);
    boolean isOwned();
    int compareTo(IProperty other);
}
