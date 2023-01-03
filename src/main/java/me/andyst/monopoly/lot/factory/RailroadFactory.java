package me.andyst.monopoly.lot.factory;

import me.andyst.monopoly.lot.impl.Railroad;

public class RailroadFactory {
    public static Railroad getReading() {
        return new Railroad("Reading Railroad", "Reading\nRailroad", 200, 100,
                25, 50, 100, 200);
    }

    public static Railroad getPennsylvania() {
        return new Railroad("Pennsylvania Railroad", "Pennsy-\nlvania\nRailroad", 200, 100,
                25, 50, 100, 200);
    }

    public static Railroad getBAndO() {
        return new Railroad("B. & O. Railroad", "B. & O.\nRailroad", 200, 100,
                25, 50, 100, 200);
    }

    public static Railroad getShortLine() {
        return new Railroad("Short Line Railroad", "Short\nLine\nRailroad", 200, 100,
                25, 50, 100, 200);
    }
}
