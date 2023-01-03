package me.andyst.monopoly.lot;

public interface ILot {
    String getName();
    String toString();
    String getFormattedName();
    int getPlayerCount();
    void incrementPlayerCount();
    void decrementPlayerCount();
}
