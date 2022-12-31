package lot.factory;

import lot.impl.Neighborhood;
import lot.impl.NeighborhoodType;
import lot.impl.Estate;

public class EstateFactory {
    public static Estate getMediterranean() {
        return new Estate("Mediterranean Avenue", "Mediter-\nranean\nAve.", 60, 30, new Neighborhood(NeighborhoodType.PURPLE, 2, 1, 3), 50,
                2, 10, 30, 90, 160, 250);
    }

    public static Estate getBaltic() {
        return new Estate("Baltic Avenue", "Baltic\nAve.", 60, 30, new Neighborhood(NeighborhoodType.PURPLE, 2, 1, 3), 50,
                4, 20, 60, 180, 320, 450);
    }

    public static Estate getOriental() {
        return new Estate("Oriental Avenue", "Oriental\nAve.", 100, 50, new Neighborhood(NeighborhoodType.LIGHT_BLUE, 3, 6, 8, 9), 50,
                6, 30, 90, 270, 400, 550);
    }

    public static Estate getVermont() {
        return new Estate("Vermont Avenue", "Vermont\nAve.", 100, 50, new Neighborhood(NeighborhoodType.LIGHT_BLUE, 3, 6, 8, 9), 50,
                6, 30, 90, 270, 400, 550);
    }

    public static Estate getConnecticut() {
        return new Estate("Connecticut Avenue", "Connec-\nticut\nAve.", 120, 60, new Neighborhood(NeighborhoodType.LIGHT_BLUE, 3, 6, 8, 9), 50,
                8, 40, 100, 300, 450, 600);
    }

    public static Estate getStCharles() {
        return new Estate("St. Charles Place", "St.\nCharles\nPlace", 140, 70, new Neighborhood(NeighborhoodType.PINK, 3, 11, 13, 14), 100,
                10, 50, 150, 450, 625, 750);
    }

    public static Estate getStates() {
        return new Estate("States Avenue", "States\nAve.", 140, 70, new Neighborhood(NeighborhoodType.PINK, 3, 11, 13, 14), 100,
                10, 50, 150, 450, 625, 750);
    }

    public static Estate getVirginia() {
        return new Estate("Virginia Avenue", "Virginia\nAve.", 160, 80, new Neighborhood(NeighborhoodType.PINK, 3, 11, 13, 14), 100,
                12, 60, 180, 500, 700, 900);
    }

    public static Estate getStJames() {
        return new Estate("St. James Place", "St.\nJames\nPlace", 180, 90, new Neighborhood(NeighborhoodType.ORANGE, 3, 16, 18, 19), 100,
                14, 70, 200, 550, 750, 950);
    }

    public static Estate getTennessee() {
        return new Estate("Tennessee Avenue", "Tenne-\nssee\nAve.", 180, 90, new Neighborhood(NeighborhoodType.ORANGE, 3, 16, 18, 19), 100,
                14, 70, 200, 550, 750, 950);
    }

    public static Estate getNewYork() {
        return new Estate("New York Avenue", "New\nYork\nAve. ", 200, 100, new Neighborhood(NeighborhoodType.ORANGE, 3, 16, 18, 19), 100,
                16, 80, 220, 600, 800, 1000);
    }

    public static Estate getKentucky() {
        return new Estate("Kentucky Avenue", "Kent-\nucky\nAve.", 220, 110, new Neighborhood(NeighborhoodType.RED, 3, 21, 23, 24), 150,
                18, 90, 250, 700, 875, 1050);
    }

    public static Estate getIndiana() {
        return new Estate("Indiana Avenue", "Indiana\nAve.", 220, 110, new Neighborhood(NeighborhoodType.RED, 3, 21, 23, 24), 150,
                18, 90, 250, 700, 875, 1050);
    }

    public static Estate getIllinois() {
        return new Estate("Illinois Avenue", "Illinois\nAve.", 240, 120, new Neighborhood(NeighborhoodType.RED, 3, 21, 23, 24), 150,
                20, 100, 300, 750, 925, 1100);
    }

    public static Estate getAtlantic() {
        return new Estate("Atlantic Avenue", "Atlantic\nAve.", 260, 130, new Neighborhood(NeighborhoodType.YELLOW, 3, 26, 27, 29), 150,
                22, 110, 330, 800, 975, 1150);
    }

    public static Estate getVentnor() {
        return new Estate("Ventnor Avenue", "Ventnor\nAve.", 260, 130, new Neighborhood(NeighborhoodType.YELLOW, 3, 26, 27, 29), 150,
                22, 110, 330, 800, 975, 1150);
    }

    public static Estate getMarvinGardens() {
        return new Estate("Marvin Gardens", "Marvin\nGardens", 280, 140, new Neighborhood(NeighborhoodType.YELLOW, 3, 26, 27, 29), 150,
                24, 120, 360, 850, 1025, 1200);
    }

    public static Estate getPacific() {
        return new Estate("Pacific Avenue", "Pacific\nAve.", 300, 150, new Neighborhood(NeighborhoodType.GREEN, 3, 31, 32, 34), 200,
                26, 130, 390, 900, 1100, 1275);
    }

    public static Estate getNoCarolina() {
        return new Estate("North Carolina Avenue", "No. Ca-\nrolina\nAve.", 300, 150, new Neighborhood(NeighborhoodType.GREEN, 3, 31, 32, 34), 200,
                26, 130, 390, 900, 1100, 1275);
    }

    public static Estate getPennsylvania() {
        return new Estate("Pennsylvania Avenue", "Pennsy-\nlvania\nAve.", 320, 160, new Neighborhood(NeighborhoodType.GREEN, 3, 31, 32, 34), 200,
                28, 150, 450, 1000, 1200, 1400);
    }

    public static Estate getParkPlace() {
        return new Estate("Park Place", "Park\nPlace", 350, 175, new Neighborhood(NeighborhoodType.BLUE, 2, 37, 39), 200,
                35, 175, 500, 1100, 1300, 1500);
    }

    public static Estate getBoardwalk() {
        return new Estate("Boardwalk", "Board-\nwalk", 400, 200, new Neighborhood(NeighborhoodType.BLUE, 2, 37, 39), 200,
                50, 200, 600, 1400, 1700, 2000);
    }
}