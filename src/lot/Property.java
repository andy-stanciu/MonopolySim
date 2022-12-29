package lot;

import lot.impl.Railroad;
import player.impl.Player;

public abstract class Property extends Lot implements IProperty {
    private final int value;
    private final int mortgageValue;

    private Player owner;

    public Property(String name, int value, int mortgageValue) {
        super(name);
        this.value = value;
        this.mortgageValue = mortgageValue;
        this.owner = null;
    }

    public int getValue() {
        return value;
    }

    public int getMortgageValue() {
        return mortgageValue;
    }

    public String toString() {
        return super.toString() + " ($" + value + ")";
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isOwned() {
        return owner != null;
    }

    public int compareTo(IProperty other) {
        if (this instanceof IEstate && other instanceof IEstate) {
            return ((IEstate)this).compareTo((IEstate)other);
        }
        if (this instanceof IEstate) { // other is not an estate
            return -1;
        }
        if (other instanceof IEstate) { // this is not an estate
            return 1;
        }
        if (this instanceof IRailroad && other instanceof IRailroad) {
            // railroads cannot be compared amongst each other
            return 0;
        }
        if (this instanceof Railroad) { // other is not a railroad
            return -1;
        }
        if (other instanceof Railroad) { // this is not a railroad
            return 1;
        }
        // must both be utilities, and utilities cannot be compared amongst each other
        return 0;
    }
}