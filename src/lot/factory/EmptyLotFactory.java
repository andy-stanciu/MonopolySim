package lot.factory;

import lot.impl.EmptyLot;

public class EmptyLotFactory {
    public static EmptyLot getGo() {
        return new EmptyLot("Go", "Go");
    }

    public static EmptyLot getVisitingJail() {
        return new EmptyLot("Visiting Jail", "Visiting Jail");
    }

    public static EmptyLot getFreeParking() {
        return new EmptyLot("Free Parking", "Free Parking");
    }

    public static EmptyLot getGoToJail() {
        return new EmptyLot("Go To Jail", "Go To Jail");
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
