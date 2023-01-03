package me.andyst.monopoly.lot.factory;

import me.andyst.monopoly.lot.impl.EmptyLot;

public class EmptyLotFactory {
    public static EmptyLot getGo() {
        return new EmptyLot("Go", "Go\nCollect\n$200");
    }

    public static EmptyLot getVisitingJail() {
        return new EmptyLot("Visiting Jail", "Visiting\nJail");
    }

    public static EmptyLot getFreeParking() {
        return new EmptyLot("Free Parking", "Free\nParking");
    }

    public static EmptyLot getGoToJail() {
        return new EmptyLot("Go To Jail", "Go To\nJail");
    }

    // temporary empty lot, chance is not yet implemented
    public static EmptyLot getChance() {
        return new EmptyLot("Chance", "Chance");
    }

    // temporary empty lot, community chest is not yet implemented
    public static EmptyLot getCommunityChest() {
        return new EmptyLot("Community Chest", "Comm-\nunity\nChest");
    }
}
