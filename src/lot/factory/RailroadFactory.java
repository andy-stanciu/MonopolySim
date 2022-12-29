package lot.factory;

import lot.impl.Railroad;

public class RailroadFactory {
    public static Railroad getReading() {
        return new Railroad("Reading Railroad", 200, 100,
                25, 50, 100, 200);
    }

    public static Railroad getPennsylvania() {
        return new Railroad("Pennsylvania Railroad", 200, 100,
                25, 50, 100, 200);
    }

    public static Railroad getBAndO() {
        return new Railroad("B. & O. Railroad", 200, 100,
                25, 50, 100, 200);
    }

    public static Railroad getShortLine() {
        return new Railroad("Short Line Railroad", 200, 100,
                25, 50, 100, 200);
    }
}
