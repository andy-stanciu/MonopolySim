package util;

import java.util.Random;

public class Dice {
    private static Dice dice;
    private static Random random;

    public static Dice getInstance() {
        if (dice == null) dice = new Dice();
        return dice;
    }

    public Dice() {
        random = new Random();
    }

    public int roll() {
        int first = random.nextInt(6) + 1;
        int second = random.nextInt(6) + 1;
        if (GameSettings.DOUBLES && first == second) {
            return 4 * first;
        }
        return first + second;
    }
}
