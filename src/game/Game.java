package game;

import board.Board;
import player.impl.Player;
import util.Dice;

import java.util.HashSet;
import java.util.Set;

public class Game {
    private final Set<Player> players;
    private final Dice dice;

    public Game(Board board, Dice dice, int playerCount) {
        this(dice, new HashSet<>());
        for (int i = 0; i < playerCount; i++) {
            Player p = new Player(board, "p" + (i + 1));
            this.players.add(p);
        }
    }

    public Game(Dice dice, Player... players) {
        this(dice, new HashSet<>());
        for (Player p : players) {
            this.players.add(p);
        }
    }

    public Game(Dice dice, Set<Player> players) {
        this.dice = dice;
        this.players = players;
    }

    public void tick() {
        for (Player p : this.players) {
            p.tick(dice);
        }
    }

    public void printPlayerStatus() {
        for (Player p : this.players) {
            System.out.println(p);
        }
    }
}
