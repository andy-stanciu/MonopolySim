package lot.impl;

import board.Board;
import lot.INeighborhood;

import java.util.Set;
import java.util.TreeSet;

public class Neighborhood implements INeighborhood {
    private final int size;
    private final NeighborhoodType type;
    private final int[] estateLocations;
    private final Set<Estate> estates;

    private int owned;
    private int houseCost;

    public Neighborhood(NeighborhoodType type, int size, int... estateLocations) {
        this.type = type;
        this.size = size;
        this.estateLocations = estateLocations;
        this.estates = new TreeSet<>();
    }

    public void setHouseCost(int houseCost) {
        this.houseCost = houseCost;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public int getSize() {
        return size;
    }

    public NeighborhoodType getType() {
        return type;
    }

    public int getOwned() {
        return owned;
    }

    public void setOwned(int owned) {
        this.owned = owned;
        if (owned < 0) {
            throw new IllegalStateException("Illegal value encountered for owned estates in neighborhood!");
        }
    }

    public Set<Estate> getEstates() {
        return estates;
    }

    public void setEstates(Board board) {
        for (int loc : estateLocations) {
            estates.add(board.getEstateAt(loc));
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof INeighborhood) {
            INeighborhood other = (INeighborhood)obj;
            return type == other.getType();
        }
        return false;
    }

    public int compareTo(INeighborhood other) {
        int diff = other.getOwned() - owned;
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        }
        diff = other.getType().ordinal() - type.ordinal();
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        }
        return type.name().compareTo(other.getType().name());
    }

    public String toString() {
        return type.name() + " (" + owned + "/" + size + ")";
    }
}
