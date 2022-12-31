package game;

import board.Board;
import graphics.DrawingPanel;
import graphics.GraphicsManager;
import graphics.GraphicsSettings;
import player.impl.Player;
import util.Dice;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Game {
    private final Set<Player> players;
    private final Dice dice;
    private final DrawingPanel display;
    private final Graphics2D graphics;
    private final GraphicsManager graphicsManager;

    public Game(Board board, Dice dice, int playerCount) {
        this(board, dice, new HashSet<>());
        for (int i = 0; i < playerCount; i++) {
            Player p = new Player(board, "p" + (i + 1));
            this.players.add(p);
        }
    }

    public Game(Board board, Dice dice, Player... players) {
        this(board, dice, new HashSet<>());
        for (Player p : players) {
            this.players.add(p);
        }
    }

    public Game(Board board, Dice dice, Set<Player> players) {
        this.dice = dice;
        this.players = players;

        this.display = new DrawingPanel(GraphicsSettings.WINDOW_SIZE, GraphicsSettings.WINDOW_SIZE);
        this.graphics = this.display.getGraphics();
        this.graphicsManager = new GraphicsManager(this.graphics);

        graphicsManager.initializeBoard();
        graphicsManager.drawLots(board);
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
