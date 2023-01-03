package me.andyst.monopoly.lot;

public abstract class Lot implements ILot {
    private final String name;
    private final String formattedName;

    private int playerCount;

    public Lot(String name, String formattedName) {
        this.name = name;
        this.formattedName = formattedName;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void incrementPlayerCount() {
        playerCount++;
    }

    public void decrementPlayerCount() {
        playerCount--;
        if (playerCount < 0) {
            throw new IllegalStateException("Player count at " + name + " must be a non-negative number!");
        }
    }

    public String toString() {
        return name;
    }
}
