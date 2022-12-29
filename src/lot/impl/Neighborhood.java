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

    public void incrementOwned() {
        owned++;
    }

    public void decrementOwned() {
        if (owned < 1) {
            throw new IllegalStateException("Illegal value encountered for owned estates in neighborhood!");
        }
        owned--;
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
        if (obj instanceof Neighborhood) {
            Neighborhood other = (Neighborhood)obj;
            return type == other.type;
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
        return other.getType().ordinal() - type.ordinal();
    }
}
